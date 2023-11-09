package com.mindhub.homebanking.dto;


import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;

    private Long loanId;
    private String name;
    private double amount;
    private int payments;
    private int paymentPaid;
    private double amountPaid;
    private boolean active;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        amount =  clientLoan.getAmount();
        payments = clientLoan.getPayments();
        paymentPaid = clientLoan.getPaymentPaid();
        amountPaid = clientLoan.getAmountPaid();
        active = clientLoan.isActive();
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public int getPaymentPaid() {
        return paymentPaid;
    }

    public double getAmountPaid() {
        return amountPaid;
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
