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
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @brief ServerApplication - application class for peer-to-peer chatting.
 */
public final class ServerApplication {
    private static final Logger LOGGER = LogManager.getLogger("server");
    private Server server;
    private final String name;
    private Connection connection;

    /**
     * @param host not used now.
     * @param port serving port.
     * @throws IOException on failing to start and server binding.
     */
    private ServerApplication(String host, int port, String name) throws IOException {
        // TODO use host for creating service daemon.
        server = ServerBuilder.forPort(port)
                .addService(new SimpleMessageService())
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

    /**
     * @brief Connection class incapsulating grpc logic behind message processing.
     */
    private class Connection {
        private final ManagedChannel channel;
        private final StreamObserver<Message> requestObserver;

        Connection(String host, int port) {
            LOGGER.debug("Setting ManagedChannelBuilder...");
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext(true)
                    .build();

            LOGGER.debug("Setting MessageServiceStub...");
            MessageServiceGrpc.MessageServiceStub stub = MessageServiceGrpc.newStub(channel);

            StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
                @Override
                public void onNext(Message value) {
                    LOGGER.info("received message: \n" + value);
                    System.out.println(value.getSender() + " a   wrote: " + value.getText());
                }

                @Override
                public void onError(Throwable t) {
                    LOGGER.error(t.getMessage());
                    System.out.println("ERROR: " + t.getMessage());
                    disconnect();
                }

                @Override
                public void onCompleted() {
                    LOGGER.info("onCompleted, name: " + name);
                    System.out.println("disconnected");
                }
            };

            LOGGER.debug("Setting chat connection...");
            requestObserver = stub.chat(responseObserver);
        }

        public void send(String text) {
            LOGGER.info("sending message: \n" + text);
            Message message = Message.newBuilder()
                    .setText(text)
                    .setTime(SimpleMessageService.currentDateString())
                    .setSender(name)
                    .build();
            requestObserver.onNext(message);
            LOGGER.info("message sent: \n" + message);

        }

        /**
         * hard way to close connection.
         */
        public void shutdown() {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
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
    private void connect(String receiverHost, int receiverPort) {
        connection = new Connection(receiverHost, receiverPort);
    }

    /**
     * @brief close outgoing connection initiated in @a connect
     */
    private void disconnect() {
        if (!isConnected()) {
            return;
        }
        connection.close();
        connection = null;
    }

    /**
     * @return if has outgoing connection.
     */
    private boolean isConnected() {
        return !Objects.isNull(connection);
    }

    public static void main(String[] args) {
        // Parsing command line arguments.
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
                + "1. exit command available only if disconnected."
                + "2. disconnect is the only command available in connected mode.";

        System.out.println(messageFormatHelp);
        System.out.println(additionalHelp);

        // Main loop.
        Scanner stdin = new Scanner(System.in);
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
                        serverApplication.connect(receiverHost, receiverPort);
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
}
