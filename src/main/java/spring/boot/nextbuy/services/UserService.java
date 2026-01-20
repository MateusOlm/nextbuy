package spring.boot.nextbuy.services;

import org.springframework.stereotype.Service;
import spring.boot.nextbuy.entities.User;
import spring.boot.nextbuy.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() { return userRepository.findAll(); }

    public Boolean emailVerification(String email) { return  userRepository.existsByEmail(email); }

    public Boolean usernameVerification(String username) { return  userRepository.existsByUsername(username); }

}
