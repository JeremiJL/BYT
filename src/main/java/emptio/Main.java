package emptio;

import emptio.adapters.rest.Server;
import emptio.domain.CredentialsRepository;
import emptio.domain.DomainRepository;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import emptio.domain.user.validators.*;
import emptio.serialization.DiskCredentialsRepository;
import emptio.serialization.DiskDomainRepository;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryDomainRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {


        // TMP

        DomainRepository<User> repository = new DiskDomainRepository<>(User.class);


        // Greet
        System.out.println("Welcome to Emptio!");

        // Wire dependencies

        // Repositories
        DomainRepository<User> userRepository = new DiskDomainRepository<>(User.class);
        CredentialsRepository credentialsRepository = new DiskCredentialsRepository();

        // Entity services
        UserService userService = new UserService(userRepository, credentialsRepository,
                new LoginValidator(), new PasswordValidator(), new AddressValidator(new PostalCodeValidator()),
                new EmailValidator(), new NameValidator(), new PhoneNumberValidator(), new SurnameValidator());

        // Server - Root dependency
        Server server = new Server(userService);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}