package CCSOP.Livraison.Service;
import CCSOP.Livraison.Repository.DeliveryRepository;
import CCSOP.Livraison.Entities.Deliver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Deliver> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Deliver> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public List<Deliver> getDeliveriesByDeliverId(Long deliverId) {
        return deliveryRepository.findByDeliverId(deliverId);
    }

    public List<Deliver> getDeliveriesByCustomerId(Long customerId) {
        return deliveryRepository.findByCustomerId(customerId);
    }
}
