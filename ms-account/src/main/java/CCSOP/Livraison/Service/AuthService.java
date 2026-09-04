package CCSOP.Livraison.Service;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.entities.Role;
import CCSOP.Livraison.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userrepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userrepository = userRepository;
    }

    public Collection<Role> authenticate(String email, String rawPassword) {
        User user = this.userrepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user.getRoles();
            }
        }
        return null;
    }
}