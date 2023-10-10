package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.format.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
	return args -> {

		Client client1= new Client ("Melba","Morel","melba@mindhub.com");
		Client client= new Client("Jona","Gui","jona@mindhub.com");
		clientRepository.save(client1);
		clientRepository.save(client);

		Account account= new Account("VIN001",5000, LocalDate.now()) ;
		Account account1= new Account("VIN002",7500,LocalDate.now().plusDays(1));
		Account account2= new Account("BIN003", 8000,LocalDate.now());
		Account account3= new Account("BIN004",4000,LocalDate.now().plusDays(2));
		client1.addAccount(account);
		client1.addAccount(account1);
		client.addAccount(account2);
		client.addAccount(account3);
		accountRepository.save(account);
		accountRepository.save(account1);
		accountRepository.save(account2);
		accountRepository.save(account3);

		Transaction transaction= new Transaction(2000, "buy market", LocalDateTime.now(), TransactionType.DEBITO);
		Transaction transaction1= new Transaction(-3000,"payback internet",LocalDateTime.now(),TransactionType.CREDITO);
		Transaction transaction2= new Transaction(5000,"breakfast fee",LocalDateTime.now(),TransactionType.DEBITO);
		Transaction transaction3= new Transaction(-2500,"buy pizza",LocalDateTime.now(),TransactionType.CREDITO);
		account.addTransaction(transaction);
		account.addTransaction(transaction1);
		account2.addTransaction(transaction2);
		account2.addTransaction(transaction3);
		transactionRepository.save(transaction);
		transactionRepository.save(transaction1);
		transactionRepository.save(transaction2);
		transactionRepository.save(transaction3);

	};

}
}