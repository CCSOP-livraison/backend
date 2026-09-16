package CCSOP.Livraison.Service;

import CCSOP.Livraison.Entitie.Order;
import CCSOP.Livraison.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> saveAllOrders(List<Order> orders) {
        return orderRepository.saveAll(orders);
    }
}
