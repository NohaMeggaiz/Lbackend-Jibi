package web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_transaction;
    @Column(name = "montant")
    private double montant;
    @OneToOne(cascade = CascadeType.ALL)

    private Client client_source;

    @OneToOne(cascade = CascadeType.ALL)

    private Creancier creancier_dest;

    @Column(name = "date_transaction",nullable = false)
    private Date date_transaction;

}
