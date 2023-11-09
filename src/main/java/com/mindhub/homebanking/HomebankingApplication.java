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
	//@Autowired
	//private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return args -> {

			/*Client client1 = new Client("Melba","Morel", "melba@mindhub.com",
					passwordEncoder.encode("Melba"));
			Client client2 = new Client("Yoshi","Sweet","Yoshi@mindhub.com",
					passwordEncoder.encode("Yoshi"));
			Client admin = new Client("Admin","Admin","admin@admin.com",
					passwordEncoder.encode("admin123"));
			Client client3 = new Client("Mario","Bros", "mb@mindhub.com",
					passwordEncoder.encode("Mario"));
			Client client4 = new Client("Luigi","Bros", "lb@mindhub.com",
					passwordEncoder.encode("Luigi"));
			Client client5 = new Client("Peach","Princess", "peach@mindhub.com",
					passwordEncoder.encode("Peach"));
			Client client6 = new Client("Bowser","Evil", "bowser@mindhub.com",
					passwordEncoder.encode("Bowser"));

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(admin);
			clientRepository.save(client3);
			clientRepository.save(client4);
			clientRepository.save(client5);
			clientRepository.save(client6);

			Account account = new Account("VIN001", 5000, LocalDate.now(),0,true);
			Account account1 = new Account("VIN002", 7500, LocalDate.now().plusDays(1),1,true);
			Account account2 = new Account("BIN003", 8000, LocalDate.now(),0,true);
			Account account3 = new Account("BIN004", 4000, LocalDate.now().plusDays(2),0,true);
			Account account4 = new Account("VIN005", 10000, LocalDate.now(),0,true);
			Account account5 = new Account("VIN006", 8000, LocalDate.now().plusDays(2),0,true);
			Account account6 = new Account("VIN007", 9000, LocalDate.now().plusDays(1),0,true);
			Account account7 = new Account("VIN008", 11000, LocalDate.now().plusDays(2),0,true);
			client1.addAccount(account);
			client1.addAccount(account1);
			client2.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);
			client4.addAccount(account5);
			client5.addAccount(account6);
			client5.addAccount(account7);
			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);
			accountRepository.save(account5);
			accountRepository.save(account6);
			accountRepository.save(account7);



			Transaction transaction = new Transaction(-2000, "buy market", LocalDateTime.now(), TransactionType.DEBIT,0,true);
			Transaction transaction1 = new Transaction(3000, "payback internet", LocalDateTime.now(), TransactionType.CREDIT,0,true);
			Transaction transaction2 = new Transaction(-5000, "breakfast fee", LocalDateTime.now(), TransactionType.DEBIT,0,true);
			Transaction transaction3 = new Transaction(2500, "buy pizza", LocalDateTime.now(), TransactionType.CREDIT,0,true);
			Transaction transaction4 = new Transaction(2500, "buy pizza", LocalDateTime.now(), TransactionType.CREDIT,0,true);
			Transaction transaction5 = new Transaction(2500, "buy pizza", LocalDateTime.now(), TransactionType.CREDIT,0,true);
			Transaction transaction6 = new Transaction(2500, "buy pizza", LocalDateTime.now(), TransactionType.CREDIT,0,true);
			account.addTransaction(transaction);
			account.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account3.addTransaction(transaction4);
			account4.addTransaction(transaction5);
			account4.addTransaction(transaction6);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);

		Loan loan1=new Loan("Mortgage",500000, List.of(12,24,36,48,60));
		Loan loan2=new Loan("Personal",100000, List.of(6,12,24));
		Loan loan3=new Loan("Automotive",300000, List.of(6,12,24,36));
		loanRepository.save(loan1);
		loanRepository.save(loan2);
		loanRepository.save(loan3);



			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(50000, 12, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(10000, 24, client3, loan1);
			ClientLoan clientLoan5 = new ClientLoan(25000, 6, client5, loan2);
			ClientLoan clientLoan6 = new ClientLoan(55000, 6, client6, loan2);
			ClientLoan clientLoan7 = new ClientLoan(60000, 24, client6, loan1);

			client1.getLoans().add(clientLoan1);
			client1.getLoans().add(clientLoan2);
			client2.getLoans().add(clientLoan3);
			client3.getLoans().add(clientLoan4);
			client5.getLoans().add(clientLoan5);
			client6.getLoans().add(clientLoan6);
			client6.getLoans().add(clientLoan7);
			loan1.getClients().add(clientLoan1);
			loan2.getClients().add(clientLoan2);
			loan2.getClients().add(clientLoan3);
			loan1.getClients().add(clientLoan4);
			loan2.getClients().add(clientLoan5);
			loan2.getClients().add(clientLoan6);
			loan1.getClients().add(clientLoan7);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			clientLoanRepository.save(clientLoan5);
			clientLoanRepository.save(clientLoan6);
			clientLoanRepository.save(clientLoan7);



			Card card1 = new Card(client1.getName() + " " + client1.getLastName(), "0023 5789 6789 0980", "023", LocalDate.now(),
					LocalDate.now().plusYears(5),
					CardType.DEBIT, CardColor.GOLD,true);
			client1.addCards(card1);
			cardRepository.save(card1);
			Card card2 = new Card(client1.getName() + " " + client1.getLastName(), "0987 8768 6545 8765", "032", LocalDate.now(),
					LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.TITANIUM,true);
			client1.addCards(card2);
			cardRepository.save(card2);
			Card card3 = new Card(client2.getName() + " " + client2.getLastName(), "1234 8768 6537 9876", "345", LocalDate.now(),
					LocalDate.now().plusYears(5), CardType.CREDIT, CardColor.SILVER,true);
			client2.addCards(card3);
			cardRepository.save(card3);

*/

		};
	}
}
