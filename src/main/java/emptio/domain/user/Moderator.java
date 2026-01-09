package emptio.domain.user;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@With @Value public class Moderator extends User {

    public Moderator(int id, String name, String surname,
                        String email, String number,
                        String login, String password,
                        LocalDate lastLogin, Address address) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
    }

    @Override
    public User withId(int id) {
        return new Moderator(
                id, getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withName(@NonNull String name) {
        return new Moderator(
                getId(), name, getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withSurname(@NonNull String surname) {
        return new Moderator(
                getId(), getName(), surname, getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withEmail(@NonNull String email) {
        return new Moderator(
                getId(), getName(), getSurname(), email, getPhoneNumber(),
                getLogin(), getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withPhoneNumber(@NonNull String phoneNumber) {
        return new Moderator(
                getId(), getName(), getSurname(), getEmail(), phoneNumber,
                getLogin(), getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withLogin(@NonNull String login) {
        return new Moderator(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                login, getPassword(), getLastLogin(), getAddress()
        );
    }

    @Override
    public User withPassword(@NonNull String password) {
        return new Moderator(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), password, getLastLogin(), getAddress()
        );
    }

    @Override
    public User withAddress(@NonNull Address address) {
        return new Moderator(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getLastLogin(), address
        );
    }

    @Override
    public User withLastLogin(@NonNull LocalDate lastLogin) {
        return new Moderator(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), lastLogin, getAddress()
        );
    }
}
