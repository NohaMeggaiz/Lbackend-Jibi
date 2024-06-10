package web.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import web.models.Compte;

@RestController
public interface CompteRepo extends JpaRepository<Compte, Long> {
    Compte findByRib(String rib);



}
