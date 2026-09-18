package CCSOP.Livraison.Repository;

import CCSOP.Livraison.Entitie.Delivery;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDeliverId(Long deliverId);
    List<Delivery> findByCustomerId(Long customerId);
}
