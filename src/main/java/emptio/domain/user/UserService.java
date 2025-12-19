package emptio.domain.user;

import emptio.domain.*;
import emptio.domain.campaign.Campaign;
import emptio.domain.cart.Cart;
import emptio.domain.product.Product;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService {

    private final Set<Validator<User>> validators;
    private final DomainRepository<User> userRepository;
    private final CredentialsRepository credentialsRepository;

    @SafeVarargs
    public UserService(DomainRepository<User> userRepository, CredentialsRepository credentialsRepository, Validator<User>... validators) {
        this.validators = new HashSet<>(List.of(validators));
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public UserService(DomainRepository<User> userRepository, CredentialsRepository credentialsRepository, Set<Validator<User>> validators) {
        this.validators = validators;
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public User newUser(AccountType accountType,
                        String name, String surname,
                        String email, String number,
                        String login, String password, Address address) throws ValidationException
    {
        LocalDate today = today();
        User user = new User(User.idService.getNewId(), name, surname, email, number, login, password, address, today);

        try {
            validators.forEach(validator -> validator.validate(user));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a user with given parameters, cause : " + e.getMessage());
        }

        try {
            credentialsRepository.setCredentials(user.getLogin(), new UserCredentials(user.getId(),user.getPassword()));
            userRepository.add(user);
        } catch (Exception e) {
            credentialsRepository.deleteCredentials(user.getLogin());
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

    public int getUserId(String login, String password) {
        UserCredentials credentials = credentialsRepository.getCredentials(login);
        if (credentials.getPassword().equals(password))
            return credentials.getId();
        else
            throw new CredentialsException("Given password does not match given login");
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
