package CCSOP.Livraison;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Connexion réussie pour le compte ADMIN via /auth/login")
    void testAdminLoginSuccess() throws Exception {
        String json = """
            {
                "email": "jean.dupont@example.com",
                "password": "admin123"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("jean.dupont@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte CUSTOMER via /auth/login")
    void testCustomerLoginSuccess() throws Exception {
        String json = """
            {
                "email": "camille.petit@example.com",
                "password": "customer123"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("camille.petit@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte DELIVER via /auth/login")
    void testDeliverLoginSuccess() throws Exception {
        String json = """
            {
                "email": "lucas.bernard@example.com",
                "password": "deliver123"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("lucas.bernard@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte MODERATION via /auth/login")
    void testModerationLoginSuccess() throws Exception {
        String json = """
            {
                "email": "sophie.martin@example.com",
                "password": "moderation123"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("sophie.martin@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion refusée en cas de mauvais mot de passe (401 Unauthorized)")
    void testLoginFailsWithWrongPassword() throws Exception {
        String json = """
            {
                "email": "jean.dupont@example.com",
                "password": "wrongpassword"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email ou mot de passe incorrect"));
    }

    @Test
    @DisplayName("Connexion refusée pour un utilisateur inconnu (401 Unauthorized)")
    void testLoginFailsWithUnknownUser() throws Exception {
        String json = """
            {
                "email": "inconnu@example.com",
                "password": "password123"
            }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email ou mot de passe incorrect"));
    }
}
