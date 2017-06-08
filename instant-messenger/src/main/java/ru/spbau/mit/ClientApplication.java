package ru.spbau.mit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientApplication {
    private static final Logger logger = LogManager.getLogger("client");

    private final String name;
    private final ManagedChannel channel;
    private final StreamObserver<Message> requestObserver;

    public ClientApplication(String host, int port, String name) {
        this.name = name;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true).build();

        MessageServiceGrpc.MessageServiceStub stub = MessageServiceGrpc.newStub(channel);

        StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                logger.info("received message: \n" + value);
                String receiver = value.getReceiver();
                if (!receiver.equals(name)) {
                    String errorMessage = "Invalid receiver: " + receiver + " for name: " + name;
                    logger.error(errorMessage);
                    throw new IllegalStateException(errorMessage);
                }
                System.out.println(value.getSender() + " wrote: " + value.getText());
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
                System.out.println("ERROR: " + t.getMessage());
                shutdown(1);
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted, name: " + name);
                shutdown(0);
            }
        };

        this.requestObserver = stub.chat(responseObserver);
        sent("service", "init");
    }

    public void shutdown(int exitCode) {
        try {
            System.out.println("Wait for it...");
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        System.exit(exitCode);
    }

    public void sent(String recipient, String text) {
        requestObserver.onNext(Message.newBuilder()
                .setText(text)
                .setTime(SimpleMessageService.currentDateString())
                .setSender(name)
                .setReceiver(recipient)
                .build());
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
        final String name = cmd.getOptionValue(nameOptionString);
        logger.debug("Parsed options: " + "host: " + host + ", port: " + port);

        ClientApplication client = null;
        try {
            client = new ClientApplication(host, port, name);
        }
        catch (Throwable t) {
            String errorMessage = "Error while initializing client: " + t.getMessage();
            logger.error(errorMessage);
            System.err.println(errorMessage);
            System.exit(1);
        }

        String messageFormatHelp = "Message format: <message-receiver-name>: message-text\n" +
            "where <message-receiver-name> is non-empty sequence of colon-less symbols\n" +
            "submit \"exit\" to exit.";
        System.out.println(messageFormatHelp);

        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.nextLine();
            if (line.equals("exit")) {
                break;
            }
            try {
                int i = line.indexOf(":");
                String receiver = line.substring(0, i);
                String message = line.substring(i+1);
                client.sent(receiver, message);
            }
            catch (Exception e) { // TODO make caught exception more specific.
                String errorMessage = "Invalid message in line: " + line;
                logger.error(errorMessage);
                System.out.println(errorMessage);
                System.out.println(messageFormatHelp);
            }
        }
        client.shutdown(0);
    }

//    public String getName() {
//        return name;
//    }
}
