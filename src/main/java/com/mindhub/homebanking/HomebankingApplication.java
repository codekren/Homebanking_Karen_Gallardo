package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.format.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
	return args -> {

		Client client1= new Client ("Melba","Morel","melba@mindhub.com");
		Client client2= new Client("Jona","Gui","jona@mindhub.com");
		clientRepository.save(client1);
		clientRepository.save(client2);

		Account account= new Account("VIN001",5000, LocalDate.now()) ;
		Account account1= new Account("VIN002",7500,LocalDate.now().plusDays(1));
		Account account2= new Account("BIN003", 8000,LocalDate.now());
		Account account3= new Account("BIN004",4000,LocalDate.now().plusDays(2));
		client1.addAccount(account);
		client1.addAccount(account1);
		client2.addAccount(account2);
		client2.addAccount(account3);
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

		/*Loan loan1=new Loan("Hipotecario",500000, List.of(12,24,36,48,60));
		Loan loan2=new Loan("Personal",100000, List.of(6,12,24));
		Loan loan3=new Loan("Automotriz",300000, List.of(6,12,24,36));
		loanRepository.save(loan1);
		loanRepository.save(loan2);
		loanRepository.save(loan3);*/


		Loan loan4=new Loan("Hipotecario",400000, List.of(60));
		Loan loan5=new Loan("Personal", 50000,List.of(12));
		ClientLoan clientLoan1= new ClientLoan (400000,60,client1,loan4);
		ClientLoan clientLoan2= new ClientLoan (50000,12,client1,loan5);
		loanRepository.save(loan4);
		loanRepository.save(loan5);
		client1.getLoans().add(clientLoan1);
		client1.getLoans().add(clientLoan2);
		loan4.getClients().add(clientLoan1);
		loan5.getClients().add(clientLoan2);

		clientLoanRepository.save(clientLoan1);
		clientLoanRepository.save(clientLoan2);

		Loan loan6=new Loan("Personal",100000, List.of(24));
		Loan loan7=new Loan("Automotriz",200000, List.of(36));
		loanRepository.save(loan6);
		loanRepository.save(loan7);
		ClientLoan clientLoan3= new ClientLoan (100000,24,client2,loan6);
		ClientLoan clientLoan4= new ClientLoan (200000,36,client2,loan7);
		client2.getLoans().add(clientLoan3);
		client2.getLoans().add(clientLoan4);
		loan6.getClients().add(clientLoan3);
		loan7.getClients().add(clientLoan4);
		clientLoanRepository.save(clientLoan3);
		clientLoanRepository.save(clientLoan4);
	};

}
}