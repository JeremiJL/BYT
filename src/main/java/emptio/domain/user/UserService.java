package emptio.domain.user;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.serialization.IdService;

import java.time.LocalDate;
import java.util.Set;

public class UserService {

    static private Set<Validator<User>> validators;
    static private Repository<User> userRepository;

    public static void setValidators(Set<Validator<User>> validators) {
        UserService.validators = validators;
    }

    public static void setUserRepository(Repository<User> userRepository) {
        UserService.userRepository = userRepository;
    }

    static public User newUser(String name, String surname,
                               String email, String number,
                               String login, String password, Address address) throws ValidationException
    {
        LocalDate today = today();
        User user = new User(User.idService.getNewId(), name, surname, email, number, login, password, address, today);

        try {
            validators.forEach(validator -> validator.validate(user));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a user with given parameters, cause : " + e.getMessage());
        }

        return userRepository.find(
                userRepository.add(user)
        );
    }

    static public User getEmptioUser() {
        return userRepository.find(1);
    }

    private static LocalDate today() {
        return LocalDate.now();
    }
}

