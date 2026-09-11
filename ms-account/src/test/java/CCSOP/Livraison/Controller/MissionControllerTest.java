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
        long deliveryId = 4L;

        // WHEN
        mockMvc.perform(get("/deliveries", deliveryId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(deliveryId))
                .andExpect(jsonPath("$.[0].status.name").exists())
                .andExpect(jsonPath("$.[0].orders").isArray())
                .andExpect(jsonPath("$.[0].orders[0].dish.restaurant.picture").exists())
                .andExpect(jsonPath("$.[0].orders[0].dish.restaurant.zipcode").exists())
                .andExpect(jsonPath("$.[0].orders[0].dish.restaurant.address").exists())
                .andExpect(jsonPath("$.[0].orders[0].dish.restaurant.locate").exists())
                .andExpect(jsonPath("$.[0].orders[0].dish.restaurant.name").exists())
                .andExpect(jsonPath("$.[0].deliver").doesNotExist());
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
                .andExpect(jsonPath("$.status.name").exists())
                .andExpect(jsonPath("$.deliver.lastname").exists())
                .andExpect(jsonPath("$.deliver.firstname").exists())
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders[0].dish.name").exists())
                .andExpect(jsonPath("$.customer").exists())
                .andExpect(jsonPath("$.customer.lastname").exists())
                .andExpect(jsonPath("$.customer.firstname").exists())
                .andExpect(jsonPath("$.customer.address").exists())
                .andExpect(jsonPath("$.customer.zipcode").exists())
                .andExpect(jsonPath("$.customer.locate").exists())
                .andExpect(jsonPath("$.orders[0].dish.restaurant.zipcode").exists())
                .andExpect(jsonPath("$.orders[0].dish.restaurant.address").exists())
                .andExpect(jsonPath("$.orders[0].quantity").exists())
                .andExpect(jsonPath("$.orders[0].dish.restaurant.locate").exists())
                .andExpect(jsonPath("$.orders[0].dish.restaurant.name").exists());
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
        Long deliverId = 3L;

        // WHEN
        mockMvc.perform(get("/deliveries/deliver/{deliverId}", deliverId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].status.name").exists())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @DisplayName("Récupération de toutes les commandes associées à un client via /deliveries/customer/{customerId}")
    void testGetDeliveriesByCustomerIdSuccess() throws Exception {
        // GIVEN
        Long customerId = 4L;

        // WHEN
        mockMvc.perform(get("/deliveries/customer/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].status.name").exists());
    }
}
