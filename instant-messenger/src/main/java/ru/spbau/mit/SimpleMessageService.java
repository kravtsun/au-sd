package ru.spbau.mit;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.mit.MessageServiceGrpc.MessageServiceImplBase;

import java.util.Date;

public class SimpleMessageService extends MessageServiceImplBase {
    private static final Logger logger = LogManager.getLogger("service");

    @Override
    public void chat(Message request, StreamObserver<Message> responseObserver) {
        logger.info("received message: " + request);
        Date date = new Date(System.currentTimeMillis());
        Message response = Message.newBuilder()
                .setText("Received: " + request)
                .setTime(date.toString())
                .setSender("server")
                .setReceiver(request.getSender())
                .build();
        responseObserver.onNext(response);
        logger.info("sent response: " + response);
        responseObserver.onCompleted();
    }
}
