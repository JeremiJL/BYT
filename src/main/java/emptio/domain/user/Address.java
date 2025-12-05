package emptio.domain.user;

import lombok.Value;

@Value public class Address {

    String postalCode;
    String streetName;
    String country;
    String city;
    int buildingNumber;
    int apartmentNumber;
}
