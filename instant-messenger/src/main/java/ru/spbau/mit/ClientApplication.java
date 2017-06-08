package ru.spbau.mit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientApplication {
    private static final Logger logger = LogManager.getLogger("client");

    private final ManagedChannel channel;
//    private final MessageServiceGrpc.MessageServiceBlockingStub blockingStub;
    private final MessageServiceGrpc.MessageServiceStub stub;
    private final String name;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public ClientApplication(String host, int port, String name) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true).build();
//        this.blockingStub = MessageServiceGrpc.newBlockingStub(channel);
//        this.blockingStub.connect(ClientData.newBuilder().setName(name).build());
        this.stub = MessageServiceGrpc.newStub(channel);
//        Message initMessage = Message.newBuilder()
//                .setText("test")
//                .setTime(new Date(System.currentTimeMillis()).toString())
//                .setSender(name)
//                .build();
//        this.stub.chat()
        this.name = name;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

//    public void sentMessage(String recipient, String text) {
//        Message request = Message.newBuilder()
//                .setText(text)
//                .setTime(new Date(System.currentTimeMillis()).toString())
//                .setSender(getName())
//                .setReceiver(recipient)
//                .build();
//        logger.info("sending message: \n" + request);
//        Message response = null;
//
//        try {
//            response = blockingStub.chat(request);
//        } catch (StatusRuntimeException e) {
//            logger.error("RPC error: " + e.getMessage());
//        }
//        logger.info("received message: " + response);
//    }

    public static void main(String[] args) throws InterruptedException {
        String hostOptionString = "host";
        String portOptionString = "port";
        String nameOptionString = "name";

        Options options = new Options();

        Option hostOption = Option.builder()
                .argName(hostOptionString)
                .longOpt(hostOptionString)
                .hasArg(true)
                .type(String.class)
                .required(true)
                .desc("Server address.")
                .build();

        Option portOption = Option.builder()
                .argName(portOptionString)
                .longOpt(portOptionString)
                .hasArg(true)
                .type(Integer.class)
                .required(true)
                .desc("Server port.")
                .build();

        Option nameOption = Option.builder()
                .argName(nameOptionString)
                .longOpt(nameOptionString)
                .hasArg(true)
                .type(String.class)
                .required(true)
                .desc("name")
                .build();

        // what if two names collapses?

        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(nameOption);
        assert options.getRequiredOptions().size() == 2;

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Error while trying to parse options: " + e.getMessage());
            System.err.println(e.getMessage());
            // TODO make help message display required options in USAGE string.
            formatter.printHelp("instant-messenger-client", options);
            System.exit(1);
        }

        // TODO use options to get parsed values.
        String host = cmd.getOptionValue(hostOptionString);
        int port = Integer.valueOf (cmd.getOptionValue(portOptionString));
        String name = cmd.getOptionValue(nameOptionString);
        logger.debug("Parsed options: " + "host: " + host + ", port: " + port);

        ClientApplication client = null;
        try {
            client = new ClientApplication(host, port, name);
        }
        catch (Throwable t) {
            logger.error("Error while trying to create client application: " + t.getMessage());
            System.err.println(t.getMessage());
            System.exit(1);
        }

        System.out.println("Message format: <message-receiver-name>: message-text");
        System.out.println("where <message-receiver-name> is non-empty sequence of colon-less symbols");
        System.out.println("submit \"exit\" to exit.");

        Scanner in = new Scanner(System.in);

        StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                logger.info("received message: \n" + value);
                if (!value.getReceiver().equals(name)) {
                    String errorMessage = "Invalid receiver: " + value.getReceiver() + " for name: " + name;
                    throw new InvalidStateException(errorMessage);
                }
                System.out.println(value.getSender() + " wrote: " + value.getText());
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
                System.out.println("ERROR: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
                System.exit(0);
            }
        };

        try {
            StreamObserver<Message> requestObserver = client.stub.chat(responseObserver);
            while (true) {
                String line = in.nextLine();
                if (line.equals("exit")) {
                    System.exit(0);
//                    client.shutdown();
                }
                try {
                    int i = line.indexOf(":");
                    String receiver = line.substring(0, i);
                    String message = line.substring(i+1);
                    requestObserver.onNext(Message.newBuilder()
                            .setText(message)
                            .setTime(new Date(System.currentTimeMillis()).toString())
                            .setSender(name)
                            .setReceiver(receiver)
                            .build());
                }
                catch (Exception e) { // TODO make caught exception more specific.
                    logger.error("Invalid message");
                }
            }
//            client.sentMessage("server", "hello");
        }
        finally {
            client.shutdown();
        }
    }

//    public String getName() {
//        return name;
//    }
}
