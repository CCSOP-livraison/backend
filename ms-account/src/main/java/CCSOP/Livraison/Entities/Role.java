package CCSOP.Livraison.Entities;
import jakarta.persistence.*;
import java.util.Collection;
@Table(name="roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "id_role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "id_privilege", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name=name;
    }

    public Role() {

    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public Collection<? extends Privilege> getPrivileges() {
        return this.privileges;
    }
}
