package spring.boot.nextbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.nextbuy.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
