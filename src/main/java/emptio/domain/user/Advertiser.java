package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.campaign.Campaign;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value public class Advertiser extends User {

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
}
