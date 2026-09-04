package CCSOP.Livraison.Service;

import CCSOP.Livraison.Repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userrepository = userRepository;
        this.roleRepository = roleRepository;
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

    public User register(User user, String rawPassword) {
        if (this.userrepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setTokenExpired(false);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("CUSTOMER");
            if (defaultRole == null) {
                defaultRole = roleRepository.save(new Role("CUSTOMER"));
            }
            user.setRoles(List.of(defaultRole));
        }

        return this.userrepository.save(user);
    }
}