package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="number")
    @GenericGenerator(name="number", strategy = "native")
    private Long id;
    private String  number;
    private double balance;
    private LocalDate creationDate;
    private double currentBalance;
    private boolean active ;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")// para indicar el nombre de la columna
    private Client client;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions= new HashSet<>();

    public Account() {
    }
    public Account( String number, double balance, LocalDate creationDate, double currentBalance, boolean active) {

        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.currentBalance = currentBalance;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
