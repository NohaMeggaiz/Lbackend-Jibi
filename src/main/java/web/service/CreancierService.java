package web.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Client;
import web.models.Compte;
import web.models.Creancier;
import web.repositories.ClientRepository;
import web.repositories.CompteRepo;
import web.repositories.CreancierRepository;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreancierService {

    private final CreancierRepository creancierRepository;
    private final CompteRepo compteRepo;


    private static final String NUMBER = "0123456789";

    public String genererRib() {
        Long dateOfToday = System.currentTimeMillis();
        String dateOfTodayInMs = dateOfToday.toString();

        SecureRandom random = new SecureRandom();
        StringBuilder rib = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            rib.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }
        rib.append(dateOfTodayInMs);

        log.info("RIB of client generated: {}", rib);
        return rib.toString();
    }

    @Transactional
    public Boolean createCompteToUser(String code, String compteName) throws IOException {
        Creancier creancier = creancierRepository.findByCode(code);
        if (creancier == null) {
            log.error("Creancier with code {} not found", code);
            return false;
        }

        Compte compte = new Compte();
        String rib = genererRib();
        compte.setRib(rib);
        compte.setType_compte("Creancier");
        compte.setSolde(0.0);
        compte.setComptename(compteName);

        creancier.setCompte_creancier(compte);
        compteRepo.save(compte);
        return true;
    }

    @Transactional
    public Boolean createCreancier(String code, String nomCreancier, String logoUrl) throws IOException, MessagingException {
        Creancier creancier = new Creancier();
        creancier.setCode(code);
        creancier.setNom_creancier(nomCreancier);
        creancier.setLogo_Url(logoUrl);

        creancierRepository.save(creancier);

        // Assuming you might want to send an email after creation
        // mailService.sendCreancierCreationEmail(creancier);

        return true;
    }

    public List<Creancier> getAllCreanciers() {
        return creancierRepository.findAll();
    }
}
