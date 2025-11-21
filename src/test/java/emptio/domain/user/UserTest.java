package emptio.domain.user;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.validators.*;
import emptio.serialization.IdService;
import emptio.serialization.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    Repository<User> userRepository;
    IdService idService;
    Set<Validator<User>> validators;
    UserServiceFacade userServiceFacade;
    UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        idService = new IdService();
        validators = new HashSet<>();
        userServiceFacade =  new UserServiceFacade();
        userService = new UserService(validators, userRepository, idService);
    }

    @Test
    void shouldValidateEmail() {
        validators.add(new EmailValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withEmail("abdulla!pjatk").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withEmail(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withEmail("").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withEmail("abdulla@pjatk").newUser();
    }

    @Test
    void shouldValidateLogin() {
        LoginValidator loginValidator = new LoginValidator();
        validators.add(loginValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withLogin("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withLogin(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withLogin(stringOfGivenLength(loginValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withLogin(stringOfGivenLength(loginValidator.maxCharacters - 1)).newUser();
        userServiceFacade.withLogin("prof-abdulla").newUser();
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withName("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withName(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withName(stringOfGivenLength(nameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withName(stringOfGivenLength(nameValidator.maxCharacters - 1)).newUser();
        userServiceFacade.withName("Mohamed").newUser();
    }

    @Test
    void shouldValidatePassword() {
        PasswordValidator passwordValidator = new PasswordValidator();
        validators.add(passwordValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPassword("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPassword(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPassword(stringOfGivenLength(passwordValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPassword("password").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withPassword(stringOfGivenLength(passwordValidator.maxCharacters - 1)).newUser();
        userServiceFacade.withPassword("GigaChadStrongPassword").newUser();
    }

    @Test
    void shouldValidatePhoneNumber() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        validators.add(phoneNumberValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPhoneNumber("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPhoneNumber(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.minCharacters - 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withPhoneNumber("abcdefghi").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withPhoneNumber("678098152").newUser();
    }

    @Test
    void shouldValidateSurname() {
        SurnameValidator surnameValidator = new SurnameValidator();
        validators.add(surnameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withSurname("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withSurname(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userServiceFacade.withSurname(stringOfGivenLength(surnameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userServiceFacade.withSurname("Abdulla").newUser();
    }

    private class UserServiceFacade {

        private String name;
        private String surname;
        private String email;
        private String phoneNumber;
        private String login;
        private String password;

        User newUser() {
            return userService.newUser(name, surname, email, phoneNumber, login, password);
        }

        UserServiceFacade withName(String name) {
            this.name = name;
            return this;
        }
        UserServiceFacade withSurname(String surname) {
            this.surname = surname;
            return this;
        }
        UserServiceFacade withEmail(String email) {
            this.email = email;
            return this;
        }
        UserServiceFacade withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        UserServiceFacade withLogin(String login) {
            this.login = login;
            return this;
        }
        UserServiceFacade withPassword(String password) {
            this.password = password;
            return this;
        }
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


