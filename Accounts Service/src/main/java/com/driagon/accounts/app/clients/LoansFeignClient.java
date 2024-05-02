package com.driagon.accounts.app.clients;

import com.driagon.accounts.app.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);
}