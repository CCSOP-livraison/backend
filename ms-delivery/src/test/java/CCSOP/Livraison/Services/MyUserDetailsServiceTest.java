package CCSOP.Livraison.Services;

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
    @DisplayName("Chargement de l'utilisateur ADMIN (Jean Dupont) avec ses autorités")
    void testLoadAdminUser() {
        //GIVEN & WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("jean.dupont@example.com");
        //THEN
        assertNotNull(userDetails);
        assertEquals("jean.dupont@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN")), "L'autorité ROLE_ADMIN doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur CUSTOMER (Camille Petit) avec ses autorités")
    void testLoadCustomerUser() {
        //GIVEN & WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("camille.petit@example.com");
        //THEN
        assertNotNull(userDetails);
        assertEquals("camille.petit@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_CUSTOMER")), "L'autorité ROLE_CUSTOMER doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur DELIVER (Lucas Bernard) avec ses autorités")
    void testLoadDeliverUser() {
        //GIVEN & WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("lucas.bernard@example.com");
        //THEN
        assertNotNull(userDetails);
        assertEquals("lucas.bernard@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_DELIVER")), "L'autorité ROLE_DELIVER doit être présente");
    }

    @Test
    @DisplayName("Chargement de l'utilisateur MODERATION (Sophie Martin) avec ses autorités")
    void testLoadModerationUser() {
        //GIVEN & WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("sophie.martin@example.com");
        //THEN
        assertNotNull(userDetails);
        assertEquals("sophie.martin@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_MODERATION")), "L'autorité ROLE_MODERATION doit être présente");
    }

    @Test
    @DisplayName("Chargement d'un utilisateur inexistant lève une exception")
    void testLoadUnknownUserThrowsException() {
        //WHEN & THEN
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknown@example.com");
        });
    }
}
