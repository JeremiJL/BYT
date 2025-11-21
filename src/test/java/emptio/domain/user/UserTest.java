package emptio.domain.user;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.validators.EmailValidator;
import emptio.domain.user.validators.PasswordValidator;
import emptio.serialization.IdService;
import emptio.serialization.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    Repository<User> userRepository = new InMemoryUserRepository();
    IdService idService = new IdService();


    @Test
    void shouldValidateEmail() {
        // Set up email validator
        Set<Validator<User>> validators = new HashSet<>();
        validators.add(new EmailValidator());
        // Set up user service
        UserService userService = new UserService(validators, userRepository, idService);

        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenEmail(userService,"abdulla!pjatk");
        });
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenEmail(userService,null);
        });
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenEmail(userService,"");
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        createUserWithGivenEmail(userService,"abdulla@pjatk");
    }

    private void createUserWithGivenEmail(UserService userService, String email) {
        userService.newUser(null,null,email,null,null,null);
    }

    @Test
    void shouldValidatePassword() {
        // Set up email validator
        Set<Validator<User>> validators = new HashSet<>();
        validators.add(new PasswordValidator());
        // Set up user service
        UserService userService = new UserService(validators, userRepository, idService);

        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenPassword(userService,"password");
        });
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenPassword(userService,null);
        });
        assertThrows(ValidationException.class, () -> {
            createUserWithGivenPassword(userService,"");
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        createUserWithGivenPassword(userService,"AJNSIEUP!2joz09w");
    }

    private void createUserWithGivenPassword(UserService userService, String password) {
        userService.newUser(null,null,null,null,null,password);
    }
}
