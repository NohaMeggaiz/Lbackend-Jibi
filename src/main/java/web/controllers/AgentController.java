package web.controllers;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.models.Client;
import web.repositories.AdminRepo;
import web.repositories.ClientRepository;
import web.request.ChangePasswordAgentRequest;
import web.request.CreateCompteRequest;
import web.request.SignupRequestClient;
import web.service.AgentService;
import web.service.ClientService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    ClientService userService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/client/add")
    public ResponseEntity<?> registerClient(@RequestBody SignupRequestClient signupRequestClient)
            throws IOException, MessagingException {

        // Check if numTel already exists
        if (clientRepository.existsByNumTel(signupRequestClient.getNumTel())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: NumTel is already in use!");
        }

        // Check if email already exists
        if (clientRepository.existsByEmail(signupRequestClient.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        boolean isCreated = agentService.createClient(
                //  signupRequestClient.getUsername(),
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

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordAgentRequest changePasswordRequestAgent) {
        agentService.changePassword(changePasswordRequestAgent.getUsername(), changePasswordRequestAgent.getNewPassword());

        return ResponseEntity.ok("password changed successfully for Agent");
    }


    @PostMapping(value = "/createbankAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createClientBankAccount(@Valid @RequestBody CreateCompteRequest createCompteRequest) throws IOException {

        System.out.println("inside api : " + createCompteRequest.getTypecompte() + " ----" + createCompteRequest.getNumTel());
        userService.createCompteToUser(createCompteRequest.getNumTel(), createCompteRequest.getTypecompte());
        return ResponseEntity.ok().body("bank account has been created");
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> AllClients() {
        List<Client> users = agentService.getAllClients();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/clientwithBA")
    public ResponseEntity<?> registerClientwithBA(@RequestBody SignupRequestClient signupRequestClient)
            throws IOException, MessagingException {

        // Check if numTel already exists
        if (clientRepository.existsByNumTel(signupRequestClient.getNumTel())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: NumTel is already in use!");
        }

        // Check if email already exists
        if (clientRepository.existsByEmail(signupRequestClient.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        boolean isCreated = agentService.createClient(
                //  signupRequestClient.getUsername(),
                signupRequestClient.getNom(),
                signupRequestClient.getPrenom(),
                signupRequestClient.getEmail(),
                signupRequestClient.getNumTel()
        );

        if (isCreated) {
            System.out.println("inside api : " + signupRequestClient.getTypecompte() + " ----" + signupRequestClient.getNumTel());
            userService.createCompteToUser(signupRequestClient.getNumTel(), signupRequestClient.getTypecompte());
            return ResponseEntity.ok().body("Client created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error: Client creation failed!");
        }


    }



    @DeleteMapping("/DeleteClient/{numTel}")
    public ResponseEntity<String> deleteClient(@PathVariable String numTel) {
        try {
            userService.deleteClientByNumTel(numTel);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/client")
    public ResponseEntity<Client> getClient(String numTel) {
        Client client = agentService.getClient(numTel);
        return ResponseEntity.ok(client);
    }
}




