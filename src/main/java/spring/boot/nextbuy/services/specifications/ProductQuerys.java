package spring.boot.nextbuy.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import spring.boot.nextbuy.entities.Product;

import java.util.Objects;

import static spring.boot.nextbuy.services.specifications.ProductSpec.brandContains;
import static spring.boot.nextbuy.services.specifications.ProductSpec.categoryContains;

public class ProductQuerys {

    private String name;
    private String category;
    private String brand;

    public ProductQuerys(String name, String category, String brand) {
        this.name = name;
        this.category = category;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuerys that = (ProductQuerys) o;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, brand);
    }

    public Specification<Product> toSpecification() {
        return ProductSpec.nameContains(this.name).and(brandContains(brand)).and(categoryContains(category));
    }
}
