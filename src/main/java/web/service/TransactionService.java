package web.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import web.models.Client;
import web.models.Compte;
import web.models.Creancier;
import web.models.Transaction;
import web.repositories.ClientRepository;
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

        // No need to save the account changes explicitly, as they will be cascaded.

        Transaction transaction = new Transaction();
        transaction.getClients().add(client);
        transaction.getCreanciers().add(creancier);
        transaction.setMontant(montant);
        transaction.setDate_transaction(new Date());

        return transactionRepository.save(transaction);
    }
}
