package web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Client;
import web.models.Compte;
import web.repositories.ClientRepository;
import web.repositories.CompteRepo;

import java.io.IOException;
import java.security.SecureRandom;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientService {


    @Autowired
    private final ClientRepository userRepo;
    @Autowired
    private final CompteRepo compteRepo;
    private final PasswordEncoder passwordEncoder;
    final String number = "0123456789";
    @Autowired
    EmailService mailService;

    public String genererRib() {
        Long dateoftoday =  System.currentTimeMillis();
        String dateoftodayinms = dateoftoday.toString();

        SecureRandom random = new SecureRandom();
        String rib="";


        for(int i=0;i<11;i++) {
            rib+=number.charAt(random.nextInt(number.length()));
        }
        rib+=dateoftodayinms;

        System.out.println("RIB of client genarated: "+rib);
        return rib;
    }

    public Boolean createCompteToUser(String numTel, String typeCompte) throws IOException {
        Client user = userRepo.findByNumTel(numTel);
        Compte compte = new Compte();
        String rib = genererRib();
        compte.setRib(rib);

        if (typeCompte == null) {
            System.out.println("Type compte has arrived empty null");
        }

        // Adjusting account details based on the type of account
        switch (typeCompte) {
            case "Hissab 1":
                compte.setType_compte("Hissab 1");
                compte.setSolde(200.0); // Adjusted solde for Hissab 1
                compte.setComptename("Plafond : 200 DH");
                break;
            case "Hissab 2":
                compte.setType_compte("Hissab 2");
                compte.setSolde(5000.0); // Adjusted solde for Hissab 2
                compte.setComptename("Plafond : 5000 DH");
                break;
            case "Hissab 3":
                compte.setType_compte("Hissab 3");
                compte.setSolde(20000.0); // Adjusted solde for Hissab 3
                compte.setComptename("Plafond : 20000 DH");
                break;
            default:
                System.out.println("Invalid type compte");
                break;
        }

        user.setCompte(compte);
        compteRepo.save(compte);
        return true;
    }



    @Transactional
    public void deleteClientByNumTel(String numTel) {
        if (userRepo.findByNumTel(numTel) !=null) {
            userRepo.deleteByNumTel(numTel);
        } else {
            throw new RuntimeException("Client with numTel " + numTel + " not found");
        }
    }

    public double getCompteSolde(long idClient) {
        Client client = userRepo.findById(idClient);
        if (client == null) {
            throw new RuntimeException("Client with ID " + idClient + " not found");
        }
        Compte compte = client.getCompte();
        if (compte == null) {
            throw new RuntimeException("Account for client with ID " + idClient + " not found");
        }
        return compte.getSolde();
    }
    public Client getClientById(long idClient) {
        Client client = userRepo.findById(idClient);
        if (client == null) {
            throw new RuntimeException("Client with ID " + idClient + " not found");
        }
        return client;
    }

    public void changePassword(String numTel, String newPassword) {
        Client client = userRepo.findByNumTel(numTel);
        String encodedPassword = passwordEncoder.encode(newPassword);

        client.setPassword(encodedPassword);
        userRepo.save(client);
    }
}

