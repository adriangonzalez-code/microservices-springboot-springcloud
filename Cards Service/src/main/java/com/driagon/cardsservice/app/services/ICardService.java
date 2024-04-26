package com.driagon.cardsservice.app.services;

import com.driagon.cardsservice.app.dto.CardDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);
}