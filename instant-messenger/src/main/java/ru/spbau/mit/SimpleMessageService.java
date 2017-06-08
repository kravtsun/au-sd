package ru.spbau.mit;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.MessageServiceGrpc.MessageServiceImplBase;

import java.util.Date;

public class SimpleMessageService extends MessageServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger("service");

    @Override
    public StreamObserver<Message> chat(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message request) {
                String from = request.getSender();
                String message = from + " wrote: " + request.getText();
                LOGGER.info("received message: \n" + request);
                System.out.println(message);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.error(t.getMessage());
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
