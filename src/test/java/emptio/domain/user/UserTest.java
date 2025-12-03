package emptio.domain.user;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.validators.*;
import emptio.serialization.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    Repository<User> userRepository;
    Set<Validator<User>> validators;

    UserService userService;
    UserServiceBuilder userServiceBuilder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryRepository<>();
        validators = new HashSet<>();

        userServiceBuilder =  new UserServiceBuilder();
        userService = new UserService(validators, userRepository);
    }

    @Test
    void shouldValidateEmail() {
        validators.add(new EmailValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withEmail("abdulla!pjatk").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withEmail(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withEmail("").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withEmail("abdulla@pjatk").newUser();
    }

    @Test
    void shouldValidateLogin() {
        LoginValidator loginValidator = new LoginValidator();
        validators.add(loginValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withLogin("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withLogin(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withLogin(stringOfGivenLength(loginValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withLogin(stringOfGivenLength(loginValidator.maxCharacters - 1)).newUser();
        userServiceBuilder.withLogin("prof-abdulla").newUser();
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withName("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withName(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withName(stringOfGivenLength(nameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withName(stringOfGivenLength(nameValidator.maxCharacters - 1)).newUser();
        userServiceBuilder.withName("Mohamed").newUser();
    }

    @Test
    void shouldValidatePassword() {
        PasswordValidator passwordValidator = new PasswordValidator();
        validators.add(passwordValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPassword("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPassword(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPassword(stringOfGivenLength(passwordValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPassword("password").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withPassword(stringOfGivenLength(passwordValidator.maxCharacters - 1)).newUser();
        userServiceBuilder.withPassword("GigaChadStrongPassword").newUser();
    }

    @Test
    void shouldValidatePhoneNumber() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        validators.add(phoneNumberValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPhoneNumber("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPhoneNumber(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.minCharacters - 1)).newUser();
        });
//        TODO : This case should have a personal validator.
//        assertThrows(ValidationException.class, () -> {
//            userServiceBuilder.withPhoneNumber("abcdefghi").newUser();
//        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withPhoneNumber("678098152").newUser();
    }

    @Test
    void shouldValidateSurname() {
        SurnameValidator surnameValidator = new SurnameValidator();
        validators.add(surnameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withSurname("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withSurname(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceBuilder.withSurname(stringOfGivenLength(surnameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceBuilder.withSurname("Abdulla").newUser();
    }

    private class UserServiceBuilder {

        private String name;
        private String surname;
        private String email;
        private String phoneNumber;
        private String login;
        private String password;
        private Address address;

        User newUser() {
            return userService.newUser(name, surname, email, phoneNumber, login, password, address);
        }

        UserServiceBuilder withName(String name) {
            this.name = name;
            return this;
        }
        UserServiceBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }
        UserServiceBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        UserServiceBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        UserServiceBuilder withLogin(String login) {
            this.login = login;
            return this;
        }
        UserServiceBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
        UserServiceBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


