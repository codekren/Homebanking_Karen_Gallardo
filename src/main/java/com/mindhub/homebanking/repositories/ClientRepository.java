package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository <Client, Long > {
   Client findByEmail (String email); // indica que el método se usa para buscar un objeto de tipo Client
    // basado en el param email, o sea se busca un cliente cuyo correo electrónico coincida con el valor proporcionado.


    }



