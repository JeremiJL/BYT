package emptio.domain.user;

import emptio.domain.*;
import emptio.domain.campaign.Campaign;
import emptio.domain.cart.Cart;
import emptio.domain.product.Product;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.*;

public class UserService {

    private final Set<Validator<User>> validators;
    private final UserRepository<User> userRepository;

    @SafeVarargs
    public UserService(UserRepository<User> userRepository, Validator<User>... validators) {
        this.validators = new HashSet<>(List.of(validators));
        this.userRepository = userRepository;
    }

    public UserService(UserRepository<User> userRepository, Set<Validator<User>> validators) {
        this.validators = validators;
        this.userRepository = userRepository;
    }

    public User newUser(AccountType accountType,
                        String name, String surname,
                        String email, String number,
                        String login, String password, Address address) throws ValidationException, RepositoryException
    {
        LocalDate today = today();

        User user = switch (accountType) {
            case SHOPPER -> new Shopper(User.idService.getNewId(), name, surname, email, number, login, password, address, today, null);
            case MERCHANT -> new Merchant(User.idService.getNewId(), name, surname, email, number, login, password, address, today, Collections.emptySet());
            case ADVERTISER -> new Advertiser(User.idService.getNewId(), name, surname, email, number, login, password, address, today, Collections.emptySet());
        };

        try {
            validators.forEach(validator -> validator.validate(user));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a user with given parameters, cause : " + e.getMessage());
        }

        try {
            userRepository.add(user);
        } catch (RepositoryException e) {
            userRepository.remove(user.getId());
            throw e;
        }

        return userRepository.find(
            user.getId()
        );
    }

    public User getEmptioUser() {
        return userRepository.find(1);
    }

    public Optional<@NonNull Integer> getUserId(String login, String password) {
        UserCredentials credentials = userRepository.find(login);
        if (credentials.getPassword().equals(password))
            return Optional.of(credentials.getId());
        else
            return Optional.empty();
    }

    public Optional<User> getUser(int id) {
        try {
            return Optional.of(userRepository.find(id));
        } catch (RepositoryException e) {
            return Optional.empty();
        }
    }

    public void addCampaign(Advertiser advertiser, Campaign newCampaign) {
        Set<Campaign> advertiserCampaigns;
        advertiserCampaigns = new HashSet<>(advertiser.getCampaigns());
        advertiserCampaigns.add(newCampaign);

        userRepository.update(advertiser.withCampaigns(
                advertiserCampaigns
        ));
    }

    public void addProduct(Merchant merchant, Product newProduct) {
        Set<Product> merchantProducts;
        merchantProducts = new HashSet<>(merchant.getProducts());
        merchantProducts.add(newProduct);

        userRepository.update(merchant.withProducts(
                merchantProducts
        ));
    }

    public void newCart(Shopper shopper, Cart cart) {
        userRepository.update(
                shopper.withCart(cart)
        );
    }

    private static LocalDate today() {
        return LocalDate.now();
    }
}
