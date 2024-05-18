package web.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.repositories.AdminRepo;
import web.repositories.ClientRepository;
import web.request.SignupRequestClient;
import web.service.AgentService;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/client/add")
    public ResponseEntity<?> registerClient(@RequestBody SignupRequestClient signupRequestClient)
            throws IOException, MessagingException {

        // Check if username already exists
        if (clientRepository.existsByUsername(signupRequestClient.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Check if email already exists
        if (clientRepository.existsByEmail(signupRequestClient.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        boolean isCreated = agentService.createClient(
                signupRequestClient.getUsername(),
                signupRequestClient.getNom(),
                signupRequestClient.getPrenom(),
                signupRequestClient.getEmail(),
                signupRequestClient.getNumTel()
        );

        if (isCreated) {
            return ResponseEntity.ok().body("Client created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error: Client creation failed!");
        }
    }
}
