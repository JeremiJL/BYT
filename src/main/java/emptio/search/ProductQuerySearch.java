package emptio.search;

import emptio.domain.ProductRepository;
import emptio.domain.common.Category;
import emptio.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductQuerySearch implements QuerySearch<Product> {

    ProductRepository productRepository;

    public ProductQuerySearch(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> search(String searchQuery) {

        Category category = Category.valueOf(searchQuery);
        List<Integer> queryResults = productRepository.search(
                category.toString()
        );
        List<Product> matchedProducts = new ArrayList<>();
        for (Integer queryResultId : queryResults) {
            matchedProducts.add(
                    productRepository.find(queryResultId)
            );
        }
        return matchedProducts;
    }
}
