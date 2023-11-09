package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
public class TransactionController  {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity <Object> newTransaction (@RequestParam double amount,
                                                   @RequestParam String description,
                                                   @RequestParam String numberBegin,
                                                   @RequestParam String numberFinal,
                                                   Authentication authentication){

        Account accountBegin = accountService.findAccountByNumber(numberBegin);
        Account accountFinal = accountService.findAccountByNumber(numberFinal);

        if (amount <= 0.0) {
            return new ResponseEntity<>("Amount is missing", HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty()) {
            return new ResponseEntity<>("Description is missing", HttpStatus.FORBIDDEN);
        }

        if (numberBegin.isEmpty()) {
            return new ResponseEntity<>("Number Begin is missing", HttpStatus.FORBIDDEN);
        }

        if (numberFinal.isEmpty()) {
            return new ResponseEntity<>("Number Final is missing", HttpStatus.FORBIDDEN);
        }

        if (numberBegin.equals(numberFinal)  ){
            return new ResponseEntity<>("Numbers accounts must be different", HttpStatus.FORBIDDEN);
        }

        if (accountBegin == null ) {

            return new ResponseEntity<>("numberBegin already doesnt exist", HttpStatus.FORBIDDEN);

        }



        Client client = clientService.findClientByEmail(authentication.getName());
        boolean verifyAccount = client.getAccount().stream().anyMatch(account -> account.getNumber().equals(numberBegin));
        if(!verifyAccount){
            return new ResponseEntity<>("Account origin doesnt exist", HttpStatus.FORBIDDEN);
        }


        if(accountFinal == null){
            return new ResponseEntity<>("This account destiny doesnt exist", HttpStatus.FORBIDDEN);

        }

        if(accountBegin.getBalance()< amount ){
            return new ResponseEntity<>("This account has not available balance", HttpStatus.FORBIDDEN);
        }

        double balanceAccountDebit = accountBegin.getBalance() - amount;
        Transaction transactionDebit = new Transaction((-amount),description +" "+ numberBegin, LocalDateTime.now(),
                TransactionType.DEBIT, balanceAccountDebit,true);
        double balanceAccountCredit = accountFinal.getBalance() + amount;
        Transaction transactionCredit = new Transaction(amount,description +" "+ numberFinal, LocalDateTime.now(),
                TransactionType.CREDIT,balanceAccountCredit,true);



        accountBegin.addTransaction(transactionDebit);
        accountFinal.addTransaction(transactionCredit);

        transactionService.saveTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);


        accountBegin.setBalance(accountBegin.getBalance()-amount);
        accountFinal.setBalance(accountFinal.getBalance()+amount);

        accountService.saveAccount(accountBegin);
        accountService.saveAccount(accountFinal);

        AccountDTO accountDTO = new AccountDTO(accountBegin);
        Long accountId = accountDTO.getId();
        return new ResponseEntity<>(accountId, HttpStatus.CREATED);


    }


}
