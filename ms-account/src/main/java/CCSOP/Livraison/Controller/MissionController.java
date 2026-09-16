package CCSOP.Livraison.Controller;
import CCSOP.Livraison.Service.DeliveryService;
import CCSOP.Livraison.Entities.Deliver;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deliveries")
public class MissionController {
    public record CreateDeliveryRequest(Long customerId, List<Map<String, Object>> menu, List<Map<String, Object>> orders) {}
    public record AssignDeliveryRequest(Long deliverId) {}
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

    @PutMapping("/{id}/delivered")
    public ResponseEntity<Object> putStatusDelivered(@PathVariable Long id) {
        try {
            Deliver updatedDelivery = deliveryService.putStatusDelivered(id);
            return ResponseEntity.ok(updatedDelivery);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
    @PutMapping("/{id}/preparing")
    public ResponseEntity<Object> testPutStatusAssignByDeliverer(@RequestBody AssignDeliveryRequest request, @PathVariable Long id) {
        try {
            Deliver updatedDelivery = deliveryService.putStatusAssignByDeliverer(id,request.deliverId);
            return ResponseEntity.ok(updatedDelivery);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
    @PutMapping("/{id}/close")
    public ResponseEntity<Object> testPutStatusClosedByClient(@PathVariable Long id) {
        try {
            Deliver updatedDelivery = deliveryService.putStatusClosedByClient(id);
            return ResponseEntity.ok(updatedDelivery);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
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



    @PostMapping
    public ResponseEntity<Deliver> createDelivery(@RequestBody CreateDeliveryRequest request) {
        List<Map<String, Object>> menu = request.menu() != null ? request.menu() : request.orders();
        Deliver created = deliveryService.createDelivery(request.customerId(), menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

