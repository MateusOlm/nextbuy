package spring.boot.nextbuy.services;

import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.repositories.ProductRepository;
import spring.boot.nextbuy.services.specifications.ProductQuerys;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product insert(Product product) { return productRepository.save(product); }

    public List<Product> findAll(ProductQuerys querys) { return productRepository.findAll(querys.toSpecification()); }
}
