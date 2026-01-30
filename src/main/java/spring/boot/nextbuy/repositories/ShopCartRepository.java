package spring.boot.nextbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.nextbuy.entities.ShopCart;

public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {
}
