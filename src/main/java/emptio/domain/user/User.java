package emptio.domain.user;

import java.time.LocalDate;

public class User {

    String name;
    String surname;
    String email;
    String phoneNumber;

    String login;
    String password;

    LocalDate lastLogin;

    protected User(String name, String surname,
                   String email, String number,
                   String login, String password, LocalDate lastLogin) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = number;
        this.login = login;
        this.password = password;
        this.lastLogin = lastLogin;
    }
}


