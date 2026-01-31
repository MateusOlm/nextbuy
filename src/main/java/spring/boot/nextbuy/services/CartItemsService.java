package spring.boot.nextbuy.services;

import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.CartItems;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.entities.dto.CartItemsRequest;
import spring.boot.nextbuy.repositories.CartItemsRepository;

import java.util.Set;

@Service
public class CartItemsService {

    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    public CartItems insert(CartItems cartItems) { return cartItemsRepository.save(cartItems); }

    public void update(User user, CartItemsRequest cartItemsRequest) {
        Set<CartItems> items = user.getShopCart().getCartItems();
        items.forEach(prod -> {
            if(prod.getProduct().getName().equals(cartItemsRequest.name())) {
            prod.setCartQuantity(cartItemsRequest.quantity());
            cartItemsRepository.save(prod);
        }
        });

    }
}
