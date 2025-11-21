package emptio.domain.user;

import emptio.domain.ValidationException;
import emptio.domain.Validator;

import java.time.LocalDate;
import java.util.Set;

public class UserService {

    Set<Validator<User>> validators;
    UserRepository userRepository;

    public UserService(Set<Validator<User>> validators) {
        this.validators = validators;
    }

    public User newUser(String name, String surname,
                        String email, String number,
                        String login, String password) throws ValidationException
    {
        LocalDate today = today();
        User user = new User(name, surname, email, number, login, password, today);

        try {
            validators.forEach(validator -> validator.validate(user));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a user with given parameters, cause : " + e.getMessage());
        }

        return userRepository.add(user);
    }

    private LocalDate today() {
        return LocalDate.now();
    }
}

