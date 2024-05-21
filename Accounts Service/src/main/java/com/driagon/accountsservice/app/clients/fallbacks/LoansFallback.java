package com.driagon.accountsservice.app.clients.fallbacks;

import com.driagon.accountsservice.app.clients.LoansFeignClient;
import com.driagon.accountsservice.app.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}