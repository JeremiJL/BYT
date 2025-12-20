package emptio.domain;

import emptio.domain.user.Advertiser;
import emptio.domain.user.Merchant;
import emptio.domain.user.Shopper;
import emptio.domain.user.User;

public class UserRepository<T extends User> implements DomainRepository<T> {

    private final DomainRepository<Shopper> shopperRepository;
    private final DomainRepository<Merchant> merchantRepository;
    private final DomainRepository<Advertiser> advertiserRepository;

    public UserRepository(DomainRepository<Shopper> shopperRepository, DomainRepository<Merchant> merchantRepository,
                          DomainRepository<Advertiser> advertiserRepository) {
        this.shopperRepository = shopperRepository;
        this.merchantRepository = merchantRepository;
        this.advertiserRepository = advertiserRepository;
    }

    @Override
    public Integer add(T user) {
        return switch (user) {
            case Merchant merchant -> merchantRepository.add(merchant);
            case Shopper shopper -> shopperRepository.add(shopper);
            case Advertiser advertiser -> advertiserRepository.add(advertiser);
            case null, default -> throw new RepositoryException("Can not save User of unknown sub-type.");
        };
    }

    @Override
    public T find(Integer id) {
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

    @Override
    public void remove(Integer id) {
        try {
            shopperRepository.remove(id);
        } catch (RepositoryException _) {}

        try {
            merchantRepository.remove(id);
        } catch (RepositoryException _) {}

        try {
            advertiserRepository.remove(id);
        } catch (RepositoryException _) {}

        throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
    }
}
