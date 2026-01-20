package spring.boot.nextbuy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.services.ProductService;
import spring.boot.nextbuy.services.specifications.ProductQuerys;

import java.util.List;

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
    public List<Product> searchProduct(ProductQuerys querys) {
        return productService.findAll(querys);
    }
}
