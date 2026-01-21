package spring.boot.nextbuy.entities.mapper;

import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.entities.dto.ProductResponse;

public class ProductMapper {

    public static Product DtoToProduct(ProductRequest productRequest) {
        return new Product(productRequest.name(), productRequest.description()
                , productRequest.category(), productRequest.brand(), productRequest.price()
                , productRequest.quantity(), productRequest.imgPath());
    }

    public static ProductResponse ProductToDto(Product product) {
        return new ProductResponse(product.getName(), product.getDescription(),
                product.getCategory(), product.getBrand(), product.getImgPath(), product.getPrice(),
                product.getQuantity());
    }
}
