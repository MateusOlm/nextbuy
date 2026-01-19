package spring.boot.nextbuy.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> registerProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.name(), productRequest.description()
                , productRequest.category(), productRequest.brand(), productRequest.price()
                , productRequest.quantity(), productRequest.imgPath());

        productService.insert(product);

        return ResponseEntity.ok().body(product.getId());
    }
}
