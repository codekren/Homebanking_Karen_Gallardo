package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.CardUtils.cardNumber;
import static com.mindhub.homebanking.utils.CardUtils.cvvNumber;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @GetMapping("/clients/current/cards")
    public List<CardDTO> cardsClient (Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());

        List<CardDTO> cardDTOList = client.getCards().stream().map(card -> new CardDTO(card) ).
                collect(Collectors.toList());
        return cardDTOList;
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard( @RequestParam String cardColor, @RequestParam String cardType,
            Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());

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
                LocalDate.now().plusYears(5), CardType.valueOf(cardType),CardColor.valueOf(cardColor),true );

        client.addCards(card);
        cardService.saveCard(card);
        clientService.saveClient(client);

        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard( @RequestParam Long id,
                                              Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Card card = cardService.findCardById(id);


        if(card == null){
            return new ResponseEntity<>("This card doesn´t exist",HttpStatus.FORBIDDEN);
        }

        if(!card.isActive()){
            return new ResponseEntity<>("This card it´s not active",HttpStatus.BAD_REQUEST);

        }

        if(!(card.getClient().equals(client))){
            return new ResponseEntity<>("It´s not possible this card not belong client",
                    HttpStatus.BAD_REQUEST);

        }
        card.setActive(false);
        cardService.saveCard(card);

        return new ResponseEntity<>("This card has been removed",
                HttpStatus.OK);
    }
    }



