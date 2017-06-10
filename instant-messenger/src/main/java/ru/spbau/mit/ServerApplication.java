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
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * @brief ServerApplication - application class for peer-to-peer chatting.
 */
public final class ServerApplication {
    private static final Logger LOGGER = LogManager.getLogger("server");
    private final SimpleMessageService service;
    private final Server server;
    private final String name;
    private final String host;
    private final int port;
    private volatile Connection connection;

    /**
     * @param host not used now.
     * @param port serving port.
     * @throws IOException on failing to start and server binding.
     */
    private ServerApplication(String host, int port, String name) throws IOException {
        this.host = host;
        this.port = port;
        // TODO use host for creating service daemon.
        this.service = new SimpleMessageService();
        server = ServerBuilder.forPort(port)
                .addService(service)
                .build()
                .start();
        LOGGER.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the LOGGER may have been reset by its JVM shutdown hook.
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

    private String getHost() {
        return host;
    }

    private int getPort() {
        return port;
    }

    /**
     * @brief Connection class incapsulating grpc logic behind message processing.
     */
    private class Connection {
//        private final ManagedChannel channel;
        private final StreamObserver<Message> requestObserver;

        Connection(String host, int port) {
            LOGGER.debug("Setting ManagedChannelBuilder...");
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext(true)
                    .build();
            LOGGER.debug("Setting ManagedChannelBuilder... OK");

            LOGGER.debug("Setting MessageServiceStub...");
            MessageServiceGrpc.MessageServiceStub stub = MessageServiceGrpc.newStub(channel);
            LOGGER.debug("Setting MessageServiceStub... OK");

            StreamObserver<Message> responseObserver = service.getResponseObserver();

            if (!host.equals(getHost()) || port != getPort()) {
                LOGGER.debug("Setting chat connection...");
                this.requestObserver = stub.chat(responseObserver);
                LOGGER.debug("Setting chat connection... OK");
            } else {
                this.requestObserver = service.getRequestObserver();
            }
        }

        Connection(StreamObserver<Message> requestObserver) {
            this.requestObserver = requestObserver;
        }

        public void send(String text) {
            LOGGER.info("sending message: \n" + text);
            Message message = Message.newBuilder()
                    .setText(text)
                    .setTime(currentDateString())
                    .setSender(name)
                    .build();
            requestObserver.onNext(message);
            LOGGER.info("message sent: \n" + message);
        }

        /**
         * soft way to close connection.
         */
        public void close() {
            requestObserver.onCompleted();
        }
    }

    /**
     * Open outgoing connection in order to write messages to specified receiver.
     * @param receiverHost host to which to connect.
     * @param receiverPort port to which to connect.
     */
    private synchronized void connect(String receiverHost,
                                      int receiverPort,
                                      StreamObserver<Message> requestObserver) {
        if (isConnected()) {
            throw new IllegalStateException("Already connected");
        }
        String infoMessage = "Trying to connect "
                + serverInfo(getHost(), getPort())
                + " to " + serverInfo(receiverHost, receiverPort);
        LOGGER.info(infoMessage);
        if (Objects.isNull(requestObserver)) {
            connection = new Connection(receiverHost, receiverPort);
        } else {
            connection = new Connection(requestObserver);
        }
        LOGGER.info(infoMessage + " OK");
        System.out.println("connected");
    }

    /**
     * @brief close outgoing connection initiated in @a connect
     */
    private synchronized void disconnect() {
        if (!isConnected()) {
            return;
        }
        // Guard against self-chat-connection.
        Connection oldConnection = connection;
        connection = null;
        oldConnection.close();
        System.out.println("disconnected");
    }

    /**
     * @return if has outgoing connection.
     */
    private boolean isConnected() {
        return !Objects.isNull(connection);
    }

    /**
     * for logging purposes.
     * @param host host
     * @param port port
     * @return logging info string.
     */
    private static String serverInfo(String host, int port) {
        return host + ":" + port;
    }

    /**
     * for logging purposes.
     * @return date string in chosen (in compile-time) format.
     */
    private static String currentDateString() {
        return new Date(System.currentTimeMillis()).toString();
    }

    /**
     * Application entry point.
     * @param args
     */
    public static void main(String[] args) {
        // Parsing command line arguments.
        final String hostOptionString = "host";
        final String portOptionString = "port";
        final String nameOptionString = "name";

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
            LOGGER.error("Error while trying to parse options: " + e.getMessage());
            System.err.println(e.getMessage());
            // TODO make help message display required options in USAGE string.
            formatter.printHelp("instant-messenger", options);
            System.exit(1);
        }

        // TODO use options to get parsed values.
        String host = cmd.getOptionValue(hostOptionString);
        int port = Integer.valueOf(cmd.getOptionValue(portOptionString));
        final String name = cmd.getOptionValue(nameOptionString);
        LOGGER.debug("Parsed options: "
                + "host: " + host
                + ", port: " + port
                + ", name: " + name);
        ServerApplication serverApplication = null;
        try {
            serverApplication = new ServerApplication(host, port, name);
        } catch (IOException e) {
            LOGGER.error("Error while trying to create server application: " + e.getMessage());
            System.err.println(e.getMessage());
            System.exit(1);
        }

        final String connectCommand = "connect";
        final String disconnectCommand = "disconnect";
        final String exitCommand = "exit";
        String messageFormatHelp =
                "connect <host> <port>\n"
                        + "disconnect\n"
                        + "exit\n";
        String additionalHelp = "NOTES:\n"
                + "1. exit command available only if disconnected.\n"
                + "2. disconnect is the only command available in connected mode.";

        System.out.println(messageFormatHelp);
        System.out.println(additionalHelp);

        // Main loop.
        final Scanner stdin = new Scanner(System.in);
        while (true) {
            String line = stdin.nextLine();
            try {
                if (line.equals(exitCommand)) {
                    break;
                } else if (serverApplication.isConnected()) {
                    if (line.equals(disconnectCommand)) {
                        serverApplication.disconnect();
                    } else {
                        serverApplication.connection.send(line);
                    }
                } else {
                    if (line.indexOf(connectCommand) == 0) {
                        Scanner in = new Scanner(line);
                        String s = in.next(connectCommand);
                        if (!s.equals(connectCommand)) {
                            throw new IllegalStateException("Error while parsing connect command");
                        }
                        String receiverHost = in.next();
                        int receiverPort = in.nextInt();
                        serverApplication.connect(receiverHost, receiverPort, null);
                    } else {
                        throw new IllegalArgumentException("Illegal command: " + line);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * SimpleMessageService represents remote procedure call facade.
     */
    private class SimpleMessageService extends MessageServiceGrpc.MessageServiceImplBase {
        private final Logger serviceLogger = LogManager.getLogger("service");

        private StreamObserver<Message> getRequestObserver() {
            return new StreamObserver<Message>() {
                @Override
                public void onNext(Message request) {
                    String from = request.getSender();
                    String message = from + " wrote: " + request.getText();
                    serviceLogger.info("received message: \n" + request);
                    System.out.println(message);
                }

                @Override
                public void onError(Throwable t) {
                    serviceLogger.error(t.getMessage());
                    System.err.println("ERROR: " + t.getMessage());
                    disconnect();
                }

                @Override
                public void onCompleted() {
                    serviceLogger.info("onCompleted, name: " + name);
                    disconnect();
                }
            };
        }

        private StreamObserver<Message> getResponseObserver() {
            return new StreamObserver<Message>() {
                @Override
                public void onNext(Message value) {
                    serviceLogger.info("received message: \n" + value);
                    System.out.println(value.getSender() + " wrote: " + value.getText());
                }

                @Override
                public void onError(Throwable t) {
                    serviceLogger.error(t.getMessage());
                    System.out.println("ERROR: " + t.getMessage());
                    disconnect();
                }

                @Override
                public void onCompleted() {
                    LOGGER.info("onCompleted, name: " + name);
                }
            };
        }

        @Override
        public StreamObserver<Message> chat(final StreamObserver<Message> responseObserver) {
            try {
                connect(null, 0, responseObserver);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
            return getRequestObserver();
        }
    }
}
