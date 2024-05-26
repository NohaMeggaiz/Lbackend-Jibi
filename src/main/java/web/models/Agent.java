package web.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_agent;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    @Column(name = "nom",nullable = false)
    private String nom;

    @Column(name = "prenom",nullable = false)
    private String prenom;

    @Column(name = "pieceIdentite",nullable = false)
    private String pieceIdentite;

    @Column(name = "numPieceIdentite",nullable = false)
    private String numPieceIdentite;

    @Column(name = "dateNaissance",nullable = false)
    private Date dateNaissance;

    @Column(name = "adresse",nullable = false)
    private String adresse;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="numTel",nullable = false)
    private String numTel;

    @Column(name="numMatriculation",nullable = false)
    private String numMatriculation;

    @Column(name="numPattente",nullable = false)
    private String numPattente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileAgentStorage idDocument;


    @Column(name="role")
    private String role = "ROLE_AGENT";

   // @Column(name="firstAuth",nullable = false)
    //private Boolean firstAuth;

    public Long getId_user() {
        return id_agent;
    }



}
