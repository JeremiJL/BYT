package emptio;

import emptio.adapters.rest.Server;
import emptio.domain.CredentialsRepository;
import emptio.domain.DomainRepository;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import emptio.domain.user.validators.LoginValidator;
import emptio.domain.user.validators.PasswordValidator;
import emptio.serialization.DiskDomainRepository;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryDomainRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


        // TMP

        DomainRepository<User> repository = new DiskDomainRepository<>(User.class.getName());


        // Greet
        System.out.println("Welcome to Emptio!");

        // Wire dependencies

        // Repositories
        DomainRepository<User> userRepository = new DiskDomainRepository<>(User.class.getName());
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