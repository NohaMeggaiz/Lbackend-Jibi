package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.models.Admin;
import web.request.AgentProjection;

import java.util.List;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}