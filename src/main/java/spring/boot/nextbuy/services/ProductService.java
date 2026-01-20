package spring.boot.nextbuy.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductResponse;
import spring.boot.nextbuy.repositories.ProductRepository;
import spring.boot.nextbuy.entities.dto.ProductQuerys;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product insert(Product product) { return productRepository.save(product); }

    public Page<ProductResponse> searchProducts(ProductQuerys querys, Pageable pageable) {
        Page<Product> page = productRepository.findAll(querys.toSpecification(), pageable);
        return page.map(product -> {
            return new ProductResponse(product.getName(), product.getDescription(),
                    product.getCategory(), product.getBrand(), product.getImgPath(), product.getPrice(),
                    product.getQuantity());
        });
    }
}
