package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @RequestMapping("/loans")
    public List<LoanDTO> getLoans (){
     List<Loan> loanList = loanRepository.findAll();
        List<LoanDTO> loanDTOList =  loanList.stream().map(loan-> new LoanDTO(loan)).collect(Collectors.toList());
        return loanDTOList;
    }
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanCreated (@RequestBody LoanApplicationDTO loanApplicationDTO,Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getAccountDestination());
        double interest = 0.2;

        if( loanApplicationDTO.getAmount()<=0 ){

        return new ResponseEntity<>("The amount must be greater than 0",HttpStatus.FORBIDDEN);

        }

        if(loanApplicationDTO.getPayments()<=0){

            return new ResponseEntity<>("The payments must be greater than 0",HttpStatus.FORBIDDEN);

        }

        if(loanApplicationDTO.getAccountDestination().isBlank()){

            return new ResponseEntity<>("The account destination should not be empty",HttpStatus.FORBIDDEN);
        }

        if(loan == null){

            return new ResponseEntity<>("This loan doesn´t exist",HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getLoanId() > loan.getMaxAmount()){

            return new ResponseEntity<>("This loan exceeds the maximum allowed ",HttpStatus.FORBIDDEN);
        }

       if(!(loan.getPayments().stream().anyMatch(payment -> payment == (loanApplicationDTO.getPayments())))){

            return new ResponseEntity<>("The number of installments is not available",HttpStatus.FORBIDDEN);
        }

        if(account == null){

            return new ResponseEntity<>("The destination account does not exist",HttpStatus.FORBIDDEN);
        }

        if(!(client.getAccount().stream().anyMatch(acc -> acc.getNumber().equals(loanApplicationDTO.getAccountDestination())))){

            return new ResponseEntity<>("the target account does not belong to the user",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan((loanApplicationDTO.getAmount()* interest + (loanApplicationDTO.getAmount())), loanApplicationDTO.getPayments(), client, loan);

        client.getLoans().add(clientLoan);
        loan.getClients().add(clientLoan);
        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction (loanApplicationDTO.getAmount(), loan.getName() + " loan aprroved", LocalDateTime.now(),TransactionType.CREDIT);

        account.addTransaction(transaction);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(account);





        return new ResponseEntity<>("Loan created successful", HttpStatus.CREATED);
    }



}
