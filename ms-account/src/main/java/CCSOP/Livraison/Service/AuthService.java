package CCSOP.Livraison.Service;

import CCSOP.Livraison.Repository.RoleRepository;
import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.entities.Role;
import CCSOP.Livraison.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userrepository;
    private final RoleRepository roleRepository;
    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userrepository = userRepository;
        this.roleRepository = roleRepository;

        initUser("admin@domain.com", "password123", "ADMIN");
        initUser("moderation@domain.com", "password123", "MODERATION");
        initUser("customer@domain.com", "password123", "CUSTOMER");
        initUser("deliver@domain.com", "password123", "DELIVER");
    }

    private void initUser(String email, String rawPassword, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = roleRepository.findByName("ROLE_" + roleName);
        }
        if (role == null) {
            role = new Role(roleName);
            role = roleRepository.save(role);
        }

        User user = userrepository.findByEmail(email);
        if (user == null) {
            user = new User(email, passwordEncoder.encode(rawPassword), role);
            user.setEnabled(true);
            userrepository.save(user);
        }
    }

    public boolean authenticate(String email, String rawPassword) {
        User user = this.userrepository.findByEmail(email);
        if(user!=null){
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
