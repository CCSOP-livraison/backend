package CCSOP.Livraison;

import CCSOP.Livraison.Service.MyUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyUserDetailsServiceTest {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Test
    @DisplayName("Chargement de l'utilisateur ADMIN avec ses autorités")
    void testLoadAdminUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin@domain.com");
        assertNotNull(userDetails);
        assertEquals("admin@domain.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN")), "L'autorité ROLE_ADMIN doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur CUSTOMER avec ses autorités")
    void testLoadCustomerUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("customer@domain.com");
        assertNotNull(userDetails);
        assertEquals("customer@domain.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_CUSTOMER")), "L'autorité ROLE_CUSTOMER doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur DELIVER avec ses autorités")
    void testLoadDeliverUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("deliver@domain.com");
        assertNotNull(userDetails);
        assertEquals("deliver@domain.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_DELIVER")), "L'autorité ROLE_DELIVER doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur MODERATION avec ses autorités")
    void testLoadModerationUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("moderation@domain.com");
        assertNotNull(userDetails);
        assertEquals("moderation@domain.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_MODERATION")), "L'autorité ROLE_MODERATION doit être présente");
    }

    @Test
    @DisplayName("Chargement d'un utilisateur inexistant lève une exception")
    void testLoadUnknownUserThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknown@domain.com");
        });
    }
}
