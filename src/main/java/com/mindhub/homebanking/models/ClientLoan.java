package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="number")
    @GenericGenerator(name="number", strategy = "native")
    private Long id;
    private double amount;
    private int payments;
    private double percentage;
    private int paymentPaid;
    private double amountPaid;
    private boolean active;
    @ManyToOne(fetch=FetchType.EAGER)
    private Client clientLoan;

    @ManyToOne(fetch=FetchType.EAGER)
    private Loan loan;


    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments, double percentage,
                      int paymentsPaid, double amountPaid, boolean active, Client clientLoan, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.percentage = percentage;
        this.paymentPaid = paymentPaid;
        this.amountPaid = amountPaid;
        this.active = active;
        this.clientLoan = clientLoan;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPaymentPaid() {
        return paymentPaid;
    }

    public void setPaymentPaid(int paymentPaid) {
        this.paymentPaid  = paymentPaid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(Client clientLoan) {
        this.clientLoan = clientLoan;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }


}
