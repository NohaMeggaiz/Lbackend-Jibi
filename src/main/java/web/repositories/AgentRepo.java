package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.models.Agent;
import web.request.AgentProjection;

import java.util.List;

@Repository
public interface AgentRepo  extends JpaRepository<Agent, Long> {
    Agent findByUsername(String username);
    Agent findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT new web.request.AgentProjection(a.id_agent, a.username, a.nom, a.prenom, a.pieceIdentite, a.numPieceIdentite, a.dateNaissance, a.adresse, a.email, a.numTel, a.numMatriculation, a.numPattente) FROM Agent a")
    List<AgentProjection> findAllAgentsProjection();

}
