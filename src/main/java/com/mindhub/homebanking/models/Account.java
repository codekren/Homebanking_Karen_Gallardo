package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="number")
    @GenericGenerator(name="number", strategy = "native")
    private Long id;
    private String  number;
    private double balance;
    private LocalDate creationDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")// para indicar el nombre de la columna
    private Client client;

    public Account() {
    }

    public Account( String number, double balance, LocalDate creationDate) {

        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
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
}
