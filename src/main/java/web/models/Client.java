package web.models;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_client;

   // @Column(name = "username")
    //private String username;
    @Column(name = "nom",nullable = false)
    private String nom;

    @Column(name = "prenom",nullable = false)
    private String prenom;

    @Column(name = "numTel",nullable = false)
    private  String numTel;


    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;


   /* @Column(name="firstAuth",nullable = false)
    private Boolean firstAuth = true;*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_compte")
    private Compte compte;

    @Column(name="role")
    private String role = "ROLE_CLIENT";

    @ManyToMany(mappedBy = "clients")
    private Set<Transaction> transactions = new HashSet<>();

}
