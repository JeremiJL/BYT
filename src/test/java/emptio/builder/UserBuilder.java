package emptio.builder;

import emptio.domain.user.Address;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.Setter;

@Setter
public class UserBuilder {

    private final UserService userService;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;
    private Address address;

    public UserBuilder(UserService userService, AddressBuilder addressBuilder) {
        this.userService = userService;
        this.name = "Mohamed";
        this.surname = "Abdulla";
        this.email = "abdulla123@pjatk.edu.pl";
        this.phoneNumber = "555-555-555";
        this.login = "abdulla123";
        this.password = "best_password_123";
        this.address = addressBuilder.build();
    }

    public User build() {
        return userService.newUser(name, surname, email, phoneNumber, login, password, address);
    }
}
