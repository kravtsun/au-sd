package ru.spbau.mit;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerApplication {
    private static final Logger logger = LogManager.getLogger("server");

    private Server server;

    /**
     * @param host not used now.
     * @param port serving port.
     * @throws IOException
     */
    private ServerApplication(String host, int port) throws IOException {
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
//                interrupt(); // alternative to Thread.stop()?
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) {
        String hostOptionString = "host";
        String portOptionString = "port";

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

        options.addOption(hostOption);
        options.addOption(portOption);
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
            formatter.printHelp("instant-messenger-server", options);
            System.exit(1);
        }

        // TODO use options to get parsed values.
        String host = cmd.getOptionValue(hostOptionString);
        int port = Integer.valueOf (cmd.getOptionValue(portOptionString));
        logger.debug("Parsed options: " + "host: " + host + ", port: " + port);
        ServerApplication serverApplication = null;
        try {
            serverApplication = new ServerApplication(host, port);
        } catch (IOException e) {
            logger.error("Error while trying to create server application: " + e.getMessage());
            System.err.println(e.getMessage());
            System.exit(1);
        }

        logger.debug("Server start successful, hanging up");
        try {
            serverApplication.blockUntilShutdown();
        } catch (InterruptedException e) {
            logger.info("interrupt signal received: " + e.getMessage());
        }
    }

}
