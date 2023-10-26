package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> cardsClient (Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());

        List<CardDTO> cardDTOList = client.getCards().stream().map(card -> new CardDTO(card) ).
                collect(Collectors.toList());
        return cardDTOList;
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard( @RequestParam String cardColor, @RequestParam String cardType,
            Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null){
            throw new UsernameNotFoundException("User not register " + authentication.getName());
        }

        if(cardType.isEmpty()){
            return new ResponseEntity<>("Choose a card type.", HttpStatus.FORBIDDEN);
        }
        if(cardColor.isEmpty()){
            return new ResponseEntity<>("Choose a card color.", HttpStatus.FORBIDDEN);
        }

        int countCards = (int)client.getCards().stream().filter(card -> card.getType()
                .equals(CardType.valueOf(cardType))).count();

        if(countCards == 3){
            return new ResponseEntity<>("too many cards", HttpStatus.FORBIDDEN);

        }

        Card card = new Card(client.getName() +" " + client.getLastName(),cardNumber(),cvvNumber(), LocalDate.now(),
                LocalDate.now().plusYears(5), CardType.valueOf(cardType),CardColor.valueOf(cardColor) );

        client.addCards(card);
        cardRepository.save(card);
        clientRepository.save(client);

        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }

    private String cvvNumber() {
        int randomNumber = (int) (Math.random() * 1000);
        return String.format("%03d", randomNumber);
    }

    private String card4Number() {
        int randomNumber = (int) (Math.random() * 10000);
        return String.format("%04d", randomNumber);
    }

    private String cardNumber(){
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
