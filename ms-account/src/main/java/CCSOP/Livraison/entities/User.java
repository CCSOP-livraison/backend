package CCSOP.Livraison.entities;

import jakarta.persistence.*;

@Table(name= "\" user\"")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastname;
    private String firstname;
    private String address;
    private String zipcode;
    private String locate;
    private String email;
    private String phoneNumber;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPresent() {
        return true;
    }
}

