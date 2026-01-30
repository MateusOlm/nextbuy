package spring.boot.nextbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.nextbuy.entities.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
}
