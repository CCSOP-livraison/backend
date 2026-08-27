package CCSOP.Livraison.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
        // GIVEN - Données d'inscription (format JSON correspondant au DTO AuthRequest)
        String userJson = """
                {
                    "username": "testuser@domain.com",
                    "password": "password123"
                }
                """;

        // WHEN - Envoi de la requête d'inscription
        var response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // THEN - Le statut de la réponse doit être 201 Created
        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("2. La connexion avec des identifiants valides doit retourner un token JWT (200 OK)")
    void testLoginSuccess() throws Exception {
        // GIVEN - Un utilisateur préalablement inscrit
        String registerJson = """
                {
                    "username": "loginuser@domain.com",
                    "password": "password123"
                }
                """;
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        String loginJson = """
                {
                    "username": "loginuser@domain.com",
                    "password": "password123"
                }
                """;

        // WHEN - Tentative de connexion avec les identifiants valides
        var response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson));

        // THEN - Le statut est 200 OK et un token JWT est retourné
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("3. La connexion avec un mauvais mot de passe doit échouer (401 Unauthorized)")
    void testLoginBadPassword() throws Exception {
        // GIVEN - Inscription préalable pour s'assurer que l'utilisateur existe
        String registerJson = """
                {
                    "username": "loginbadpw@domain.com",
                    "password": "correctpassword"
                }
                """;
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson));

        String loginJson = """
                {
                    "username": "loginbadpw@domain.com",
                    "password": "wrongpassword"
                }
                """;

        // WHEN - Tentative de connexion avec un mauvais mot de passe
        var response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson));

        // THEN - Accès refusé avec un statut 401 Unauthorized
        response.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("4. Accéder à un endpoint protégé sans token doit échouer (403 Forbidden)")
    void testProtectedEndpointWithoutToken() throws Exception {
        // GIVEN - Aucune authentification (aucun header / token fourni)

        // WHEN - Tentative d'accès à la ressource protégée
        var response = mockMvc.perform(get("/api/user/profile"));

        // THEN - Accès interdit avec un statut 403 Forbidden
        response.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("5. Accéder à un endpoint protégé avec un token JWT valide doit réussir (200 OK)")
    void testProtectedEndpointWithValidToken() throws Exception {
        // GIVEN
        String userJson = """
            {
                "username": "protecteduser@domain.com",
                "password": "password123"
            }
            """;
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // WHEN & THEN - On se connecte et on récupère le contenu de la réponse
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        // Extraction manuelle du token depuis la String JSON retournée
        String responseString = result.getResponse().getContentAsString();
        // Utilisation de JsonPath pour extraire le champ "token"
        String token = com.jayway.jsonpath.JsonPath.read(responseString, "$.token");

        // Appel de la route protégée avec le header Bearer
        mockMvc.perform(get("/api/user/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}