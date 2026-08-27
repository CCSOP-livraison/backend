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
@Transactional  // Restores the database to its default state
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1. A new user's registration must be successful (201 Created)")
    void testRegisterSuccess() throws Exception {
        // GIVEN - Initialization of user data
        String userJson = """
                {
                    "username": "testuser@domain.com",
                    "password": "password123",
                    "lastname": "DuMoulin",
                    "firstname": "Maxime",
                    "address": "routeExemple 3",
                    "locate": "Lausanne",
                    "zipcode": "1020",
                    "phoneNumber": "+4183900033"
                }
                """;

        // WHEN
        var response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        // THEN
        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("2. Logging in with valid credentials must return a JWT token (200 OK)")
    void testLoginSuccess() throws Exception {
        // GIVEN - Initialization Test data
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
    @DisplayName("3. A login attempt with an incorrect password should fail (401 Unauthorized)")
    void testLoginBadPassword() throws Exception {
        // GIVEN - Initialization Test data
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

        // WHEN
        var response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson));

        // THEN
        response.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("4. Accessing a protected endpoint without a token must fail (403 Forbidden)")
    void testProtectedEndpointWithoutToken() throws Exception {
        // GIVEN

        // WHEN - Tentative d'accès à la ressource protégée
        var response = mockMvc.perform(get("/api/user/profile"));

        // THEN - Accès interdit avec un statut 403 Forbidden
        response.andExpect(status().isForbidden());
    }
}