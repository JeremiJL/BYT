package emptio.domain.user;


import emptio.builder.AddressBuilder;
import emptio.builder.UserBuilder;
import emptio.domain.CredentialsRepository;
import emptio.domain.DomainRepository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.validators.*;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    DomainRepository<User> userRepository;
    CredentialsRepository credentialsRepository;
    Set<Validator<User>> validators;

    UserService userService;
    UserBuilder userBuilder;
    AddressBuilder addressBuilder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryDomainRepository<>();
        credentialsRepository = new InMemoryCredentialsRepository();
        validators = new HashSet<>();
        userService = new UserService(userRepository,credentialsRepository,validators);
        addressBuilder = new AddressBuilder();
        userBuilder = new UserBuilder(userService, addressBuilder);
    }

    @Test
    void shouldValidateEmail() {
        validators.add(new EmailValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setEmail("abdulla!pjatk");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setEmail(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setEmail("");
            userBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setEmail("abdulla@pjatk");
        userBuilder.build();
    }

    @Test
    void shouldValidateLogin() {
        LoginValidator loginValidator = new LoginValidator();
        validators.add(loginValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setLogin("");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setLogin(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setLogin(stringOfGivenLength(loginValidator.maxCharacters + 1));
            userBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setLogin(stringOfGivenLength(loginValidator.maxCharacters - 1));
        userBuilder.build();
        userBuilder.setLogin("prof-abdulla");
        userBuilder.build();
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setName("");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setName(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setName(stringOfGivenLength(nameValidator.maxCharacters + 1));
            userBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setName(stringOfGivenLength(nameValidator.maxCharacters - 1));
        userBuilder.build();
    }

    @Test
    void shouldValidatePassword() {
        PasswordValidator passwordValidator = new PasswordValidator();
        validators.add(passwordValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPassword("");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setPassword(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPassword(stringOfGivenLength(passwordValidator.maxCharacters + 1));
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPassword("password");
            userBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setPassword(stringOfGivenLength(passwordValidator.maxCharacters - 1));
        userBuilder.build();
    }

    @Test
    void shouldValidatePhoneNumber() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        validators.add(phoneNumberValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPhoneNumber("");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setPhoneNumber(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPhoneNumber(stringOfGivenLength(phoneNumberValidator.maxCharacters + 1));
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setPhoneNumber(stringOfGivenLength(phoneNumberValidator.minCharacters - 1));
            userBuilder.build();
        });
//        TODO : This case should have a personal validator.
//        assertThrows(ValidationException.class, () -> {
//            userServiceBuilder.setPhoneNumber();("abcdefghi").newUser();
//        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setPhoneNumber("678098152");
        userBuilder.build();
    }

    @Test
    void shouldValidateSurname() {
        SurnameValidator surnameValidator = new SurnameValidator();
        validators.add(surnameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            userBuilder.setSurname("");
            userBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            userBuilder.setSurname(null);
            userBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            userBuilder.setSurname(stringOfGivenLength(surnameValidator.maxCharacters + 1));
            userBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        userBuilder.setSurname("Abdulla");
        userBuilder.build();
    }


    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


