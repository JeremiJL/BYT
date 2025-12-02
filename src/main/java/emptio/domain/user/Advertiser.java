package emptio.domain.user;

import java.time.LocalDate;

public class Advertiser extends User {

    protected Advertiser(String name, String surname,
                         String email, String number,
                         String login, String password,
                         LocalDate lastLogin, Address address) {
        super(name, surname, email, number, login, password, lastLogin, address);
    }
}
