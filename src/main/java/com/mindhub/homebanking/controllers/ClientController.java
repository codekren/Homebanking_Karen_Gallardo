package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
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

import static com.mindhub.homebanking.utils.AccountUtils.getRandomVINNumber;

@RestController
@RequestMapping("/api") //ruta base del controlador
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountController accountController;


    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientService.findAllClients();
        Stream<Client> clientStream = clients.stream();
        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());

        return clientDTOS;
    }

    @GetMapping("/clients/{id}") //Servlet (anotación más método) asociamos una petición específica a una ruta
    public ResponseEntity<Object> getClient(@PathVariable Long id) {
        Client client = clientService.findClientById(id);

        if(client == null){

            return new ResponseEntity<>("Dont found client", HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(new ClientDTO(client),HttpStatus.OK );


    }
    @PostMapping("/clients")
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


        if (clientService.findClientByEmail(email) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        Account account =
                new Account(getRandomVINNumber(accountService), 0.0, LocalDate.now(),0,true);
        client.addAccount(account);
        accountService.saveAccount(account);


        return new ResponseEntity<>("Client successfully created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        ClientDTO clientDTO = Optional.ofNullable(client)
                .map(cli -> new ClientDTO(cli))
                .orElse(null);
        return clientDTO;
    }
}
