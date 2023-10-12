package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
     private String name, lastName, email;
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO,generator="datoId")
     @GenericGenerator( name = "datoId", strategy = "native")
     private Long  id;
     @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts= new HashSet<>();// genera un espacio de referencia en memoria de la app
    @OneToMany(mappedBy="clientLoan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans= new HashSet<>();
    public Client() {

    }

    public Client(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Set<Account> getAccount() {
        return accounts;
    }
    public void addAccount(Account account){
        account.setClient(this);// el set asigna el valor de todo el obj mentor
        this.accounts.add(account);// similar al push JS
    }


    public List<ClientLoan> getLoans() {
        return clientLoans.stream().collect(Collectors.toList());
    }
}
