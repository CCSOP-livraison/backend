package CCSOP.Livraison.Entities;

import jakarta.persistence.*;

@Table(name="deliveries_dishs")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deliver")
    private Deliver deliver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dishs")
    private Dish dish;
    private int quantity;

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }
}