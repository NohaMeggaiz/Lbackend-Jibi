package web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Creancier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_creancier;

    @Column(unique = true,nullable = false)
    private String code;
    private String nom_creancier;
    private String logo_Url;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_compte")
    private Compte compte_creancier;

    @ManyToMany(mappedBy = "creanciers")
    private Set<Transaction> transactions = new HashSet<>();

}