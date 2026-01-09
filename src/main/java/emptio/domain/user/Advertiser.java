package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.campaign.Campaign;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@With @Value public class Advertiser extends User {

    @NonNull Set<Campaign> campaigns;

    public Advertiser(@JsonProperty("id") int id, @JsonProperty("name") @NonNull String name, @JsonProperty("surname") @NonNull String surname,
                      @JsonProperty("email") @NonNull String email, @JsonProperty("phoneNumber") @NonNull String phoneNumber,
                      @JsonProperty("login") @NonNull String login,
                      @JsonProperty("password") @NonNull String password,
                      @JsonProperty("address") @NonNull Address address,
                      @JsonProperty("lastLogin") @NonNull LocalDate lastLogin,
                      @JsonProperty("campaigns") @NonNull Set<Campaign> campaigns) {
        super(id, name, surname, email, phoneNumber, login, password, address, lastLogin);
        this.campaigns = campaigns;
    }

    public Advertiser withCampaigns(Set<Campaign> campaigns) {
        return new Advertiser(
                        getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                        getLogin(), getPassword(), getAddress(), getLastLogin(),
                        campaigns
                );
    }

    @Override
    public User withId(int id) {
        return new Advertiser(
                id, getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withName(@NonNull String name) {
        return new Advertiser(
                getId(), name, getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withSurname(@NonNull String surname) {
        return new Advertiser(
                getId(), getName(), surname, getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withEmail(@NonNull String email) {
        return new Advertiser(
                getId(), getName(), getSurname(), email, getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withPhoneNumber(@NonNull String phoneNumber) {
        return new Advertiser(
                getId(), getName(), getSurname(), getEmail(), phoneNumber,
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withLogin(@NonNull String login) {
        return new Advertiser(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                login, getPassword(), getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withPassword(@NonNull String password) {
        return new Advertiser(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), password, getAddress(), getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withAddress(@NonNull Address address) {
        return new Advertiser(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), address, getLastLogin(),
                getCampaigns()
        );
    }

    @Override
    public User withLastLogin(@NonNull LocalDate lastLogin) {
        return new Advertiser(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), lastLogin,
                getCampaigns()
        );
    }
}
