package com.driagon.accounts.app.clients;

import com.driagon.accounts.app.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);
}