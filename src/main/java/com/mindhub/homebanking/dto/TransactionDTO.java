package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionDTO {
    private Long id;
    private double amount;
    private double currentBalance;
    private String description;
    private boolean active;
    private LocalDateTime date;
    private TransactionType type;


    public TransactionDTO(Transaction transaction) {
        id=transaction.getId();
        type=transaction.getType();
        amount= transaction.getAmount();
        description= transaction.getDescription();
        date=transaction.getDate();
        currentBalance= transaction.getcurrentBalance();
        active=transaction.isActive();
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

}
