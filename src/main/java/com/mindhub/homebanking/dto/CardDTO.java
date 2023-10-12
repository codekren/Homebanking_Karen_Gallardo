package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardHolder;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private CardType type;
    private CardColor color;

    public CardDTO(Card card) {
        id= card.getId();
        cardHolder=card.getCardHolder();
        number=card.getNumber();
        cvv=card.getCvv();
        fromDate=card.getFromDate();
        thruDate=card.getThruDate();
        type=card.getType();
        color=card.getColor();

    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }
}
