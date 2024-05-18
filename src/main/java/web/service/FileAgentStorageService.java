package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.models.FileAgentStorage;
import web.repositories.FileAgentStorageRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileAgentStorageService {

    @Autowired
    private FileAgentStorageRepository fileAgentStorageRepository;

    public FileAgentStorage storeFile(String agentUsername, MultipartFile file) throws IOException {
        FileAgentStorage fileAgentStorage = new FileAgentStorage();
        fileAgentStorage.setAgentUsername(agentUsername);
        fileAgentStorage.setFileName(file.getOriginalFilename());
        fileAgentStorage.setFileData(file.getBytes());

        return fileAgentStorageRepository.save(fileAgentStorage);
    }

    public List<FileAgentStorage> getFilesByAgentUsername(String agentUsername) {
        return fileAgentStorageRepository.findByAgentUsername(agentUsername);
    }

    public Optional<FileAgentStorage> getFileById(Long id) {
        return fileAgentStorageRepository.findById(id);
    }

    public List<FileAgentStorage> getAllFiles() {
        return fileAgentStorageRepository.findAll();
    }
}
