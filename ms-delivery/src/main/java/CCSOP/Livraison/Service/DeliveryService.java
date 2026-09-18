package CCSOP.Livraison.Service;

import CCSOP.Livraison.Entitie.*;
import CCSOP.Livraison.Exception.EmptyCartException;
import CCSOP.Livraison.Repository.DeliveryRepository;
import CCSOP.Livraison.Repository.DishRepository;
import CCSOP.Livraison.Repository.StatutRepository;
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
    private DishRepository dishRepository;

    @Autowired
    private StatutRepository statutRepository;

    public List<Delivery> getAllDeliveriesNotAttribute() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        List<Delivery> deliversNotAttribute = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            if (delivery.getDeliver() == null) {
                deliversNotAttribute.add(delivery);
            }
        }
        return deliversNotAttribute;
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    /// TO DO: CHECK THE STATUS BY SENDING A REQUEST TO THE CORRESPONDING MICROSERVICE TO RETRIEVE THE DELIVERY PERSON'S ID
    public Delivery putStatusAssignByDeliverer(Long id, Long deliverId) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
        if(delivery.getStatus().getName().equals("pending")){
            //User deliverer = userRepository.findById(deliverId)
                 //   .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
           // delivery.setDeliver(deliverer);
            Statut deliveredStatut = statutRepository.findByName("preparing")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            delivery.setStatus(deliveredStatut);
        }
        return deliveryRepository.save(delivery);
    }

    public Delivery putStatusDelivered (Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));

        if(delivery.getStatus().getName().equals("preparing")){
            Statut deliveredStatut = statutRepository.findByName("delivered")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            delivery.setStatus(deliveredStatut);
        }
        return deliveryRepository.save(delivery);
    }

    public Delivery putStatusClosedByClient(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livraison non trouvée avec l'id : " + id));
        if(delivery.getStatus().getName().equals("delivered")){
            Statut deliveredStatut = statutRepository.findByName("closed")
                    .orElseThrow(() -> new IllegalStateException("Le statut 'delivered' est introuvable en base de données"));
            delivery.setStatus(deliveredStatut);
        }
        return deliveryRepository.save(delivery);
    }


    public List<Delivery> getDeliveriesByDeliverId(Long deliverId) {
        return deliveryRepository.findByDeliverId(deliverId);
    }

    public List<Delivery> getDeliveriesByCustomerId(Long customerId) {
        return deliveryRepository.findByCustomerId(customerId);
    }
    /// TO DO: CHECK THE STATUS BY SENDING A REQUEST TO THE CORRESPONDING MICROSERVICE TO RETRIEVE THE CUSTOMER PERSON'S ID

    @Transactional
    public Delivery createDelivery(Long customerId, List<Map<String, Object>> items) {
        //User customer = userRepository.findById(customerId)
        //        .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        Statut pendingStatut = statutRepository.findByName("pending")
                .orElseGet(() -> statutRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("Default pending status not found")));


        Delivery delivery = new Delivery();
        List<Order> orders = new ArrayList<>();
        if (items != null) {
            for (Map<String, Object> item : items) {
                if(((Number) item.get("quantity")).intValue()!=0) {
                    Long dishId = ((Number) item.get("dishId")).longValue();
                    int quantity = ((Number) item.get("quantity")).intValue();

/// TO DO: CHECK THE STATUS BY SENDING A REQUEST TO THE CORRESPONDING MICROSERVICE TO RETRIEVE THE DISH'S ID

                   // Dish dish = dishRepository.findById(dishId)
                    //        .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + dishId));

                    Order order = new Order();
                    order.setDelivery(delivery);
                    //order.setDish(dish);
                    order.setQuantity(quantity);
                    orders.add(order);
                }
            }
        }
        if(orders.isEmpty()){
            throw new EmptyCartException("Impossible de créer une commande : le panier est vide.");
        }
        //delivery.setCustomer(customer);
        delivery.setStatus(pendingStatut);
        delivery.setDelivery_date(LocalDate.now());
        delivery.setOrders(orders);
        delivery.setName("CMD: init");
        Delivery savedDelivery =deliveryRepository.save(delivery);
        savedDelivery.setName("CMD: 300"+ savedDelivery.getId());
        return deliveryRepository.save(savedDelivery);
    }
}
