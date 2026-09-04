package CCSOP.Livraison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountSecurityAccessTest {

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

    @Nested
    @DisplayName("Tests d'accès au contrôleur Admin (/admin)")
    class AdminControllerAccessTests {

        @Test
        @WithMockUser(username = "jean.dupont@example.com", roles = {"ADMIN"})
        @DisplayName("Un administrateur (ROLE_ADMIN) doit pouvoir accéder à /admin")
        void adminCanAccessAdminController() throws Exception {
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "camille.petit@example.com", roles = {"CUSTOMER"})
        @DisplayName("Un client (ROLE_CUSTOMER) ne doit PAS pouvoir accéder à /admin (403 Forbidden)")
        void customerCannotAccessAdminController() throws Exception {
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "lucas.bernard@example.com", roles = {"DELIVER"})
        @DisplayName("Un livreur (ROLE_DELIVER) ne doit PAS pouvoir accéder à /admin (403 Forbidden)")
        void deliverCannotAccessAdminController() throws Exception {
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "sophie.martin@example.com", roles = {"MODERATION"})
        @DisplayName("Un modérateur (ROLE_MODERATION) ne doit PAS pouvoir accéder à /admin (403 Forbidden)")
        void moderationCannotAccessAdminController() throws Exception {
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("Un utilisateur anonyme ne doit PAS pouvoir accéder à /admin (401/403)")
        void anonymousCannotAccessAdminController() throws Exception {
            mockMvc.perform(get("/admin"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Tests d'accès au contrôleur Customer (/customer)")
    class CustomerControllerAccessTests {

        @Test
        @WithMockUser(username = "camille.petit@example.com", roles = {"CUSTOMER"})
        @DisplayName("Un client (ROLE_CUSTOMER) doit pouvoir accéder à /customer")
        void customerCanAccessCustomerController() throws Exception {
            mockMvc.perform(get("/customer"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "jean.dupont@example.com", roles = {"ADMIN"})
        @DisplayName("Un administrateur (ROLE_ADMIN) ne doit PAS pouvoir accéder à /customer (403 Forbidden)")
        void adminCannotAccessCustomerController() throws Exception {
            mockMvc.perform(get("/customer"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "lucas.bernard@example.com", roles = {"DELIVER"})
        @DisplayName("Un livreur (ROLE_DELIVER) ne doit PAS pouvoir accéder à /customer (403 Forbidden)")
        void deliverCannotAccessCustomerController() throws Exception {
            mockMvc.perform(get("/customer"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "sophie.martin@example.com", roles = {"MODERATION"})
        @DisplayName("Un modérateur (ROLE_MODERATION) ne doit PAS pouvoir accéder à /customer (403 Forbidden)")
        void moderationCannotAccessCustomerController() throws Exception {
            mockMvc.perform(get("/customer"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("Un utilisateur anonyme ne doit PAS pouvoir accéder à /customer (401/403)")
        void anonymousCannotAccessCustomerController() throws Exception {
            mockMvc.perform(get("/customer"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Tests d'accès au contrôleur Deliver (/deliver)")
    class DeliverControllerAccessTests {

        @Test
        @WithMockUser(username = "lucas.bernard@example.com", roles = {"DELIVER"})
        @DisplayName("Un livreur (ROLE_DELIVER) doit pouvoir accéder à /deliver")
        void deliverCanAccessDeliverController() throws Exception {
            mockMvc.perform(get("/deliver"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "jean.dupont@example.com", roles = {"ADMIN"})
        @DisplayName("Un administrateur (ROLE_ADMIN) ne doit PAS pouvoir accéder à /deliver (403 Forbidden)")
        void adminCannotAccessDeliverController() throws Exception {
            mockMvc.perform(get("/deliver"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "camille.petit@example.com", roles = {"CUSTOMER"})
        @DisplayName("Un client (ROLE_CUSTOMER) ne doit PAS pouvoir accéder à /deliver (403 Forbidden)")
        void customerCannotAccessDeliverController() throws Exception {
            mockMvc.perform(get("/deliver"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "sophie.martin@example.com", roles = {"MODERATION"})
        @DisplayName("Un modérateur (ROLE_MODERATION) ne doit PAS pouvoir accéder à /deliver (403 Forbidden)")
        void moderationCannotAccessDeliverController() throws Exception {
            mockMvc.perform(get("/deliver"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("Un utilisateur anonyme ne doit PAS pouvoir accéder à /deliver (401/403)")
        void anonymousCannotAccessDeliverController() throws Exception {
            mockMvc.perform(get("/deliver"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Tests d'accès au contrôleur Moderation (/moderation)")
    class ModerationControllerAccessTests {

        @Test
        @WithMockUser(username = "sophie.martin@example.com", roles = {"MODERATION"})
        @DisplayName("Un modérateur (ROLE_MODERATION) doit pouvoir accéder à /moderation")
        void moderationCanAccessModerationController() throws Exception {
            mockMvc.perform(get("/moderation"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "jean.dupont@example.com", roles = {"ADMIN"})
        @DisplayName("Un administrateur (ROLE_ADMIN) ne doit PAS pouvoir accéder à /moderation (403 Forbidden)")
        void adminCannotAccessModerationController() throws Exception {
            mockMvc.perform(get("/moderation"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "camille.petit@example.com", roles = {"CUSTOMER"})
        @DisplayName("Un client (ROLE_CUSTOMER) ne doit PAS pouvoir accéder à /moderation (403 Forbidden)")
        void customerCannotAccessModerationController() throws Exception {
            mockMvc.perform(get("/moderation"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "lucas.bernard@example.com", roles = {"DELIVER"})
        @DisplayName("Un livreur (ROLE_DELIVER) ne doit PAS pouvoir accéder à /moderation (403 Forbidden)")
        void deliverCannotAccessModerationController() throws Exception {
            mockMvc.perform(get("/moderation"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithAnonymousUser
        @DisplayName("Un utilisateur anonyme ne doit PAS pouvoir accéder à /moderation (401/403)")
        void anonymousCannotAccessModerationController() throws Exception {
            mockMvc.perform(get("/moderation"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Tests des routes publiques")
    class PublicRoutesTests {

        @Test
        @DisplayName("La route actuator health doit être accessible publiquement")
        void healthEndpointIsPublic() throws Exception {
            mockMvc.perform(get("/actuator/health"))
                    .andExpect(status().isOk());
        }
    }
}
