package CCSOP.Livraison.Entities;

import jakarta.persistence.*;

@Table(name="deliveries_dishs")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @Column(name = "id_deliver")
    private Long deliverId;
    @Column(name = "id_dishs")
    private Long dishId;
    private int quantity;
}