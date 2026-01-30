package spring.boot.nextbuy.services;

import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.ShopCart;
import spring.boot.nextbuy.repositories.ShopCartRepository;

@Service
public class ShopCartService {

    private ShopCartRepository shopCartRepository;

    public ShopCartService(ShopCartRepository shopCartRepository) {
        this.shopCartRepository = shopCartRepository;
    }

    public ShopCart insert(ShopCart shopCart) {return shopCartRepository.save(shopCart); }
}
