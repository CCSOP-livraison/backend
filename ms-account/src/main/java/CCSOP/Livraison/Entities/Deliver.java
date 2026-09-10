package CCSOP.Livraison.Entities;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Table(name="deliveries")
@Entity
public class Deliver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status; //

    @ManyToOne
    @JoinColumn(name = "deliver_id")
    private User deliver; //

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "deliveries_dishs",
            joinColumns = @JoinColumn(name = "deliver_id"),
            inverseJoinColumns = @JoinColumn(name = "dishs_id")
    )
    private List<Dish> orders;
    private Date delivery_date;


    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }

    public Status getStatus() {
        return status;
    }

    public User getDeliver() {
        return deliver;
    }

    public User getCustomer() {
        return customer;
    }

    public List<Dish> getDishs()
    {
        return orders;
    }
}
