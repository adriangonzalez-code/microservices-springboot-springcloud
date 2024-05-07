package com.driagon.accountsservice.app.clients.fallbacks;

import com.driagon.accountsservice.app.clients.CardsFeignClient;
import com.driagon.accountsservice.app.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}