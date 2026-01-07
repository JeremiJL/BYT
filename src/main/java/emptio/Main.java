package emptio;

import emptio.adapters.rest.Server;
import emptio.common.DasSymetricEncryptor;
import emptio.common.Enviorment;
import emptio.common.SymetricEncryptor;
import emptio.domain.UserRepository;
import emptio.domain.user.*;
import emptio.domain.user.validators.*;
import emptio.serialization.DiskCredentialsRepository;
import emptio.serialization.DiskDomainRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Variables
        final Enviorment ENV = Enviorment.DEV;
        final int port = 8080;

        // Greet
        System.out.println("Welcome to Emptio! Environment is set to "
                + ENV.name() + ". " +
                "Listening on port " + port + ". For local development open your browser at: localhost:" + port + ". Good luck!");

        // Wire dependencies

        // Tools
        SymetricEncryptor symmetricEncryptor = new DasSymetricEncryptor();

        // Repositories
        UserRepository<User> userRepository = new UserRepository<>(
                new DiskDomainRepository<>(Shopper.class, ENV),
                new DiskDomainRepository<>(Merchant.class, ENV),
                new DiskDomainRepository<>(Advertiser.class, ENV),
                new DiskCredentialsRepository(ENV)
        );

        // Entity services
        UserService userService = new UserService(userRepository,
                new LoginValidator(), new PasswordValidator(), new AddressValidator(new PostalCodeValidator()),
                new EmailValidator(), new NameValidator(), new PhoneNumberValidator(), new SurnameValidator());

        // Server - Root dependency
        Server server = new Server(port, userService, symmetricEncryptor);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}