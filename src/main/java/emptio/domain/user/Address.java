package emptio.domain.user;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

@NonFinal @Value public class Address {

    @NonNull String postalCode;
    String streetName;
    @NonNull String country;
    @NonNull String city;
    int buildingNumber;
    Integer apartmentNumber;
}
