package spring.boot.nextbuy.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.entities.dto.SinginRequest;
import spring.boot.nextbuy.entities.dto.SingupRequest;
import spring.boot.nextbuy.securities.JWTUtils;
import spring.boot.nextbuy.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
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
            return ResponseEntity.badRequest().build();
        }
        if(userService.usernameVerification(singupRequest.username())) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(singupRequest.password());
        User user = new User(singupRequest.username(), encryptedPassword, singupRequest.email());
        userService.insert(user);


        return ResponseEntity.ok().build();
    }

    @PostMapping("singout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.cleanCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();

    }
}
