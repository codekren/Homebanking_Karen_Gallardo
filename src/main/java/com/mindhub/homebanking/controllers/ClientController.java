package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api") //ruta base del controlador
public class ClientController {
    @Autowired // inyección de dependencias de springboot(contexto)
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        Stream<Client> clientStream = clients.stream();
        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());

        return clientDTOS;
    }

    @RequestMapping("/clients/{id}") //Servlet (anotación más método) asociamos una petición específica a una ruta
    public ClientDTO getClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);

        ClientDTO clientDTO = client.map(cli -> new ClientDTO(cli)).orElse(null);
        return clientDTO;

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountController accountController;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register( // Este método maneja solicitudes Post a URL/clients
                                            //para registrar nuevos usuarios

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (name.isEmpty() || name.isBlank()) {
            return new ResponseEntity<>("Name is missing", HttpStatus.FORBIDDEN);
        }

        if (lastName.isEmpty()|| name.isBlank()) {
            return new ResponseEntity<>("Lastname is missing", HttpStatus.FORBIDDEN);
        }

        if (email.isEmpty()|| name.isBlank()) {
            return new ResponseEntity<>("Email is missing", HttpStatus.FORBIDDEN);
        }

        if (password.isEmpty()|| name.isBlank()) {
            return new ResponseEntity<>("Password is missing", HttpStatus.FORBIDDEN);
        }


        if (clientRepository.findByEmail(email) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        Account account = new Account(accountController.getRandomVINNumber(), 0.0, LocalDate.now());
        client.addAccount(account);
        accountRepository.save(account);


        return new ResponseEntity<>("Client successfully created", HttpStatus.CREATED);
    }
    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        ClientDTO clientDTO = Optional.ofNullable(client)
                .map(cli -> new ClientDTO(cli))
                .orElse(null);
        return clientDTO;
    }
}
