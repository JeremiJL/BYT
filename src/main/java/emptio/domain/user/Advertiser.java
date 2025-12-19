package emptio.domain.user;

import emptio.domain.campaign.Campaign;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value public class Advertiser extends User {

    @NonNull Set<Campaign> campaigns;

    public Advertiser(int id, String name, String surname,
                         String email, String number,
                         String login, String password,
                         LocalDate lastLogin, Address address) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
        this.campaigns = Collections.emptySet();
    }

    public Advertiser(int id, @NonNull String name, @NonNull String surname, @NonNull String email,
                      @NonNull String phoneNumber, @NonNull String login, @NonNull String password,
                      @NonNull Address address, @NonNull LocalDate lastLogin, @NonNull Set<Campaign> campaigns) {
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
