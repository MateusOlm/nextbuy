package spring.boot.nextbuy.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.nextbuy.configurations.JWTUtils;
import spring.boot.nextbuy.entities.dto.CartItemsRequest;
import spring.boot.nextbuy.services.CartItemsService;

@RestController
@RequestMapping("/shopcart")
public class ShopCartController {

    private CartItemsService cartItemsService;
    private JWTUtils jwtUtils;

    public ShopCartController(CartItemsService cartItemsService, JWTUtils jwtUtils) {
        this.cartItemsService = cartItemsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemsRequest cartItemsRequest, HttpServletRequest request) {
        String email = jwtUtils.getEmailFromJwtToken(jwtUtils.getJwtFromCookie(request));
        System.out.print(email);
        return null;
    }

}
