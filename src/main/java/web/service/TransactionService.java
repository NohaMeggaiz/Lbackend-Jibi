package web.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import web.models.Client;
import web.models.Compte;
import web.models.Creancier;
import web.models.Transaction;
import web.repositories.ClientRepository;
import web.repositories.CompteRepo;
import web.repositories.CreancierRepository;
import web.repositories.TransactionRepository;

import java.util.Date;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreancierRepository creancierRepository;

    @Autowired
    private CompteRepo compteRepository;

    @Transactional
    public Transaction makeTransaction(String numTel, String creancierCode, double montant) {
        Client client = clientRepository.findByNumTel(numTel);
        Creancier creancier = creancierRepository.findByCode(creancierCode);

        if (client == null || creancier == null) {
            throw new IllegalArgumentException("Client ou créancier non trouvé");
        }

        Compte clientCompte = client.getCompte();
        Compte creancierCompte = creancier.getCompte_creancier();

        if (clientCompte.getSolde() < montant) {
            throw new IllegalArgumentException("Solde insuffisant");
        }

        clientCompte.setSolde(clientCompte.getSolde() - montant);
        creancierCompte.setSolde(creancierCompte.getSolde() + montant);

        compteRepository.save(clientCompte);
        compteRepository.save(creancierCompte);

        Transaction transaction = new Transaction();
        transaction.setClient_source(client);
        transaction.setCreancier_dest(creancier);
        transaction.setMontant(montant);
        transaction.setDate_transaction(new Date());

        return transactionRepository.save(transaction);
    }
}