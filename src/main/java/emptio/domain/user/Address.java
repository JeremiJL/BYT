package emptio.domain.user;

import lombok.NonNull;
import lombok.Value;

@Value public class Address {

    @NonNull String postalCode;
    String streetName;
    @NonNull String country;
    @NonNull String city;
    @NonNull int buildingNumber;
    Integer apartmentNumber;
}
