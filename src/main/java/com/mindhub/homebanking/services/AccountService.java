package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAllAccounts();
    Account findAccountById(Long id);

    void saveAccount(Account account);

    Account findAccountByNumber (String number);

    boolean existsAccountByNumber(String number);

}
