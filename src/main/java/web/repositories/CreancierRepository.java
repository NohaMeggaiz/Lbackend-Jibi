package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.models.Creancier;

@Repository
public interface CreancierRepository extends JpaRepository<Creancier,Long> {
    Creancier findByCode(String code);
}