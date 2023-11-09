package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.services.implement.AccountServiceImpl;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.homebanking.utils.AccountUtils.getRandomVINNumber;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        Stream<Account> accountStream = accounts.stream();
        Stream<AccountDTO> accountDTOStream = accountStream.map(account -> new AccountDTO(account));

        List<AccountDTO> accountDTOS = accountDTOStream.collect(Collectors.toList());

        return accountDTOS;
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id,Authentication authentication) {
        Account account = accountService.findAccountById(id);
        Client client = clientService.findClientByEmail(authentication.getName());

        if(!client.getAccount().stream().anyMatch(acc -> acc.getNumber().equals(account.getNumber()))){

            return new ResponseEntity<>("Account doesnt belong to client",HttpStatus.FORBIDDEN);

        }
        if (account == null) {

            return new ResponseEntity<>("Account doesn´t exist", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);

    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
        List<AccountDTO> accountsClient = client.getAccount().stream().map(account ->
                new AccountDTO(account)).collect(Collectors.toList());
        return accountsClient;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType type ,Authentication authentication) {


        Client client = clientService.findClientByEmail(authentication.getName());

        if (client.getAccount().stream().filter(acc->acc.isActive()).collect(Collectors.toList()).size() == 3) {
            return new ResponseEntity<>("Not permission", HttpStatus.FORBIDDEN);

        } else {

            Account account = new Account(getRandomVINNumber(accountService), 0.0,
                    LocalDate.now(),0,true, type);

            client.addAccount(account);
            accountService.saveAccount(account);
        }


        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

        @PostMapping("/clients/current/accounts/delete")
        public ResponseEntity<Object> deleteAccount(@RequestParam Long id, Authentication authentication ){

            Client client = clientService.findClientByEmail(authentication.getName());
            Account account = accountService.findAccountById(id);


            if(account == null){
                return new ResponseEntity<>("This account doesn´t exist",HttpStatus.FORBIDDEN);
            }
            if(client.getAccount().stream().filter(acc -> acc.isActive()).collect(Collectors.toList()).size() == 1){
                return new ResponseEntity<>("Minimum one active account ",HttpStatus.FORBIDDEN);
            }

            if( account.getBalance() !=0){
                return new ResponseEntity<>("Amount must be 0",HttpStatus.FORBIDDEN);

            }

            if(!account.isActive()){
                return new ResponseEntity<>("This account it´s not active",HttpStatus.BAD_REQUEST);

            }

            if(!(account.getClient().equals(client))){
                return new ResponseEntity<>("It´s not possible this account no belong client",
                        HttpStatus.BAD_REQUEST);

            }

            account.getTransactions().stream().forEach(trns -> {
                trns.setActive(false);
                transactionService.saveTransaction(trns);
            });
            account.setActive(false);
            accountService.saveAccount(account);


            return new ResponseEntity<>("This account has been removed",
                    HttpStatus.OK);
        }



}





