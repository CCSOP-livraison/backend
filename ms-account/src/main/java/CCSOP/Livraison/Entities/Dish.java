package CCSOP.Livraison.Entities;

import jakarta.persistence.*;
@Table(name="dishs")
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double price;
    private String name;
    private String description;
    @JoinColumn(name = "id_restaurant")
    @ManyToOne
    private Restaurant restaurant;

    public long getId() {
        return this.id;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
    public Restaurant getRestaurant(){
        return this.restaurant;
    }
}