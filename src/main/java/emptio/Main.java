package emptio;

import emptio.adapters.rest.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to Emptio!");

        wireDependencies();

        try {
            Server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void wireDependencies() {
    }
}