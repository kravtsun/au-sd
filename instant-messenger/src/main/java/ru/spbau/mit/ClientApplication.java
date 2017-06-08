package ru.spbau.mit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientApplication {
    private static final Logger logger = LogManager.getLogger("client");

    private final ManagedChannel channel;
    private final MessageServiceGrpc.MessageServiceBlockingStub blockingStub;
    private final String name;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public ClientApplication(String host, int port, String name) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true).build();
        this.blockingStub = MessageServiceGrpc.newBlockingStub(channel);
        this.name = name;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sentMessage(String recipient, String text) {
        Message request = Message.newBuilder()
                .setText(text)
                .setTime(new Date(System.currentTimeMillis()).toString())
                .setSender(getName())
                .setReceiver(recipient)
                .build();
        logger.info("sending message: \n" + request);
        Message response = null;

        try {
            response = blockingStub.chat(request);
        } catch (StatusRuntimeException e) {
            logger.error("RPC error: " + e.getMessage());
        }
        logger.info("received message: " + response);
    }

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

        try {
            client.sentMessage("server", "hello");
        }
        finally {
            client.shutdown();
        }
    }

    public String getName() {
        return name;
    }
}
