package emptio.domain.user;

import emptio.domain.common.Blockable;
import emptio.domain.common.State;
import emptio.serialization.Identifiable;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data public class User extends Blockable implements Identifiable {

    final int id;
    final String name;
    final String surname;
    final String email;
    final String phoneNumber;
    final String login;
    final String password;
    final Address address;
    final @NonNull LocalDate lastLogin;

    public State getState() {
        if (this.lastLogin.isBefore(LocalDate.now().minusYears(2))) {
            this.state = State.ARCHIVED;
        }
        return this.state;
    }
}


