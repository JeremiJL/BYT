package emptio.serialization;

import emptio.builder.AddressBuilder;
import emptio.builder.UserBuilder;
import emptio.common.Enviorment;
import emptio.domain.UserRepository;
import emptio.domain.Validator;
import emptio.domain.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

public class UserDiskSerializationTest {

    UserService userService;
    UserBuilder userBuilder;
    UserRepository<User> userRepository;
    final Enviorment ENV = Enviorment.TEST;

    @BeforeEach
    void setUp() {
        Set<Validator<User>> validators = Collections.emptySet();
        this.userRepository = new UserRepository<>(
                new DiskDomainRepository<>(Shopper.class, ENV),
                new DiskDomainRepository<>(Merchant.class, ENV),
                new DiskDomainRepository<>(Advertiser.class, ENV),
                new DiskCredentialsRepository(ENV)
        );

        this.userService = new UserService(
                userRepository, validators
        );
        this.userBuilder = new UserBuilder(userService, new AddressBuilder());
    }

    @AfterEach
    void tearDown() {
        this.userRepository.tearDown();
    }

    @Test
    public void updateTest() {
        // Create and serialize a new shopper account
        userBuilder.setAccountType(AccountType.SHOPPER);
        userBuilder.setLogin("123");
        User user = userBuilder.build();

        // Find a shopper account
        User foundUser = userService.getUser(user.getId());

        // Update a shopper account
        User updatedUser = userService.updateUser(foundUser, "MyNewName", foundUser.getSurname(), foundUser.getEmail(),
                foundUser.getPhoneNumber(), foundUser.getLogin(), foundUser.getPassword(), foundUser.getAddress());


        System.out.println(foundUser.getName());
        System.out.println(updatedUser.getName());
    }
}
