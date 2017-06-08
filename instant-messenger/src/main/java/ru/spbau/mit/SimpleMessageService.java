package ru.spbau.mit;

import com.sun.xml.internal.ws.api.model.MEP;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.MessageServiceGrpc.MessageServiceImplBase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
//                String from = value.getSender();
                logger.info("message received: \n" + value);
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
//                System.exit(1);
            }
        });
    }

    @Override
    public StreamObserver<Message> chat(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                String from = value.getSender();
                logger.info(value);
                logger.debug(clientObservers.entrySet());
                if (!clientObservers.containsKey(from)) {
                    clientObservers.put(from, responseObserver);
                }
                String to = value.getReceiver();
                if (!clientObservers.containsKey(to)) {
                    responseObserver.onError(new IllegalArgumentException("invalid receiver: " + to));
                }
                clientObservers.get(to).onNext(value);
                String logMessage = "message sent: \n" + value;
                Message successMessage = Message.newBuilder()
                        .setText(logMessage)
                        .setTime(new Date(System.currentTimeMillis()).toString())
                        .setSender("service")
                        .setReceiver(value.getSender())
                        .build();
                responseObserver.onNext(successMessage);
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

//    @Override
//    public void connect(ClientData clientData, StreamObserver<Message> responseObserver) {
//        String clientName = clientData.getName();
//        if (clientObservers.containsKey(clientName)) {
//            responseObserver.onError(new IllegalArgumentException("already has a user with name: " + clientName));
//        }
//
//        clientObservers.put(clientName, responseObserver);
//
//        Message successMessage = Message.newBuilder()
//                .setText("client " + clientName + " added")
//                .setTime(new Date(System.currentTimeMillis()).toString())
//                .setSender("service")
//                .setReceiver(clientName)
//                .build();
//        responseObserver.onNext(successMessage);
//    }

    // TODO disconnect.

//    @Override
//    public void chat(Message request, StreamObserver<Message> responseObserver) {
//        logger.info("received message: " + request);
//        Date date = new Date(System.currentTimeMillis());
//        Message response = Message.newBuilder()
//                .setText("Received: " + request)
//                .setTime(date.toString())
//                .setSender("server")
//                .setReceiver(request.getSender())
//                .build();
//        responseObserver.onNext(response);
//        logger.info("sent response: " + response);
//        responseObserver.onCompleted();
//    }
}
