package emptio.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@With @Value public class Moderator extends User {

    public Moderator(int id, String name, String surname,
                        String email, String number,
                        String login, String password,
                        LocalDate lastLogin, Address address) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
    }
}
