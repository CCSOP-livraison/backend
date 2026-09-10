package CCSOP.Livraison.Controller;
import CCSOP.Livraison.Service.DeliveryService;
import CCSOP.Livraison.Entities.Deliver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deliveries")
public class MissionController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Deliver>> getAllDeliveries() {
        List<Deliver> deliveries = deliveryService.getAllDeliveriesNotAttribute();
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeliveryById(@PathVariable Long id) {
        if (deliveryService.getDeliveryById(id).isPresent()) {
            return ResponseEntity.ok(deliveryService.getDeliveryById(id).get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Restaurant non trouvé");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }

    @GetMapping("/deliver/{deliverId}")
    public ResponseEntity<List<Deliver>> getDeliveriesByDeliverId(@PathVariable Long deliverId) {
        List<Deliver> deliveries = deliveryService.getDeliveriesByDeliverId(deliverId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Deliver>> getDeliveriesByCustomerId(@PathVariable Long customerId) {
        List<Deliver> deliveries = deliveryService.getDeliveriesByCustomerId(customerId);
        return ResponseEntity.ok(deliveries);
    }
}
