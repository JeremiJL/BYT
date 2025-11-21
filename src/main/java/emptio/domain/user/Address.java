package emptio.domain.user;

public class Address {

    public final String postalCode;
    public final @Nullable String streetName;
    public final String country;
    public final String city;
    public final int buildingNumber;
    public final @Nullable int apartmentNumber;

    public Address(String postalCode, String streetName, String country, String city, int buildingNumber, int apartmentNumber) {
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.country = country;
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }
}
