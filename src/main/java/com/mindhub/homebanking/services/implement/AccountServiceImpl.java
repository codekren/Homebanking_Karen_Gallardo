package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {

        return accountRepository.findAll();
    }

    @Override
    public Account findAccountById(Long id) {

        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {

        accountRepository.save(account);

    }

    @Override
    public Account findAccountByNumber(String number) {

        return accountRepository.findByNumber(number);
    }

    @Override
    public boolean existsAccountByNumber(String number) {

        return accountRepository.existsByNumber(number);

    }


}
