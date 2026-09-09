package CCSOP.Livraison.controller;

import CCSOP.Livraison.Repository.RestaurantRepository;
import CCSOP.Livraison.Service.DishService;
import CCSOP.Livraison.entities.Dish;
import CCSOP.Livraison.entities.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final DishService dishService ;

    public RestaurantController(RestaurantRepository restaurantRepository, DishService dishService) {
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable Long id) {
        if (restaurantRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(restaurantRepository.findById(id).get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Restaurant non trouvé");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<Dish>> getAllDishs( @PathVariable Long restaurantId) {
        List<Dish> dishes = dishService.getDishesByRestaurant(restaurantId) ;
        return ResponseEntity.ok(dishes);
    }
}
