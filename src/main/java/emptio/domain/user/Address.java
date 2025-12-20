package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public Address(
            @JsonProperty("postalCode") @NonNull String postalCode,
            @JsonProperty("streetName") String streetName,
            @JsonProperty("country") @NonNull String country,
            @JsonProperty("city") @NonNull String city,
            @JsonProperty("buildingNumber") int buildingNumber,
            @JsonProperty("apartmentNumber") Integer apartmentNumber) {
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.country = country;
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }
}
