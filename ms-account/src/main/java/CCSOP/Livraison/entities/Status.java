package CCSOP.Livraison.entities;

import jakarta.persistence.*;
@Table(name="status_deliveries")
@Entity
public class Status {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}