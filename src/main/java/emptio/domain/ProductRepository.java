package emptio.domain;

import emptio.domain.product.Product;

import java.util.List;

public class ProductRepository implements SearchRepository<Product>, DomainRepository<Product> {

    DomainRepository<Product> domainRepository;
    SearchRepository<Product> searchRepository;

    public ProductRepository(DomainRepository<Product> domainRepository, SearchRepository<Product> searchRepository) {
        this.domainRepository = domainRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public Integer add(Product i) throws RepositoryException {
        Integer result = domainRepository.add(i);
        searchRepository.put(i);
        return result;
    }

    @Override
    public Product find(Integer id) throws RepositoryException {
        return this.domainRepository.find(id);
    }

    @Override
    public boolean remove(Integer id) throws RepositoryException {
        return this.domainRepository.remove(id);
    }

    @Override
    public void tearDown() {
        this.domainRepository.tearDown();
    }

    @Override
    public List<Integer> search(String feature) {
        return this.searchRepository.search(feature);
    }

    @Override
    public boolean put(Product i) throws RepositoryException {
        return this.searchRepository.put(i);
    }

    @Override
    public String getFeature(Product i) {
        return this.searchRepository.getFeature(i);
    }
}
