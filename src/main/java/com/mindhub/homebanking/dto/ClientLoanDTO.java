package com.mindhub.homebanking.dto;


import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;

    private Long loanId;
    private String name;
    private double amount;
    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id=clientLoan.getId();
        loanId=clientLoan.getLoan().getId();
        name=clientLoan.getLoan().getName();
        amount=clientLoan.getAmount();
        payments= clientLoan.getPayments();

    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
