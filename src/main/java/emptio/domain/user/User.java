package emptio.domain.user;

import emptio.serialization.Identifiable;
import java.time.LocalDate;

public class User implements Identifiable {

    private int id;
    public final String name;
    public final String surname;
    public final String email;
    public final String phoneNumber;

    public final String login;
    public final String password;

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

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
}


