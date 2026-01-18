package spring.boot.nextbuy.configurations.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.boot.nextbuy.configurations.JWTUtils;
import spring.boot.nextbuy.services.AuthService;

import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthSecurity extends OncePerRequestFilter {

    private JWTUtils jwtUtils;
    private AuthService authService;

    public AuthSecurity(JWTUtils jwtUtils, AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseToken(request);
        if ( token != null && jwtUtils.validateToken(token) ) {
            String userEmail = jwtUtils.getIdFromJwtToken(token);
            UserDetails user = authService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        return jwtUtils.getJwtFromCookie(request);
    }
}
