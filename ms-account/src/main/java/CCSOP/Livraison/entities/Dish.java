package CCSOP.Livraison.entities;

import jakarta.persistence.*;
@Table(name="dishs")
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float price;
    private String name;
    private String description;
    @Column(name = "id_restaurant")
    private Long restaurantId;

    public long getId() {
        return this.id;
    }

    public Float getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public String description() {
        return this.description;
    }
    public Long getRestaurant_id(){
        return this.restaurantId;
    }
}