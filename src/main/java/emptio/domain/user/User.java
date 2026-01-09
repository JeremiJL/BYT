package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.common.Blockable;
import emptio.domain.common.State;
import emptio.serialization.Identifiable;
import lombok.*;
import lombok.experimental.NonFinal;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value @With @NonFinal public abstract class User extends Blockable implements Identifiable {

    int id;
    @NonNull String name;
    @NonNull String surname;
    @NonNull String email;
    @NonNull String phoneNumber;
    @NonNull String login;
    @NonNull String password;
    @NonNull Address address;
    @NonNull LocalDate lastLogin;

    public State getState() {
        if (this.lastLogin.isBefore(LocalDate.now().minusYears(2))) {
            this.state = State.ARCHIVED;
        }
        return this.state;
    }

    @JsonCreator
    public User(@JsonProperty("id") int id, @JsonProperty("name") @NonNull String name, @JsonProperty("surname") @NonNull String surname,
                @JsonProperty("email") @NonNull String email, @JsonProperty("phoneNumber") @NonNull String phoneNumber,
                @JsonProperty("login") @NonNull String login,
                @JsonProperty("password") @NonNull String password,
                @JsonProperty("address") @NonNull Address address,
                @JsonProperty("lastLogin") @NonNull LocalDate lastLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.address = address;
        this.lastLogin = lastLogin;
    }
}


