package web.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class AgentProjection {
    private Long id_agent;

    private String username;

    private String nom;

    private String prenom;

    private String pieceIdentite;

    private String numPieceIdentite;

    private Date dateNaissance;

    private String adresse;

    private String email;

    private String password;

    private String numTel;

    private String numMatriculation;

    private String numPattente;

    public AgentProjection(Long id_agent, String username, String nom, String prenom, String pieceIdentite, String numPieceIdentite, Date dateNaissance, String adresse, String email, String numTel, String numMatriculation, String numPattente) {
        this.id_agent = id_agent;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.pieceIdentite = pieceIdentite;
        this.numPieceIdentite = numPieceIdentite;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.email = email;
        this.numTel = numTel;
        this.numMatriculation = numMatriculation;
        this.numPattente = numPattente;
    }

}
