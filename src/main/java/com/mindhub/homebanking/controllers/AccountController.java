package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAllAccounts(){
        List<Account>accounts=accountRepository.findAll();
        Stream<Account> accountStream=accounts.stream();
        Stream<AccountDTO>accountDTOStream=accountStream.map(account->new AccountDTO(account));

        List<AccountDTO>accountDTOS= accountDTOStream.collect(Collectors.toList());

        return accountDTOS;
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        Optional<Account> account=accountRepository.findById(id);

        AccountDTO accountDTO= account.map(acct-> new AccountDTO(acct)).orElse(null);
        return accountDTO;

    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createAccount(Authentication authentication ) {


        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null){
            throw new UsernameNotFoundException("User not register " + authentication.getName());
        }
        if (client.getAccount().size()==3){
            return new ResponseEntity<>("Not permission", HttpStatus.FORBIDDEN);

        }
        else {
            Account account= new Account(getRandomVINNumber(),0.0, LocalDate.now());

            client.addAccount(account);
            accountRepository.save(account);
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public String getRandomVINNumber() {
            Random random = new Random();
            int randomNum;
            String vinNumber;

        do {
            randomNum = random.nextInt(90000000) + 10000000;
            vinNumber = "VIN-" + String.format("%08d", randomNum);
        } while (accountRepository.findByNumber(vinNumber) !=null);
        return vinNumber;
    }


}
