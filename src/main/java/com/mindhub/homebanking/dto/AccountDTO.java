package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String  number;
    private double balance;
    private LocalDate creationDate;

    private boolean active;
    private AccountType type;

    private List<TransactionDTO> transactions;


        public AccountDTO(Account account){
            id=account.getId();
            number= account.getNumber();
            balance=account.getBalance();
            creationDate=account.getCreationDate();
            transactions=account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
            active=account.isActive();
            type=account.getType();
        }


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public AccountType getType() {
        return type;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
}


