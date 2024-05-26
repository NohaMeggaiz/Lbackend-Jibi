package web.request;

import lombok.Data;

@Data
public class TransactionRequest {

    private String numTel;
    private String creancierCode;
    private double montant;

}