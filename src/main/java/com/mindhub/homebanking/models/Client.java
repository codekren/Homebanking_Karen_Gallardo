package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // persistence de dates JPA antes de this es  POJO, y crea una tabla en la BBDD con los datos de esta clase
public class Client {
     private String name, lastName, email; // colum de la tabla de BBDD
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO,generator="datoId")
     @GenericGenerator( name = "datoId", strategy = "native")
     private Long  id;

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
}
