package web.controllers;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.models.Admin;
import web.models.Agent;
import web.repositories.AdminRepo;
import web.repositories.AgentRepo;
import web.request.AgentProjection;
import web.request.ChangePasswordAgentRequest;
import web.request.SignupRequest;
import web.request.SignupRequestAgent;
import web.service.AdminService;

import java.io.IOException;
import java.util.Date;
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

    //les fonctions li kidir admin 3la l'agent
    @PostMapping("/addAgent")
    public ResponseEntity<?> registerAgent(@RequestParam("nom") String nom,
                                           @RequestParam("prenom") String prenom,
                                           @RequestParam("pieceIdentite") String pieceIdentite,
                                           @RequestParam("numPieceIdentite") String numPieceIdentite,
                                           @RequestParam("dateNaissance") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateNaissance,
                                           @RequestParam("adresse") String adresse,
                                           @RequestParam("email") String email,
                                           @RequestParam("numTel") String numTel,
                                           @RequestParam("numMatriculation") String numMatriculation,
                                           @RequestParam("numPattente") String numPattente,
                                           @RequestParam("file") MultipartFile file) throws IOException, MessagingException {
        boolean isCreated = adminserv.createAgent(nom, prenom, pieceIdentite, numPieceIdentite, dateNaissance, adresse, email, numTel, numMatriculation, numPattente, file);
        if (isCreated) {
            return ResponseEntity.ok().body("Agent created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error: Agent creation failed!");
        }
    }
    @GetMapping("/agents")
    public ResponseEntity<List<AgentProjection>>  getAgents() {
        List<AgentProjection> users =  adminserv.getAgents();
        return ResponseEntity.ok(users);
    }
    /*
      @GetMapping("/clients")
    public ResponseEntity<List<Client>> AllClients() {
        List<Agent> users =  adminserv.getAgents();
        return ResponseEntity.ok(users);
    }
*/

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

    @DeleteMapping("/agent/delete/{uid}")
    public ResponseEntity<?> deleteAgent(@PathVariable String uid) {
        try {
            adminserv.deleteAgentByUid(uid);
            return ResponseEntity.ok().body("Agent deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: Unable to delete agent.");
        }
    }


    //les fonctions 3la admins
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

    @GetMapping("/getadmin/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable Long id) {
        Admin admin = adminserv.getAdmin(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(404).body("Error: Admin not found!");
        }
    }
    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        boolean isDeleted = adminserv.deleteAdminById(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("Admin deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Error: Admin not found!");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordAgentRequest changePasswordRequestAgent) {
        adminserv.changePassword(changePasswordRequestAgent.getUsername(), changePasswordRequestAgent.getNewPassword());

        return ResponseEntity.ok("password changed successfully for Agent");
    }


}
