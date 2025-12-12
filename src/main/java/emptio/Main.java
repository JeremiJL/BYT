package emptio;

import emptio.adapters.rest.Server;
import emptio.domain.CredentialsRepository;
import emptio.domain.Repository;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import emptio.domain.user.validators.LoginValidator;
import emptio.domain.user.validators.PasswordValidator;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Greet
        System.out.println("Welcome to Emptio!");

        // Wire dependencies

        // Repositories
        Repository<User> userRepository = new InMemoryRepository<>();
        CredentialsRepository credentialsRepository = new InMemoryCredentialsRepository();

        // Entity services
        UserService userService = new UserService(userRepository, credentialsRepository,new LoginValidator(), new PasswordValidator());

        // Server - Root dependency
        Server server = new Server(userService);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}