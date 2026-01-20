package spring.boot.nextbuy.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.boot.nextbuy.configurations.securities.AuthSecurity;
import spring.boot.nextbuy.services.AuthService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthSecurity authSecurity;
    private AuthService authService;

    public SecurityConfig(AuthSecurity authSecurity, AuthService authService) {
        this.authSecurity = authSecurity;
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/singin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/singup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/files/download/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product").permitAll()
                        .anyRequest().authenticated());

        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.addFilterBefore(authSecurity, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(authService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationConfig(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
