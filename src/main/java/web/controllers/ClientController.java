package web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.models.Client;
import web.request.ChangePasswordAgentRequest;
import web.service.ClientService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

    @Autowired
    private ClientService clientService;

    // ... existing endpoints

    /**
     * Get the balance of the account for a client by their ID.
     *
     * @param id the ID of the client
     * @return the balance of the client's account
     */
    @GetMapping("/client/{id}/compte/solde")
    public double getCompteSolde(@PathVariable long id) {
        return clientService.getCompteSolde(id);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordAgentRequest changePasswordRequestClient) {
        clientService.changePassword(changePasswordRequestClient.getUsername(), changePasswordRequestClient.getNewPassword());

        return ResponseEntity.ok("password changed successfully for Client");
    }
}