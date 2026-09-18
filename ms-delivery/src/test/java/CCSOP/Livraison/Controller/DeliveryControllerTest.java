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
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class DeliveryControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("Mise à jour du statut de la livraison à preparing par le livreur via /deliveries/{id}/preparing")
    void testPutStatusAssignByDeliverer() throws Exception {
        // GIVEN
        Long id = 1L;
        Long delivererUserId = 3L;

        String requestBody = "{\"deliverId\": " + delivererUserId + "}";

        // WHEN
        mockMvc.perform(put("/deliveries/{id}/preparing", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.status.name").value("preparing"))
                .andExpect(jsonPath("$.deliver.id").value(delivererUserId.toString()));
    }
    @Test
    @DisplayName("Mise à jour du statut de la livraison à delivering par le livreur via /deliveries/{id}/delivered")
    void testPutStatusDeliveringByDeliverer() throws Exception {
        // GIVEN
        Long id = 2L;


        // WHEN
        mockMvc.perform(put("/deliveries/{id}/delivered", id)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.status.name").value("delivered"));
    }
    @Test
    @DisplayName("Mise à jour du statut de la livraison à closed par le client (après validation) via /deliveries/{id}/close")
    void testPutStatusClosedByClient() throws Exception {
        // GIVEN
        Long id = 3L;

        // WHEN
        mockMvc.perform(put("/deliveries/{id}/close", id)
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.status.name").value("closed"));
    }
    @Test
    @DisplayName("Création réussie d'une livraison avec un ID client et une liste de plats")
    void testCreateDeliverySuccess() throws Exception {
        // GIVEN
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("customerId", 4L);
        List<Map<String, Object>> menu = List.of(
                Map.of("dishId", 1L, "quantity", 2),
                Map.of("dishId", 3L, "quantity", 1)
        );
        requestPayload.put("menu", menu); // Ou "orders", selon ce qu'attend votre contrôleur
        String jsonRequest = objectMapper.writeValueAsString(requestPayload);

        // WHEN & THEN
        mockMvc.perform(post("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated()) // Ou isOk() selon votre code (201 Created est recommandé pour un POST)
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("CMD: 300"+5))
                .andExpect(jsonPath("$.customer.id").value(4L))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders[0].dish.id").value(1L))
                .andExpect(jsonPath("$.orders[0].quantity").value(2))
                .andExpect(jsonPath("$.orders.length()").value(2))
                .andExpect(jsonPath("$.status.name").value("pending"))
                .andExpect(jsonPath("$.deliver").isEmpty())
                .andExpect(jsonPath("$.delivery_date").value(LocalDate.now().toString()));
    }
    @Test
    @DisplayName("Création échoué d'une livraison avec un ID client et une liste de plats")
    void testCreateDeliveryFail() throws Exception {
        // GIVEN
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("customerId", 4L);
        List<Map<String, Object>> menu = List.of(
                Map.of("dishId", 1L, "quantity", 0),
                Map.of("dishId", 3L, "quantity", 0)
        );
        requestPayload.put("menu", menu);
        String jsonRequest = objectMapper.writeValueAsString(requestPayload);

        // WHEN & THEN
        mockMvc.perform(post("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").exists());

    }
}
