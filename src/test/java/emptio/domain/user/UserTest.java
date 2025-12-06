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
    UserBuilder userBuilder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryRepository<>();
        validators = new HashSet<>();
        userBuilder =  new UserBuilder();
        UserService.setUserRepository(userRepository);
        UserService.setValidators(validators);
    }

    @Test
    void shouldValidateEmail() {
        validators.add(new EmailValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withEmail("abdulla!pjatk").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withEmail(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withEmail("").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withEmail("abdulla@pjatk").newUser();
    }

    @Test
    void shouldValidateLogin() {
        LoginValidator loginValidator = new LoginValidator();
        validators.add(loginValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withLogin("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withLogin(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withLogin(stringOfGivenLength(loginValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withLogin(stringOfGivenLength(loginValidator.maxCharacters - 1)).newUser();
        userBuilder.withLogin("prof-abdulla").newUser();
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withName("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withName(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withName(stringOfGivenLength(nameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withName(stringOfGivenLength(nameValidator.maxCharacters - 1)).newUser();
        userBuilder.withName("Mohamed").newUser();
    }

    @Test
    void shouldValidatePassword() {
        PasswordValidator passwordValidator = new PasswordValidator();
        validators.add(passwordValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPassword("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPassword(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPassword(stringOfGivenLength(passwordValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPassword("password").newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withPassword(stringOfGivenLength(passwordValidator.maxCharacters - 1)).newUser();
        userBuilder.withPassword("GigaChadStrongPassword").newUser();
    }

    @Test
    void shouldValidatePhoneNumber() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        validators.add(phoneNumberValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPhoneNumber("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPhoneNumber(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.maxCharacters + 1)).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withPhoneNumber(stringOfGivenLength(phoneNumberValidator.minCharacters - 1)).newUser();
        });
//        TODO : This case should have a personal validator.
//        assertThrows(ValidationException.class, () -> {
//            userServiceBuilder.withPhoneNumber("abcdefghi").newUser();
//        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withPhoneNumber("678098152").newUser();
    }

    @Test
    void shouldValidateSurname() {
        SurnameValidator surnameValidator = new SurnameValidator();
        validators.add(surnameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.withSurname("").newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withSurname(null).newUser();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.withSurname(stringOfGivenLength(surnameValidator.maxCharacters + 1)).newUser();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.withSurname("Abdulla").newUser();
    }

    private class UserBuilder {

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

        UserBuilder withName(String name) {
            this.name = name;
            return this;
        }
        UserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }
        UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        UserBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        UserBuilder withLogin(String login) {
            this.login = login;
            return this;
        }
        UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
        UserBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


