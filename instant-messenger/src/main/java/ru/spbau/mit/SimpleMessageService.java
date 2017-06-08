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
//    private final StreamObserver<Message> observer;
////    private final ConcurrentMap<String, StreamObserver<Message>> clientObservers = new ConcurrentHashMap<>();
//
//    SimpleMessageService() {
//        super();
//        observer = new StreamObserver<Message>() {
//            @Override
//            public void onNext(Message value) {
//                logger.info("message received: \n" + value);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.err.println(t.getMessage());
//                logger.error(t.getMessage());
////                System.exit(1);
//            }
//
//            @Override
//            public void onCompleted() {
//                logger.info("Exiting");
//            }
//        };
//    }

    @Override
    public StreamObserver<Message> chat(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message request) {
                String from = request.getSender();
                String message = from + "wrote: " + request.getText();
                logger.info("received message: \n" + request);
                System.out.println(message);
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
                System.err.println("ERROR: " + t.getMessage());
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
