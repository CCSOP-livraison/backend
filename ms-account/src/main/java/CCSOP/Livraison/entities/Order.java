package CCSOP.Livraison.entities;

import jakarta.persistence.*;

@Table(name="deliveries_dishs")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "deliver_id")
    private Long deliverId;
    @Column(name = "dishs_id")
    private Long dishId;
    private int quantity;
}