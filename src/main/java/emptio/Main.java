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

        System.out.println("Welcome to Emptio!");

        wireDependencies();

        try {
            Server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void wireDependencies() {

        // Repositories
        Repository<User> userRepository = new InMemoryRepository<>();
        CredentialsRepository credentialsRepository = new InMemoryCredentialsRepository();

        // Entity services
        UserService.setUserRepository(userRepository);
        UserService.setCredentialsRepository(credentialsRepository);
        UserService.setValidators(new LoginValidator(), new PasswordValidator());

    }
}