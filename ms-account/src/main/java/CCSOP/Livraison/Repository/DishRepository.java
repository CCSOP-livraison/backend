package CCSOP.Livraison.Repository;

import CCSOP.Livraison.entities.Dish;
import CCSOP.Livraison.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
