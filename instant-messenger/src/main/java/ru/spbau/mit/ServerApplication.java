package ru.spbau.mit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ServerApplication {
    private static final Logger logger = LogManager.getLogger("server");
    private Server server;
    private final String name;

    /**
     * @param host not used now.
     * @param port serving port.
     * @throws IOException on failing to start and bind to server.
     */
    private ServerApplication(String host, int port, String name) throws IOException {
        // TODO use host for creating service daemon.
        server = ServerBuilder.forPort(port)
                .addService(new SimpleMessageService())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            ServerApplication.this.stop();
            System.err.println("*** server shut down");
        }));
        this.name = name;
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private class Connection {
        private ManagedChannel channel;
        private StreamObserver<Message> requestObserver;

        Connection(String host, int port) {
            logger.debug("Setting ManagedChannelBuilder...");
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext(true)
                    .build();

            logger.debug("Setting MessageServiceStub...");
            MessageServiceGrpc.MessageServiceStub stub = MessageServiceGrpc.newStub(channel);

            StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
                @Override
                public void onNext(Message value) {
                    logger.info("received message: \n" + value);
//                    String receiver = value.getReceiver();
//                    if (!receiver.equals(name)) {
//                        String errorMessage = "Invalid receiver: " + receiver + " for name: " + name;
//                        logger.error(errorMessage);
//                        throw new IllegalStateException(errorMessage);
//                    }
                    System.out.println(value.getSender() + " wrote: " + value.getText());
                }

                @Override
                public void onError(Throwable t) {
                    logger.error(t.getMessage());
                    System.out.println("ERROR: " + t.getMessage());
//                    shutdown(1);
                }

                @Override
                public void onCompleted() {
                    logger.info("onCompleted, name: " + name);
//                    shutdown(0);
                }
            };

            logger.debug("Setting chat connection...");
            requestObserver = stub.chat(responseObserver);
        }

        public void send(String text) {
            logger.info("sending message: \n" + text);
            Message message = Message.newBuilder()
                    .setText(text)
                    .setTime(SimpleMessageService.currentDateString())
                    .setSender(name)
                    .build();
            requestObserver.onNext(message);
            logger.info("message sent: \n" + message);

        }

        public void shutdown() {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }


    private Connection getConnection(String receiverHost, int receiverPort) {
        return new Connection(receiverHost, receiverPort);
    }

//    /**
//     * Await termination on the main thread since the grpc library uses daemon threads.
//     */
//    private void blockUntilShutdown() throws InterruptedException {
//        if (server != null) {
//            server.awaitTermination();
//        }
//    }

    public static void main(String[] args) {
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
                .desc("Serving address.")
                .build();

        Option portOption = Option.builder()
                .argName(portOptionString)
                .longOpt(portOptionString)
                .hasArg(true)
                .type(Integer.class)
                .required(true)
                .desc("Serving port.")
                .build();

        Option nameOption = Option.builder()
                .argName(nameOptionString)
                .longOpt(nameOptionString)
                .hasArg(true)
                .type(String.class)
                .required(true)
                .desc("name")
                .build();

        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(nameOption);

        assert options.getRequiredOptions().size() == 3;

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Error while trying to parse options: " + e.getMessage());
            System.err.println(e.getMessage());
            // TODO make help message display required options in USAGE string.
            formatter.printHelp("instant-messenger", options);
            System.exit(1);
        }

        // TODO use options to get parsed values.
        String host = cmd.getOptionValue(hostOptionString);
        int port = Integer.valueOf(cmd.getOptionValue(portOptionString));
        final String name = cmd.getOptionValue(nameOptionString);
        logger.debug("Parsed options: "
                + "host: " + host
                + ", port: " + port
                + ", name: " + name);
        ServerApplication serverApplication = null;
        try {
            serverApplication = new ServerApplication(host, port, name);
        } catch (IOException e) {
            logger.error("Error while trying to create server application: " + e.getMessage());
            System.err.println(e.getMessage());
            System.exit(1);
        }

        String messageFormatHelp =
                "connect <host> <port>\n" +
                        "disconnect\n" +
                        "exit\n";
//                "Message format: <message-receiver-name>: message-text\n" +
//                "where <message-receiver-name> is non-empty sequence of colon-less symbols\n" +
//                "submit \"exit\" to exit.";
        System.out.println(messageFormatHelp);

        Scanner stdin = new Scanner(System.in);
        Connection connection = null;
        while (true) {
            String line = stdin.nextLine();
            try {
                if (line.equals("exit")) {
                    break;
                } else if (!Objects.isNull(connection)) {
                    if (line.equals("disconnect")) {
                        connection.shutdown();
                        connection = null;
                    } else {
                        connection.send(line);
                    }
                } else {
                    if (line.length() > 7 && line.substring(0, 7).equals("connect")) {
                        Scanner in = new Scanner(line);
                        String s = in.next("connect");
                        if (!s.equals("connect")) {
                            throw new IllegalStateException("Command parsing connect command");
                        }
                        String receiverHost = in.next();
                        int receiverPort = in.nextInt();
                        connection = serverApplication.getConnection(receiverHost, receiverPort);
                    } else {
                        throw new IllegalArgumentException("Illegal command: " + line);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }
}
