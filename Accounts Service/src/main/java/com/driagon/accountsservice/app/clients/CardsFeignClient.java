package com.driagon.accountsservice.app.clients;

import com.driagon.accountsservice.app.clients.fallbacks.CardsFallback;
import com.driagon.accountsservice.app.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("eazybank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}