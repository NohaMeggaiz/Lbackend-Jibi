package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.models.FileAgentStorage;
import web.service.FileAgentStorageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileAgentStorageController {

    @Autowired
    private FileAgentStorageService fileAgentStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("agentUsername") String agentUsername,
                                             @RequestParam("file") MultipartFile file) {
        try {
            fileAgentStorageService.storeFile(agentUsername, file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @GetMapping("/agent/{agentUsername}")
    public ResponseEntity<List<FileAgentStorage>> getFilesByAgentUsername(@PathVariable String agentUsername) {
        List<FileAgentStorage> files = fileAgentStorageService.getFilesByAgentUsername(agentUsername);
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable Long id) {
        Optional<FileAgentStorage> fileOptional = fileAgentStorageService.getFileById(id);

        if (fileOptional.isPresent()) {
            FileAgentStorage file = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileAgentStorage>> getAllFiles() {
        List<FileAgentStorage> files = fileAgentStorageService.getAllFiles();
        return ResponseEntity.ok().body(files);
    }
}

