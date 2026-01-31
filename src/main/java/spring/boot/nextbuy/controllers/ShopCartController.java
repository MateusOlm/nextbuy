package spring.boot.nextbuy.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import spring.boot.nextbuy.configurations.JWTUtils;
import spring.boot.nextbuy.entities.CartItems;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.entities.dto.CartItemsRequest;
import spring.boot.nextbuy.entities.dto.ProductQuerys;
import spring.boot.nextbuy.services.CartItemsService;
import spring.boot.nextbuy.services.ProductService;
import spring.boot.nextbuy.services.UserService;


import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/shopcart")
public class ShopCartController {

    private CartItemsService cartItemsService;
    private UserService userService;
    private ProductService productService;
    private JWTUtils jwtUtils;

    public ShopCartController(CartItemsService cartItemsService, JWTUtils jwtUtils, UserService userService, ProductService productService) {
        this.cartItemsService = cartItemsService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addProductToCart(@RequestBody CartItemsRequest cartItemsRequest, HttpServletRequest request) {
        User user = userService.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.getJwtFromCookie(request)));
        Optional<Product> prod = productService.equalName(ProductQuerys.equalName(cartItemsRequest.name()));
        if (prod.isEmpty()) { return ResponseEntity.badRequest().build(); }
        CartItems item = new CartItems(user.getShopCart(), prod.get(), cartItemsRequest.quantity());
        cartItemsService.insert(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Set<CartItems>> getCartItems(HttpServletRequest request) {
        User user = userService.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.getJwtFromCookie(request)));
        return ResponseEntity.ok().body(user.getShopCart().getCartItems());
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateCartItems(@RequestBody CartItemsRequest cartItemsRequest, HttpServletRequest request) {
        User user = userService.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.getJwtFromCookie(request)));
        boolean update = cartItemsService.update(user, cartItemsRequest);
        if (!update) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCartItems(@RequestBody CartItemsRequest cartItemsRequest, HttpServletRequest request) {
        User user = userService.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.getJwtFromCookie(request)));
        boolean delete = cartItemsService.delete(user, cartItemsRequest);
        if (!delete) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
