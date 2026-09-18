package CCSOP.Livraison.Entitie;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Table(name="deliveries")
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_status")
    private Statut statut;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_deliverer",nullable = true)
    private User deliver;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private User customer;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> orders;

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate delivery_date;


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Statut statut) {
        this.statut = statut;
    }

    public void setDeliver(User deliver) {
        this.deliver = deliver;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setDelivery_date(LocalDate delivery_date) {
        this.delivery_date = delivery_date;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDelivery_date() {
        return delivery_date;
    }

    public Statut getStatus() {
        return statut;
    }

    public User getDeliver() {
        return deliver;
    }

    public User getCustomer() {
        return customer;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
