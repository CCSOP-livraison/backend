package CCSOP.Livraison.controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Annule les modifications en base de données après chaque test
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1. L'inscription d'un nouvel utilisateur doit réussir (201 Created)")
    void testRegisterSuccess() throws Exception {
        String userJson = """
                {
                    "username": "testuser",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("2. La connexion avec des identifiants valides doit retourner un token JWT (200 OK)")
    void testLoginSuccess() throws Exception {
        // Étape 1 : Création de l'utilisateur
        String registerJson = """
                {
                    "username": "loginuser",
                    "password": "password123"
                }
                """;
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        // Étape 2 : Tentative de connexion
        String loginJson = """
                {
                    "username": "loginuser",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()); // Vérifie la présence de la clé "token"
    }

    @Test
    @DisplayName("3. La connexion avec un mauvais mot de passe doit échouer (401 Unauthorized)")
    void testLoginBadPassword() throws Exception {
        String loginJson = """
                {
                    "username": "loginuser",
                    "password": "wrongpassword"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("4. Accéder à un endpoint protégé sans token doit échouer (403 Forbidden ou 401)")
    void testProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/user/profile")) // Remplacez par une route protégée de votre API
                .andExpect(status().isForbidden());
    }
}