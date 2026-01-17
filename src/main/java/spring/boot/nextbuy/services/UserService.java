package spring.boot.nextbuy.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public Boolean emailVerification(String email) { return  userRepository.existsByEmail(email); }

    public Boolean usernameVerification(String username) { return  userRepository.existsByUsername(username); }

}
