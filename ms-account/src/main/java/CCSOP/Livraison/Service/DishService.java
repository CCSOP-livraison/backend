package CCSOP.Livraison.Service;
import CCSOP.Livraison.Repository.DishRepository;
import CCSOP.Livraison.entities.Dish;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getDishesByRestaurant(long restaurantId) {
        List<Dish> dishes=new ArrayList<>();
        for(Dish dish : dishRepository.findAll()){
            if(dish.getRestaurant_id()==restaurantId){
                dishes.add(dish);
            }
        }
        return dishes;
    }
}
