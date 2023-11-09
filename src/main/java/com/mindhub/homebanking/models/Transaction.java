package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Transaction {
    private double amount;
    private String description;
    private LocalDateTime date;
    private double currentBalance;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="trans")
    @GenericGenerator( name = "trans", strategy = "native")
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(double amount, String description, LocalDateTime date, TransactionType type,
                       double currentBalance,boolean active) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.currentBalance = currentBalance;
        this.active = active;
    }
    public double getAmount() {
        return amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public double getcurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
