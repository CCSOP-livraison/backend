package CCSOP.Livraison.Entities;
import jakarta.persistence.*;

@Table(name="restaurants")
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String summary;
    private String address;
    private String zipcode;
    private String locate;
    private String picture;



    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public String getLocate() {
        return this.locate;
    }
    public String getDescription() {
        return this.description;
    }
    public String getSummary() {
        return this.summary;
    }
    public String getPicture() {
        return this.picture;
    }

}
