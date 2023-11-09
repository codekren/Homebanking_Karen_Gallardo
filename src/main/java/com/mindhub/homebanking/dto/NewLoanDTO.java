package com.mindhub.homebanking.dto;

import java.util.List;

public class NewLoanDTO {

    private String loanName;
    private double maxAmount;
    private double percentage;
    private List<Integer> payments;

    public NewLoanDTO() {
    }


    public String getLoanName() {
        return loanName;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
