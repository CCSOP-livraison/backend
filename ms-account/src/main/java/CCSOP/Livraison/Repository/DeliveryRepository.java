package CCSOP.Livraison.Repository;

import CCSOP.Livraison.Entities.Deliver;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Deliver, Long> {
    List<Deliver> findByDeliverId(int deliverId);
    List<Deliver> findByCustomerId(int customerId);
}
