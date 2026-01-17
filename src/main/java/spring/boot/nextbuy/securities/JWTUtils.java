package spring.boot.nextbuy.securities;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import spring.boot.nextbuy.entities.User;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    @Value("${nextbuy.app.jwtSecret}")
    private String secretKey;
    @Value("${nextbuy.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${nextbuy.app.jwtCookieName}")
    private String jwtCookie;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getJwtFromCookie(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateToken(user);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/auth").maxAge(2 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie cleanCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/auth").build();
        return cookie;
    }

    public String getIdFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setIssuer("NextBuy")
                .setIssuedAt(new Date())
                .setSubject(user.getEmail())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }
}
