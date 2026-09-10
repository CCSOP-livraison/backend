package CCSOP.Livraison.Controller;
import CCSOP.Livraison.Service.DeliveryService;
import CCSOP.Livraison.Entities.Deliver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class MissionController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Deliver>> getAllDeliveries() {
        List<Deliver> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deliver> getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping("/deliver/{deliverId}")
    public ResponseEntity<List<Deliver>> getDeliveriesByDeliverId(@PathVariable int deliverId) {
        List<Deliver> deliveries = deliveryService.getDeliveriesByDeliverId(deliverId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Deliver>> getDeliveriesByCustomerId(@PathVariable int customerId) {
        List<Deliver> deliveries = deliveryService.getDeliveriesByCustomerId(customerId);
        return ResponseEntity.ok(deliveries);
    }
}
