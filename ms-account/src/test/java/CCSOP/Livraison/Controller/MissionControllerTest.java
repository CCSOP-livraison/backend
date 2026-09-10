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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class MissionControllerTest {
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
    @DisplayName("Récupération d'une commande spécifique par son ID via /deliveries/{id}")
    void testGetDeliveriesWithoutDeliverSuccess() throws Exception {
        // GIVEN
        long deliveryId = 1L;

        // WHEN
        mockMvc.perform(get("/deliveries", deliveryId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deliveryId))
                .andExpect(jsonPath("$.dishs[0].restaurant.picture").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.zipcode").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.street").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.locality").exists());
    }

    @Test
    @DisplayName("Récupération d'une commande spécifique par son ID via /deliveries/{id}")
    void testGetDeliveryByIdSuccess() throws Exception {
        // GIVEN
        long deliveryId = 1L;

        // WHEN
        mockMvc.perform(get("/deliveries/{id}", deliveryId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deliveryId))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.deliver.name").exists())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.dishs[0].name").exists())
                .andExpect(jsonPath("$.customer").exists())
                .andExpect(jsonPath("$.customer.lastname").exists())
                .andExpect(jsonPath("$.customer.firstname").exists())
                .andExpect(jsonPath("$.customer.address.street").exists())
                .andExpect(jsonPath("$.customer.address.locality").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.zipcode").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.street").exists())
                .andExpect(jsonPath("$.dishs[0].restaurant.locality").exists())
                .andExpect(jsonPath("$.customer.address.zipcode").exists());
    }

    @Test
    @DisplayName("Échec de la récupération de la commande si l'ID n'existe pas (404 Not Found)")
    void testGetDeliveryByIdNotFound() throws Exception {
        // GIVEN
        long nonExistentId = 9999L;

        // WHEN
        mockMvc.perform(get("/deliveries/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Récupération de toutes les commandes associées à un livreur via /deliveries/deliver/{deliverId}")
    void testGetDeliveriesByDeliverIdSuccess() throws Exception {
        // GIVEN
        int deliverId = 5;

        // WHEN
        mockMvc.perform(get("/deliveries/deliver/{deliverId}", deliverId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @DisplayName("Récupération de toutes les commandes associées à un client via /deliveries/customer/{customerId}")
    void testGetDeliveriesByCustomerIdSuccess() throws Exception {
        // GIVEN
        int customerId = 12;

        // WHEN
        mockMvc.perform(get("/deliveries/customer/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].status").exists());
    }
}
