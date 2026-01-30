package spring.boot.nextbuy.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.boot.nextbuy.entities.ShopCart;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.entities.dto.SinginRequest;
import spring.boot.nextbuy.entities.dto.SingupRequest;
import spring.boot.nextbuy.configurations.JWTUtils;
import spring.boot.nextbuy.services.ShopCartService;
import spring.boot.nextbuy.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;
    private ShopCartService shopCartService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils, UserService userService, ShopCartService shopCartService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.shopCartService = shopCartService;
    }

    @PostMapping("/singin")
    public ResponseEntity<?> SingIn(@Valid @RequestBody SinginRequest singinRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singinRequest.email(), singinRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie((User) authentication.getPrincipal());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
    }

    @PostMapping("/singup")
    public ResponseEntity<?> resgisterUser(@Valid @RequestBody SingupRequest singupRequest) {
        if(userService.emailVerification(singupRequest.email())) {
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }
        if(userService.usernameVerification(singupRequest.username())) {
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(singupRequest.password());
        User user = new User(singupRequest.username(), encryptedPassword, singupRequest.email());
        userService.insert(user);
        ShopCart shopCart = new ShopCart(user);
        shopCartService.insert(shopCart);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/singout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.cleanCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();

    }

    @GetMapping("/me")
    public ResponseEntity<?> verificationSinging() {
        return ResponseEntity.ok().build();
    }
}
