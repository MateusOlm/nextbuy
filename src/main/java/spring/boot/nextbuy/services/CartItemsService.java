package spring.boot.nextbuy.services;

import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.CartItems;
import spring.boot.nextbuy.repositories.CartItemsRepository;

@Service
public class CartItemsService {

    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    public CartItems insert(CartItems cartItems) { return cartItemsRepository.save(cartItems); }
}
