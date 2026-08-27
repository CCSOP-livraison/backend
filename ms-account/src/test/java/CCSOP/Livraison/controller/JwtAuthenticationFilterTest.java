package CCSOP.Livraison.controller;

import CCSOP.Livraison.JwtAuthenticationFilter;
import CCSOP.Livraison.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        // Garantit un contexte de sécurité propre avant chaque test
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        // Nettoie le contexte après l'exécution pour éviter les fuites de state entre tests
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Doit authentifier l'utilisateur et remplir le SecurityContext lorsque le token JWT est valide")
    void doFilterInternal_WithValidToken_ShouldAuthenticateUser() throws ServletException, IOException {
        // GIVEN
        String token = "valid.jwt.token";
        String userEmail = "testuser@domain.com";

        // Définition d'un UserDetails fictif retourné par le UserDetailsService
        UserDetails userDetails = new User(userEmail, "password", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.extractMail(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        // WHEN
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // THEN
        // 1. Vérifie que l'authentification est bien enregistrée dans le SecurityContext
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication, "L'authentification ne doit pas être null");
        assertTrue(authentication.isAuthenticated());
        assertEquals(userDetails, authentication.getPrincipal());

        // 2. Vérifie les interactions avec les dépendances
        verify(jwtUtil, times(1)).validateToken(token);
        verify(jwtUtil, times(1)).extractMail(token);
        verify(userDetailsService, times(1)).loadUserByUsername(userEmail);

        // 3. S'assure que la requête continue son chemin dans la chaîne de filtres
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Ne doit pas authentifier l'utilisateur si aucun header Authorization n'est fourni")
    void doFilterInternal_WithoutAuthorizationHeader_ShouldNotAuthenticate() throws ServletException, IOException {
        // GIVEN
        when(request.getHeader("Authorization")).thenReturn(null);

        // WHEN
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // THEN
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(jwtUtil);
        verifyNoInteractions(userDetailsService);
        verify(filterChain, times(1)).doFilter(request, response);
    }
}