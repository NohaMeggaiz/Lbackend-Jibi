package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.models.FileAgentStorage;

import java.util.List;

public interface FileAgentStorageRepository extends JpaRepository<FileAgentStorage, Long> {
    List<FileAgentStorage> findByAgentUsername(String agentUsername);
}
