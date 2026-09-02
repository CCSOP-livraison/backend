package CCSOP.Livraison;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.Service.AuthService;
import CCSOP.Livraison.entities.Role;
import CCSOP.Livraison.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Authentification valide pour le compte ADMIN")
    void testAdminAuthenticationSuccess() {
        boolean result = authService.authenticate("admin@domain.com", "password123");
        assertTrue(result, "L'administrateur doit pouvoir s'authentifier avec son mot de passe");

        User user = userRepository.findByEmail("admin@domain.com");
        assertNotNull(user, "Le compte admin doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("ADMIN")),
                "Le compte admin doit posséder le rôle ADMIN");
    }

    @Test
    @DisplayName("Authentification valide pour le compte CUSTOMER")
    void testCustomerAuthenticationSuccess() {
        boolean result = authService.authenticate("customer@domain.com", "password123");
        assertTrue(result, "Le client doit pouvoir s'authentifier avec son mot de passe");

        User user = userRepository.findByEmail("customer@domain.com");
        assertNotNull(user, "Le compte customer doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("CUSTOMER")),
                "Le compte customer doit posséder le rôle CUSTOMER");
    }

    @Test
    @DisplayName("Authentification valide pour le compte DELIVER")
    void testDeliverAuthenticationSuccess() {
        boolean result = authService.authenticate("deliver@domain.com", "password123");
        assertTrue(result, "Le livreur doit pouvoir s'authentifier avec son mot de passe");

        User user = userRepository.findByEmail("deliver@domain.com");
        assertNotNull(user, "Le compte deliver doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("DELIVER")),
                "Le compte deliver doit posséder le rôle DELIVER");
    }

    @Test
    @DisplayName("Authentification valide pour le compte MODERATION")
    void testModerationAuthenticationSuccess() {
        boolean result = authService.authenticate("moderation@domain.com", "password123");
        assertTrue(result, "Le modérateur doit pouvoir s'authentifier avec son mot de passe");

        User user = userRepository.findByEmail("moderation@domain.com");
        assertNotNull(user, "Le compte moderation doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("MODERATION")),
                "Le compte moderation doit posséder le rôle MODERATION");
    }

    @Test
    @DisplayName("Échec d'authentification en cas de mot de passe incorrect")
    void testAuthenticationFailsWithWrongPassword() {
        boolean result = authService.authenticate("admin@domain.com", "wrongpassword");
        assertFalse(result, "L'authentification doit échouer avec un mauvais mot de passe");
    }

    @Test
    @DisplayName("Échec d'authentification pour un utilisateur inexistant")
    void testAuthenticationFailsWithUnknownUser() {
        boolean result = authService.authenticate("unknown@domain.com", "password123");
        assertFalse(result, "L'authentification doit échouer pour un utilisateur inexistant");
    }
}
