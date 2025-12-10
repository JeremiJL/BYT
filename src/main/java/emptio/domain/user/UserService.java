package emptio.domain.user;

import emptio.domain.*;
import emptio.serialization.IdService;

import java.time.LocalDate;
import java.util.Set;

public class UserService {

    static private Set<Validator<User>> validators;
    static private Repository<User> userRepository;
    static private CredentialsRepository credentialsRepository;

    public static void setValidators(Set<Validator<User>> validators) {
        UserService.validators = validators;
    }

    public static void setUserRepository(Repository<User> userRepository) {
        UserService.userRepository = userRepository;
    }

    public static User newUser(String name, String surname,
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

        credentialsRepository.setCredentials(user.getLogin(), new UserCredentials(user.getId(),user.getPassword()));

        return userRepository.find(
                userRepository.add(user)
        );
    }

    public static User getEmptioUser() {
        return userRepository.find(1);
    }

    public static int getUserId(String login, String password) {
        UserCredentials credentials = credentialsRepository.getCredentials(login);
        if (credentials.getPassword().equals(password))
            return credentials.getId();
        else
            throw new CredentialsException("Given password does not match given login");
    }

    private static LocalDate today() {
        return LocalDate.now();
    }
}
