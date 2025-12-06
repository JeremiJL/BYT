package emptio;

import emptio.adapters.rest.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Welcome to Emptio!");

        try {
            Server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}