package emptio.builder;

import emptio.domain.user.Address;
import lombok.Setter;

@Setter
public class AddressBuilder {

    String postalCode = "500-310";
    String streetName = "Maple Street";
    String country = "Switzerland";
    String city = "Geneva";
    int buildingNumber = 2;
    Integer apartmentNumber = 4;

    public Address build() {
        return new Address(postalCode,streetName,country,city,buildingNumber,apartmentNumber);
    }

}