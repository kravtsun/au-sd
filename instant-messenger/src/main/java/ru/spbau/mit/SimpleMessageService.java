package ru.spbau.mit;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.MessageServiceGrpc.MessageServiceImplBase;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleMessageService extends MessageServiceImplBase {
    private static final Logger logger = LogManager.getLogger("service");
    private final ConcurrentMap<String, StreamObserver<Message>> clientObservers = new ConcurrentHashMap<>();

    SimpleMessageService() {
        super();
        clientObservers.put("service", new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                logger.info("message received: \n" + value);
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
                System.exit(1);
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
                System.exit(0);
            }
        });
    }

    @Override
    public StreamObserver<Message> chat(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message request) {
                String from = request.getSender();
                if (!clientObservers.containsKey(from)) {
                    clientObservers.put(from, responseObserver);
                }
                StreamObserver<Message> observer = clientObservers.get(from);
                String receiver = request.getReceiver();
                if (!clientObservers.containsKey(receiver)) {
                    String errorMessage = "ERROR: invalid receiver: " + receiver;
                    observer.onNext(Message.newBuilder()
                            .setText(errorMessage)
                            .setSender("service")
                            .setReceiver(from)
                            .setTime(currentDateString())
                            .build());
                } else {
                    clientObservers.get(receiver).onNext(request);
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    public static String currentDateString() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
