package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Loan;
import org.springframework.web.bind.annotation.RequestMapping;


public class LoanApplicationDTO {
    private Long loanId;
    private double amount;
    private int payments;
    private double percentage;
    private String accountDestination;


    public LoanApplicationDTO() {

    }

    public Long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getAccountDestination() {
        return accountDestination;
    }
}
