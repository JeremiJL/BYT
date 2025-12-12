package emptio.domain.user;

import emptio.domain.common.Blockable;
import emptio.domain.common.State;
import emptio.serialization.Identifiable;
import lombok.*;
import lombok.experimental.NonFinal;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value @NonFinal public class User extends Blockable implements Identifiable {

    int id;
    @NonNull String name;
    @NonNull String surname;
    @NonNull String email;
    @NonNull String phoneNumber;
    @NonNull String login;
    @NonNull String password;
    @NonNull Address address;
    @NonFinal @NonNull LocalDate lastLogin;

    public State getState() {
        if (this.lastLogin.isBefore(LocalDate.now().minusYears(2))) {
            this.state = State.ARCHIVED;
        }
        return this.state;
    }
}


