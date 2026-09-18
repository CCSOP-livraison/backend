package CCSOP.Livraison.Controller;

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
        //GIVEN
        String json = """
            {
                "email": "jean.dupont@example.com",
                "password": "admin123"
            }
        """;

        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("jean.dupont@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte CUSTOMER via /auth/login")
    void testCustomerLoginSuccess() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "camille.petit@example.com",
                "password": "customer123"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("camille.petit@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte DELIVER via /auth/login")
    void testDeliverLoginSuccess() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "lucas.bernard@example.com",
                "password": "deliver123"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("lucas.bernard@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion réussie pour le compte MODERATION via /auth/login")
    void testModerationLoginSuccess() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "sophie.martin@example.com",
                "password": "moderation123"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("sophie.martin@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    @DisplayName("Connexion refusée en cas de mauvais mot de passe (401 Unauthorized)")
    void testLoginFailsWithWrongPassword() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "jean.dupont@example.com",
                "password": "wrongpassword"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email ou mot de passe incorrect"));
    }

    @Test
    @DisplayName("Connexion refusée pour un utilisateur inconnu (401 Unauthorized)")
    void testLoginFailsWithUnknownUser() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "inconnu@example.com",
                "password": "password123"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Email ou mot de passe incorrect"));
    }

    @Test
    @DisplayName("Inscription réussie pour un nouvel utilisateur via /auth/register")
    //GIVEN
    void testRegisterSuccess() throws Exception {
        String json = """
            {
                "email": "nouveau.client@example.com",
                "password": "Password123!",
                "firstname": "Alice",
                "lastname": "Durand",
                "address": "15 Rue de la République",
                "zipcode": "75002",
                "locate": "Paris",
                "phoneNumber": "+33698765432"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Inscription réussie !"))
                .andExpect(jsonPath("$.user").value("nouveau.client@example.com"));
    }

    @Test
    @DisplayName("Échec de l'inscription si l'email existe déjà (409 Conflict)")
    void testRegisterEmailAlreadyExists() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "jean.dupont@example.com",
                "password": "newpassword123",
                "firstname": "Jean",
                "lastname": "Dupont"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Un utilisateur avec cet email existe déjà"));
    }

    @Test
    @DisplayName("Échec de l'inscription si l'email est manquant ou vide (400 Bad Request)")
    //GIVEN
    void testRegisterMissingEmail() throws Exception {
        String json = """
            {
                "email": "",
                "password": "somePassword123"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("L'email et le mot de passe sont obligatoires"));
    }

    @Test
    @DisplayName("Échec de l'inscription si le mot de passe est manquant ou vide (400 Bad Request)")
    void testRegisterMissingPassword() throws Exception {
        //GIVEN
        String json = """
            {
                "email": "valid.email@example.com",
                "password": ""
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("L'email et le mot de passe sont obligatoires"));
    }

    @Test
    @DisplayName("Un utilisateur nouvellement inscrit peut se connecter via /auth/login")
    void testLoginAfterRegister() throws Exception {
        //GIVEN
        String registerJson = """
            {
                "email": "login.after.reg@example.com",
                "password": "securePassword456",
                "firstname": "Bob",
                "lastname": "Test"
            }
        """;
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isCreated());

        String loginJson = """
            {
                "email": "login.after.reg@example.com",
                "password": "securePassword456"
            }
        """;
        //WHEN
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Connexion réussie !"))
                .andExpect(jsonPath("$.user").value("login.after.reg@example.com"))
                .andExpect(jsonPath("$.roles[0].name").value("CUSTOMER"));
    }
}
