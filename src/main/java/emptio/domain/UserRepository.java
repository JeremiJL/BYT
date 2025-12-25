package emptio.domain;

import emptio.domain.user.*;

import java.util.Optional;

public class UserRepository<T extends User> implements DomainRepository<T> {

    private final DomainRepository<Shopper> shopperRepository;
    private final DomainRepository<Merchant> merchantRepository;
    private final DomainRepository<Advertiser> advertiserRepository;
    private final CredentialsRepository credentialsRepository;

    public UserRepository(DomainRepository<Shopper> shopperRepository, DomainRepository<Merchant> merchantRepository,
                          DomainRepository<Advertiser> advertiserRepository, CredentialsRepository credentialsRepository) {
        this.shopperRepository = shopperRepository;
        this.merchantRepository = merchantRepository;
        this.advertiserRepository = advertiserRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Optional<T> add(T user) throws RepositoryException, CredentialsException {
        credentialsRepository.setCredentials(user.getLogin(), new UserCredentials(user.getId(),user.getPassword()));

        return switch (user) {
            case Merchant merchant -> merchantRepository.add(merchant);
            case Shopper shopper -> shopperRepository.add(shopper);
            case Advertiser advertiser -> advertiserRepository.add(advertiser);
            default -> {
                credentialsRepository.deleteCredentials(user.getLogin());
                throw new RepositoryException("Can not save User of unknown sub-type.");
            }
        };
    }

    // BEGS FOR REFACTOR
    @Override
    public T find(Integer id) throws RepositoryException {
        try {
            //noinspection unchecked
            return (T) shopperRepository.find(id);
        } catch (RepositoryException _) {}

        try {
            //noinspection unchecked
            return (T) merchantRepository.find(id);
        } catch (RepositoryException _) {}

        try {
            //noinspection unchecked
            return (T) advertiserRepository.find(id);
        } catch (RepositoryException _) {}

        throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
    }

    public UserCredentials find(String login) throws RepositoryException, CredentialsException {
        return credentialsRepository.getCredentials(login);
    }

    @Override
    public boolean remove(Integer id) throws RepositoryException, CredentialsException {
        try {
            shopperRepository.remove(id);
        } catch (RepositoryException _) {
            try {
                merchantRepository.remove(id);
            } catch (RepositoryException _) {
                try {
                    advertiserRepository.remove(id);
                } catch (RepositoryException _) {
                    throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
                }
            }
        } finally {
            credentialsRepository.deleteCredentials(
                    find(id).getLogin()
            );
        }
    }
}
