package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;
    private String name, lastName, email;
    private List<AccountDTO> accounts;
    private List<ClientLoanDTO> loans;
    private List<CardDTO> cards;


    public ClientDTO(Client client){
        id= client.getId();
        name= client.getName();
        lastName=client.getLastName();
        email=client.getEmail();
        accounts=client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        loans=client.getLoans().stream().map(loan->new ClientLoanDTO(loan)).collect(Collectors.toList());
        cards=client.getCards().stream().map(card-> new CardDTO(card)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }
}
