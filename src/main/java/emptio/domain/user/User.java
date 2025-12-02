package emptio.domain.user;

import emptio.domain.common.Blockable;
import emptio.domain.common.State;
import emptio.serialization.Identifiable;
import java.time.LocalDate;

public abstract class User extends Blockable implements Identifiable {

    private int id;

    public final String name;
    public final String surname;
    public final String email;
    public final String phoneNumber;

    public final String login;
    public final String password;

    public final Address address;

    LocalDate lastLogin;

    protected User(String name, String surname,
                   String email, String number,
                   String login, String password, LocalDate lastLogin, Address address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = number;
        this.login = login;
        this.password = password;
        this.lastLogin = lastLogin;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public State getState() {
        if (this.lastLogin.isBefore(LocalDate.now().minusYears(2))) {
            this.state = State.ARCHIVED;
        }
        return this.state;
    }
}


