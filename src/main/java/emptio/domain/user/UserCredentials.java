package emptio.domain.user;

import lombok.NonNull;
import lombok.Value;

@Value public class UserCredentials {
    @NonNull int id;
    @NonNull String password;
}
