package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.Client;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByNumTel(String numTel);

    boolean existsByNumTel(String numTel); // Add this method

  //  Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    void deleteByNumTel(String numTel);

    Client findById(long id);


}
