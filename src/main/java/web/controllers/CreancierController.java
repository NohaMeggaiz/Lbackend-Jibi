package web.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.models.Creancier;
import web.request.AddCrRequest;
import web.service.CreancierService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/creancier")
public class CreancierController {

    private final CreancierService creancierService;

    @Autowired
    public CreancierController(CreancierService creancierService) {
        this.creancierService = creancierService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> registerClient(@RequestBody AddCrRequest addCrRequest)
            throws IOException, MessagingException {

        boolean isCreated = creancierService.createCreancier(
                addCrRequest.getCode(),
                addCrRequest.getNomCreancier(),
                addCrRequest.getLogoUrl()
        );

        if (isCreated) {
            boolean compteCreated = creancierService.createCompteToUser(addCrRequest.getCode(), addCrRequest.getNomCreancier());
            if (compteCreated) {
                return ResponseEntity.ok("Creancier created successfully.");
            } else {
                return ResponseEntity.status(500).body("Error: Account creation failed!");
            }
        } else {
            return ResponseEntity.status(500).body("Error: Creancier creation failed!");
        }
    }

    @GetMapping("/all")
    public List<Creancier> getAllCreanciers() {
        return creancierService.getAllCreanciers();
    }
}
