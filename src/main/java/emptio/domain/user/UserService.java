package emptio.domain.user;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.serialization.IdService;

import java.time.LocalDate;
import java.util.Set;

public class UserService {

    private final Set<Validator<User>> validators;
    private final Repository<User> userRepository;

    public UserService(Set<Validator<User>> validators, Repository<User> userRepository) {
        this.validators = validators;
        this.userRepository = userRepository;
    }

    public User newUser(String name, String surname,
                        String email, String number,
                        String login, String password, Address address) throws ValidationException
    {
        LocalDate today = today();
        User user = new User(name, surname, email, number, login, password, today, address);

        try {
            validators.forEach(validator -> validator.validate(user));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a user with given parameters, cause : " + e.getMessage());
        }

        return userRepository.find(
                userRepository.add(user)
        );
    }

    private LocalDate today() {
        return LocalDate.now();
    }
}

