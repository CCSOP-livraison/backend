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
    @Column(name = "id_status")
    private Long statusId;
    @Column(name = "id_deliverer")
    private Long deliverId;
    @Column(name = "id_customer")
    private Long customerId;
    @ManyToMany
    private List<Dish> dishs;
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

    public Long getStatus() {
        return statusId;
    }

    public Long getDeliver() {
        return deliverId;
    }

    public Long getCustomer() {
        return customerId;
    }

    public List<Dish> getDishs()
    {
        return dishs;
    }
}
