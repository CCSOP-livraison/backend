package CCSOP.Livraison.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Map<String, String> userDatabase = new HashMap<>();

    public AuthService() {
        userDatabase.put("user@domain.com", passwordEncoder.encode("password123"));
        userDatabase.put("admin@domain.com", passwordEncoder.encode("admin2026"));
    }

    public boolean authenticate(String email, String rawPassword) {
        String storedHash = userDatabase.get(email);

        if (storedHash == null) {
            return false; // Utilisateur inconnu
        }

        // Compare le mot de passe reçu avec le hash en mémoire
        return passwordEncoder.matches(rawPassword, storedHash);
    }
}
