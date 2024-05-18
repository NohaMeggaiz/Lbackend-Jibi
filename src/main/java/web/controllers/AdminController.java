package web.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.models.Admin;
import web.models.Agent;
import web.repositories.AdminRepo;
import web.repositories.AgentRepo;
import web.request.SignupRequest;
import web.request.SignupRequestAgent;
import web.service.AdminService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminserv;

    @Autowired
    AgentRepo agentRepo;

    @Autowired
    AdminRepo adminRepo;

    @PostMapping
    public ResponseEntity<?> createAgent(@RequestBody SignupRequestAgent agentRequest) throws IOException {
        // Check if the email already exists
        if (agentRepo.existsByEmail(agentRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Proceed to create the agent if the email does not exist
        boolean isCreated = adminserv.createAgent(
                agentRequest.getNom(),
                agentRequest.getPrenom(),
                agentRequest.getPieceIdentite(),
                agentRequest.getNumPieceIdentite(),
                agentRequest.getDateNaissance(),
                agentRequest.getAdresse(),
                agentRequest.getEmail(),
                agentRequest.getNumTel(),
                agentRequest.getNumMatriculation(),
                agentRequest.getNumPattente()
        );

        if (isCreated) {
            return ResponseEntity.ok().body("Agent created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error: Agent creation failed!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Agent>>  getAgents() {
        return ResponseEntity.ok().body(
                adminserv.getAgents());
    }

    @GetMapping("/agent/{uid}")
    public ResponseEntity<?> getAgent(@PathVariable String uid) {
        Agent agent = adminserv.getAgent(uid);
        if (agent == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Agent not found!");
        }
        return ResponseEntity.ok(agent);
    }
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAdmins() {
        return ResponseEntity.ok().body(adminserv.getAdmins());
    }


    @PostMapping("/admin/signup")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        if (adminRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (adminRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account & also here m
        Admin user = new Admin(signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail());


        adminserv.saveAdmin(user);

        return ResponseEntity.ok("Admin registered successfully!");
    }


}
