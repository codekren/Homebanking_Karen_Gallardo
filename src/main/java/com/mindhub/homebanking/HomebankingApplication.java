package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return args -> {

			Client client1 = new Client("Melba","Morel", "melba@mindhub.com",
					passwordEncoder.encode("Melba"));
			Client client2 = new Client("Jona","Giraldo","jona@mindhub.com",
					passwordEncoder.encode("Jona"));
			Client admin = new Client("Admin","Admin","admin@admin.com",
					passwordEncoder.encode("admin123"));

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(admin);

			Account account = new Account("VIN001", 5000, LocalDate.now());
			Account account1 = new Account("VIN002", 7500, LocalDate.now().plusDays(1));
			Account account2 = new Account("BIN003", 8000, LocalDate.now());
			Account account3 = new Account("BIN004", 4000, LocalDate.now().plusDays(2));
			client1.addAccount(account);
			client1.addAccount(account1);
			client2.addAccount(account2);
			client2.addAccount(account3);
			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction transaction = new Transaction(-2000, "buy market", LocalDateTime.now(), TransactionType.DEBIT);
			Transaction transaction1 = new Transaction(3000, "payback internet", LocalDateTime.now(), TransactionType.CREDIT);
			Transaction transaction2 = new Transaction(-5000, "breakfast fee", LocalDateTime.now(), TransactionType.DEBIT);
			Transaction transaction3 = new Transaction(2500, "buy pizza", LocalDateTime.now(), TransactionType.CREDIT);
			account.addTransaction(transaction);
			account.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

		Loan loan1=new Loan("Mortgage",500000, List.of(12,24,36,48,60));
		Loan loan2=new Loan("Personal",100000, List.of(6,12,24));
		Loan loan3=new Loan("Car",300000, List.of(6,12,24,36));
		loanRepository.save(loan1);
		loanRepository.save(loan2);
		loanRepository.save(loan3);



			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);

			client1.getLoans().add(clientLoan1);
			client1.getLoans().add(clientLoan2);
			loan1.getClients().add(clientLoan1);
			loan2.getClients().add(clientLoan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);



			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan1);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36, client2, loan3);
			client2.getLoans().add(clientLoan3);
			client2.getLoans().add(clientLoan4);
			loan1.getClients().add(clientLoan3);
			loan3.getClients().add(clientLoan4);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1.getName() + " " + client1.getLastName(), "0023 5789 6789 0980", "023", LocalDate.now(),
					LocalDate.now().plusYears(5),
					CardType.DEBIT, CardColor.GOLD);
			client1.addCards(card1);
			cardRepository.save(card1);
			Card card2 = new Card(client1.getName() + " " + client1.getLastName(), "0987 8768 6545 8765", "032", LocalDate.now(),
					LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.TITANIUM);
			client1.addCards(card2);
			cardRepository.save(card2);
			Card card3 = new Card(client2.getName() + " " + client2.getLastName(), "1234 8768 6537 9876", "345", LocalDate.now(),
					LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.SILVER);
			client2.addCards(card3);
			cardRepository.save(card3);



		};
	}
}
