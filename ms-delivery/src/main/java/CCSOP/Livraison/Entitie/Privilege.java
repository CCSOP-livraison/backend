package CCSOP.Livraison.Entitie;
import jakarta.persistence.*;
import java.util.Collection;
@Table(name="privileges")
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name=name;
    }

    public Privilege() {

    }

    public String getName() {
        return this.name;
    }
}
