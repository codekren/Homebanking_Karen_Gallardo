package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping ("/api")
public class TransactionController  {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping (value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity <Object> newTransaction (@RequestParam double amount,
                                                   @RequestParam String description,
                                                   @RequestParam String numberBegin,
                                                   @RequestParam String numberFinal,
                                                   Authentication authentication){

        Account accountBegin = accountRepository.findByNumber(numberBegin);
        Account accountFinal = accountRepository.findByNumber(numberFinal);

        if (amount == 0.0) {
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

        if (accountBegin == null) {

            return new ResponseEntity<>("numberBegin already doesnt exist", HttpStatus.FORBIDDEN);

        }

        Client client = clientRepository.findByEmail(authentication.getName());
        boolean verifyAccount = client.getAccount().stream().anyMatch(account -> account.getNumber().equals(numberBegin));
        if(!verifyAccount){
            return new ResponseEntity<>("Account origin doesnt exist", HttpStatus.FORBIDDEN);
        }


        if(accountFinal == null){
            return new ResponseEntity<>("This account destiny doesnt exist", HttpStatus.FORBIDDEN);

        }

        if(accountBegin.getBalance()<= amount){
            return new ResponseEntity<>("This account has not available balance", HttpStatus.FORBIDDEN);
        }


        Transaction transactionDebit = new Transaction(-amount,description +" "+ numberBegin, LocalDateTime.now(),
                TransactionType.DEBIT);
        Transaction transactionCredit = new Transaction(amount,description +" "+ numberFinal, LocalDateTime.now(),
                TransactionType.CREDIT);



        accountBegin.addTransaction(transactionDebit);
        accountFinal.addTransaction(transactionCredit);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        accountBegin.setBalance(accountBegin.getBalance()-amount);
        accountFinal.setBalance(accountFinal.getBalance()+amount);

        accountRepository.save(accountBegin);
        accountRepository.save(accountFinal);


        return new ResponseEntity<>("Transaction successfully created", HttpStatus.CREATED);
    }




}
