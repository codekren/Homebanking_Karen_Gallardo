package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils (){

    }
    public static String cvvNumber() {
        int randomNumber = (int) (Math.random() * 1000);
        return String.format("%03d", randomNumber);
    }

    public static String card4Number() {
        int randomNumber = (int) (Math.random() * 10000);
        return String.format("%04d", randomNumber);
    }

    public static String cardNumber(){
        String cardNumber = "";
        for (int i = 0; i <= 3 ; i++) {
            cardNumber += card4Number();
            if (i < 3){
                cardNumber += "-";
            }

        }
        return cardNumber;
    }



}
