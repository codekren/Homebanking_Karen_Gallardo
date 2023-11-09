package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public final class AccountUtils {

    private AccountUtils (){

    }

    public static String getRandomVINNumber(AccountService accountService){

        Random random = new Random();
        int randomNum;
        String vinNumber;


        do {
            randomNum = random.nextInt(90000000) + 10000000;
            vinNumber = "VIN-" + String.format("%08d", randomNum);
        } while (accountService.existsAccountByNumber(vinNumber));
        return vinNumber;

    }
}
