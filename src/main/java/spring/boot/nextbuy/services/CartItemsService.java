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

    public void insert(CartItems cartItems) {
        cartItemsRepository.save(cartItems);
    }

    public boolean update(User user, CartItemsRequest cartItemsRequest) {
        for (CartItems prod: user.getShopCart().getCartItems()) {
            if (prod.getProduct().getName().equals(cartItemsRequest.name())) {
                prod.setCartQuantity(cartItemsRequest.quantity());
                cartItemsRepository.save(prod);
                return true;
            }
        }
        return false;
    }

    public boolean delete(User user, CartItemsRequest cartItemsRequest) {
        for (CartItems prod: user.getShopCart().getCartItems()) {
            if (prod.getProduct().getName().equals(cartItemsRequest.name())) {
                prod.setCartQuantity(cartItemsRequest.quantity());
                cartItemsRepository.delete(prod);
                return true;
            }
        }
        return false;
    }
}