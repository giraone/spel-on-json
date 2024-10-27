package com.giraone.templating;

import java.nio.charset.StandardCharsets;

public class SpelConverterCli {

    private String inputFile;
    private String outputFile;
    private boolean encrypt = true;
    private char[] secret = "secret".toCharArray();
    private byte[] salt = "salt".getBytes(StandardCharsets.UTF_8);

    private void run(String[] args) throws Exception {

        if (args.length == 0) {
            usage();
        }
        for (String arg : args) {
            switch (arg) {
                case "--secret" -> secret = arg.toCharArray();
                case "--salt" -> salt = arg.getBytes(StandardCharsets.UTF_8);
                case "--decrypt" -> encrypt = false;
                case "--help" -> usage();
                default -> {
                    if (inputFile == null) {
                        inputFile = arg;
                    } else {
                        outputFile = arg;
                    }
                }
            }
        }
    }

    private void usage() {
        System.out.println("Usage: java -jar  com.giraone.crypto.CryptoCli [--help] [--decrypt] [--secret <secret>] [--salt <salt>] [<input-file>] [<output-file>]");
        System.out.println("If <input-file> is not given STDIN/STDOUT is used");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {

        new SpelConverterCli().run(args);
    }
}
