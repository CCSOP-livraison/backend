package CCSOP.Livraison;

import CCSOP.Livraison.Repository.UserRepository;
import CCSOP.Livraison.Service.AuthService;
import CCSOP.Livraison.entities.Role;
import CCSOP.Livraison.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Authentification valide pour le compte ADMIN (Jean Dupont)")
    void testAdminAuthenticationSuccess() {
        Collection<Role> roles = authService.authenticate("jean.dupont@example.com", "admin123");
        assertNotNull(roles, "L'administrateur doit pouvoir s'authentifier avec son mot de passe");
        assertTrue(roles.stream().map(Role::getName).anyMatch(r -> r.contains("ADMIN")),
                "Les rôles retournés doivent inclure ADMIN");

        User user = userRepository.findByEmail("jean.dupont@example.com");
        assertNotNull(user, "Le compte admin doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("ADMIN")),
                "Le compte admin doit posséder le rôle ADMIN");
    }

    @Test
    @DisplayName("Authentification valide pour le compte CUSTOMER (Camille Petit)")
    void testCustomerAuthenticationSuccess() {
        Collection<Role> roles = authService.authenticate("camille.petit@example.com", "customer123");
        assertNotNull(roles, "Le client doit pouvoir s'authentifier avec son mot de passe");
        assertTrue(roles.stream().map(Role::getName).anyMatch(r -> r.contains("CUSTOMER")),
                "Les rôles retournés doivent inclure CUSTOMER");

        User user = userRepository.findByEmail("camille.petit@example.com");
        assertNotNull(user, "Le compte customer doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("CUSTOMER")),
                "Le compte customer doit posséder le rôle CUSTOMER");
    }

    @Test
    @DisplayName("Authentification valide pour le compte DELIVER (Lucas Bernard)")
    void testDeliverAuthenticationSuccess() {
        Collection<Role> roles = authService.authenticate("lucas.bernard@example.com", "deliver123");
        assertNotNull(roles, "Le livreur doit pouvoir s'authentifier avec son mot de passe");
        assertTrue(roles.stream().map(Role::getName).anyMatch(r -> r.contains("DELIVER")),
                "Les rôles retournés doivent inclure DELIVER");

        User user = userRepository.findByEmail("lucas.bernard@example.com");
        assertNotNull(user, "Le compte deliver doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("DELIVER")),
                "Le compte deliver doit posséder le rôle DELIVER");
    }

    @Test
    @DisplayName("Authentification valide pour le compte MODERATION (Sophie Martin)")
    void testModerationAuthenticationSuccess() {
        Collection<Role> roles = authService.authenticate("sophie.martin@example.com", "moderation123");
        assertNotNull(roles, "Le modérateur doit pouvoir s'authentifier avec son mot de passe");
        assertTrue(roles.stream().map(Role::getName).anyMatch(r -> r.contains("MODERATION")),
                "Les rôles retournés doivent inclure MODERATION");

        User user = userRepository.findByEmail("sophie.martin@example.com");
        assertNotNull(user, "Le compte moderation doit exister en base");
        assertTrue(user.getRoles().stream().map(Role::getName).anyMatch(r -> r.contains("MODERATION")),
                "Le compte moderation doit posséder le rôle MODERATION");
    }

    @Test
    @DisplayName("Échec d'authentification en cas de mot de passe incorrect")
    void testAuthenticationFailsWithWrongPassword() {
        Collection<Role> roles = authService.authenticate("jean.dupont@example.com", "wrongpassword");
        assertNull(roles, "L'authentification doit échouer avec un mauvais mot de passe");
    }

    @Test
    @DisplayName("Échec d'authentification pour un utilisateur inexistant")
    void testAuthenticationFailsWithUnknownUser() {
        Collection<Role> roles = authService.authenticate("unknown@example.com", "password123");
        assertNull(roles, "L'authentification doit échouer pour un utilisateur inexistant");
    }
}
