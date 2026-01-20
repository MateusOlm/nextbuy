package spring.boot.nextbuy.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import spring.boot.nextbuy.entities.Product;

public class ProductSpec {

    public static Specification<Product> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(name)) { return null ;}
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Product> brandContains(String brand) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(brand)) { return null ;}
            return criteriaBuilder.like(root.get("brand"), "%" + brand + "%");
        };
    }

    public static Specification<Product> categoryContains(String category) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(category)) { return null ;}
            return criteriaBuilder.like(root.get("category"), "%" + category + "%");
        };
    }
}
