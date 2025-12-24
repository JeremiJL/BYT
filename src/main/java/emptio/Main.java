package emptio;

import emptio.adapters.rest.Server;
import emptio.common.DasSymetricEncryptor;
import emptio.common.SymetricEncryptor;
import emptio.domain.CredentialsRepository;
import emptio.domain.DomainRepository;
import emptio.domain.UserRepository;
import emptio.domain.user.*;
import emptio.domain.user.validators.*;
import emptio.serialization.DiskCredentialsRepository;
import emptio.serialization.DiskDomainRepository;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Greet
        System.out.println("Welcome to Emptio!");

        // Wire dependencies

        // Tools
        SymetricEncryptor symmetricEncryptor = new DasSymetricEncryptor();


//        String encrypted = symmetricEncryptor.encrypt(String.valueOf(0));
//        String decrypted = symmetricEncryptor.decrypt(encrypted);

        // Repositories
        UserRepository<User> userRepository = new UserRepository<>(
                new DiskDomainRepository<>(Shopper.class),
                new DiskDomainRepository<>(Merchant.class),
                new DiskDomainRepository<>(Advertiser.class),
                new DiskCredentialsRepository()
        );

        CredentialsRepository credentialsRepository = new DiskCredentialsRepository();

        // Entity services
        UserService userService = new UserService(userRepository,
                new LoginValidator(), new PasswordValidator(), new AddressValidator(new PostalCodeValidator()),
                new EmailValidator(), new NameValidator(), new PhoneNumberValidator(), new SurnameValidator());

        // Server - Root dependency
        Server server = new Server(userService, symmetricEncryptor);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}