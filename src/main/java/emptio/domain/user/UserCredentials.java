package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

@Value public class UserCredentials {
    int id;
    @NonNull String password;

    @JsonCreator
    public UserCredentials(@JsonProperty("id") int id, @NonNull @JsonProperty("password") String password) {
        this.id = id;
        this.password = password;
    }

}
