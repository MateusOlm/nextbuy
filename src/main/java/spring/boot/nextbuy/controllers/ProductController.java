package spring.boot.nextbuy.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.entities.dto.ProductResponse;
import spring.boot.nextbuy.entities.mapper.ProductMapper;
import spring.boot.nextbuy.services.ProductService;
import spring.boot.nextbuy.entities.dto.ProductQuerys;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerProduct(@RequestBody ProductRequest productRequest) {
        Product product = ProductMapper.DtoToProduct(productRequest);

        productService.insert(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ProductResponse> searchProduct(ProductQuerys query, Pageable pageable) {
        return productService.searchProducts(query, pageable).map(ProductMapper::ProductToDto);
    }

    @GetMapping("/brand")
    public List<ProductResponse> searchForEachCategory() {
        return productService.searchForEachCategory().stream().map(ProductMapper::ProductToDto).toList();
    }

    @GetMapping("/name")
    public ResponseEntity<Optional<ProductResponse>> equalName(ProductQuerys query) {
        Optional<ProductResponse> product = productService.equalName(query).map(ProductMapper::ProductToDto);
        if (ObjectUtils.isEmpty(product)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(product);
    }
}
