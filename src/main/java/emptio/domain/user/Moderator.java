package emptio.domain.user;

import java.time.LocalDate;

public class Moderator extends User {

    protected Moderator(int id, String name, String surname,
                        String email, String number,
                        String login, String password,
                        LocalDate lastLogin, Address address) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
    }
}
