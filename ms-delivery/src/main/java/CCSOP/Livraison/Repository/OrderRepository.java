package CCSOP.Livraison.Repository;

import CCSOP.Livraison.Entitie.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
