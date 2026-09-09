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
public class RestaurantControllerTest {

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
    @DisplayName("Récupération de la liste de tous les restaurants via /restaurants")
    void testGetAllRestaurantsSuccess() throws Exception {
        mockMvc.perform(get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].picture").exists())
                .andExpect(jsonPath("$[0].summary").exists());

    }

    @Test
    @DisplayName("Récupération d'un restaurant spécifique par son ID via /restaurants/{id}")
    void testGetRestaurantByIdSuccess() throws Exception {
        long restaurantId = 1L; // Assurez-vous qu'un restaurant avec cet ID existe dans votre base de test

        mockMvc.perform(get("/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.zipcode").exists())
                .andExpect(jsonPath("$.summary").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.picture").exists())
                .andExpect(jsonPath("$.locate").exists());
    }

    @Test
    @DisplayName("Échec de la récupération si le restaurant n'existe pas (404 Not Found)")
    void testGetRestaurantByIdNotFound() throws Exception {
        long nonExistentId = 9999L;

        mockMvc.perform(get("/restaurants/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
}