package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.models.Transaction;
import web.service.TransactionService;
import web.request.TransactionRequest;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/make")
    public Transaction makeTransaction(@RequestBody TransactionRequest request) {
        return transactionService.makeTransaction(request.getNumTel(), request.getCreancierCode(), request.getMontant());
    }
}