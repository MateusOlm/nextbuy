package spring.boot.nextbuy.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.entities.dto.ProductResponse;
import spring.boot.nextbuy.services.ProductService;
import spring.boot.nextbuy.entities.dto.ProductQuerys;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.name(), productRequest.description()
                , productRequest.category(), productRequest.brand(), productRequest.price()
                , productRequest.quantity(), productRequest.imgPath());

        productService.insert(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ProductResponse> searchProduct(ProductQuerys query, Pageable pageable) {
        return productService.searchProducts(query, pageable);
    }
}
