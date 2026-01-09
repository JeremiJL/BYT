package emptio;

import emptio.adapters.rest.Server;
import emptio.common.DasSymetricEncryptor;
import emptio.common.Enviorment;
import emptio.common.SymetricEncryptor;
import emptio.domain.ProductRepository;
import emptio.domain.UserRepository;
import emptio.domain.product.Product;
import emptio.domain.product.ProductService;
import emptio.domain.product.validators.*;
import emptio.domain.user.*;
import emptio.domain.user.validators.*;
import emptio.search.ProductQuerySearch;
import emptio.serialization.DiskCredentialsRepository;
import emptio.serialization.DiskDomainRepository;
import emptio.search.DiskSearchRepository;

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
        ProductRepository productRepository = new ProductRepository(
                new DiskDomainRepository<>(Product.class, ENV),
                new ProductRepository(
                        new DiskDomainRepository<>(Product.class, ENV),
                        new DiskSearchRepository<>(Product.class, ENV) {
                            @Override
                            public String getFeature(Product i) {
                                return i.getCategory().toString();
                            }
                        }
                )
        );

        // Search engines
        ProductQuerySearch productQuerySearch = new ProductQuerySearch(productRepository);

        // Entity services
        UserService userService = new UserService(userRepository,
                new LoginValidator(), new PasswordValidator(), new AddressValidator(new PostalCodeValidator()),
                new EmailValidator(), new NameValidator(), new PhoneNumberValidator(), new SurnameValidator());

        ProductService productService = new ProductService(productRepository, userService,
          new CategoryValidator(), new CountOnMarketplaceValidator(), new DescriptionValidator(),
          new ImageValidator(), new InteractionsValidator(), new OrderingWeightValidator(),
          new PriceValidator(), new TitleValidator()
        );

        // Server - Root dependency
        Server server = new Server(port, userService, productService, symmetricEncryptor, productQuerySearch);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}