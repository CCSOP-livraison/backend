package CCSOP.Livraison.Service;

import CCSOP.Livraison.Entities.Deliver;
import CCSOP.Livraison.Entities.Dish;
import CCSOP.Livraison.Entities.Order;
import CCSOP.Livraison.Entities.Status;
import CCSOP.Livraison.Entities.User;
import CCSOP.Livraison.Exception.EmptyCartException;
import CCSOP.Livraison.Repository.DeliveryRepository;
import CCSOP.Livraison.Repository.DishRepository;
import CCSOP.Livraison.Repository.StatusRepository;
import CCSOP.Livraison.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<Deliver> getAllDeliveriesNotAttribute() {
        List<Deliver> delivers = deliveryRepository.findAll();
        List<Deliver> deliversNotAttribute = new ArrayList<>();
        for (Deliver deliver : delivers) {
            if (deliver.getDeliver() == null) {
                deliversNotAttribute.add(deliver);
            }
        }
        return deliversNotAttribute;
    }

    public Optional<Deliver> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Deliver putStatusAssignByDeliverer(Long id, Long deliverId) {
        Deliver deliver = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
        if(deliver.getStatus().getName().equals("pending")){
            User deliverer = userRepository.findById(deliverId)
                    .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
            deliver.setDeliver(deliverer);
            Status deliveredStatus = statusRepository.findByName("preparing")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            deliver.setStatus(deliveredStatus);
        }
        return deliveryRepository.save(deliver);
    }

    public Deliver putStatusDelivered (Long id) {
        Deliver deliver = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));

        if(deliver.getStatus().getName().equals("preparing")){
            Status deliveredStatus = statusRepository.findByName("delivered")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            deliver.setStatus(deliveredStatus);
        }
        return deliveryRepository.save(deliver);
    }

    public Deliver putStatusClosedByClient(Long id) {
        Deliver deliver = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
        if(deliver.getStatus().getName().equals("delivered")){
            Status deliveredStatus = statusRepository.findByName("closed")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            deliver.setStatus(deliveredStatus);
        }
        return deliveryRepository.save(deliver);
    }


    public List<Deliver> getDeliveriesByDeliverId(Long deliverId) {
        return deliveryRepository.findByDeliverId(deliverId);
    }

    public List<Deliver> getDeliveriesByCustomerId(Long customerId) {
        return deliveryRepository.findByCustomerId(customerId);
    }
    @Transactional
    public Deliver createDelivery(Long customerId, List<Map<String, Object>> items) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        Status pendingStatus = statusRepository.findByName("pending")
                .orElseGet(() -> statusRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("Default pending status not found")));


        Deliver deliver = new Deliver();
        List<Order> orders = new ArrayList<>();
        if (items != null) {
            for (Map<String, Object> item : items) {
                if(((Number) item.get("quantity")).intValue()!=0) {
                    Long dishId = ((Number) item.get("dishId")).longValue();
                    int quantity = ((Number) item.get("quantity")).intValue();


                    Dish dish = dishRepository.findById(dishId)
                            .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + dishId));

                    Order order = new Order();
                    order.setDeliver(deliver);
                    order.setDish(dish);
                    order.setQuantity(quantity);
                    orders.add(order);
                }
            }
        }
        if(orders.isEmpty()){
            throw new EmptyCartException("Impossible de créer une commande : le panier est vide.");
        }
        deliver.setCustomer(customer);
        deliver.setStatus(pendingStatus);
        deliver.setDelivery_date(LocalDate.now());
        deliver.setOrders(orders);
        deliver.setName("CMD: init");
        Deliver savedDeliver=deliveryRepository.save(deliver);
        savedDeliver.setName("CMD: 300"+savedDeliver.getId());
        return deliveryRepository.save(savedDeliver);
    }
}
