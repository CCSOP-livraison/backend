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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DishControllerTest {
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
    @DisplayName("GET restaurants/restaurantId}/dishes")
    void getDishesByRestaurant_ShouldReturnListOfDishes_WhenRestaurantExists() throws Exception {
        //GIVEN
        long restaurantId = 1L;
        // WHEN
        mockMvc.perform(get("/restaurants/{restaurantId}/dishes",restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Quenelle de Brochet")))
                .andExpect(jsonPath("$[0].price", is(18.50)))
                .andExpect(jsonPath("$[0].description", is("Quenelle artisanale nappée de sa sauce Nantua d exception.")))
                .andExpect(jsonPath("$[0].restaurant_id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Salade Lyonnaise")));
    }

    @Test
    @DisplayName("GET restaurants/{restaurantId}/dishes - Liste vide")
    void getDishesByRestaurant_ShouldReturnEmptyList_WhenNoDishesFound() throws Exception {
        //GIVEN
        long restaurantId = 9999L;
        // WHEN
        mockMvc.perform(get("/restaurants/{restaurantId}/dishes",restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}