package com.driagon.cardsservice.app.services.impl;

import com.driagon.cardsservice.app.constants.CardConstants;
import com.driagon.cardsservice.app.dto.CardDto;
import com.driagon.cardsservice.app.entities.Card;
import com.driagon.cardsservice.app.exceptions.CardAlreadyExistsException;
import com.driagon.cardsservice.app.exceptions.ResourceNotFoundException;
import com.driagon.cardsservice.app.mapper.CardMapper;
import com.driagon.cardsservice.app.repositories.ICardRepository;
import com.driagon.cardsservice.app.services.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.driagon.cardsservice.app.constants.CardConstants.NEW_CARD_LIMIT;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private ICardRepository repository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = this.repository.findByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber");
        }

        this.repository.save(this.createNewCard(mobileNumber));
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card cards = this.repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardMapper.mapToCardDto(cards, new CardDto());
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardDto cardsDto) {
        Card cards = this.repository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardMapper.mapToCard(cardsDto, cards);
        this.repository.save(cards);
        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card cards = this.repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        this.repository.deleteById(cards.getCardId());
        return true;
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(NEW_CARD_LIMIT);

        return newCard;
    }
}