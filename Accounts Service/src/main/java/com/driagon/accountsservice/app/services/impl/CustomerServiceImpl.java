package com.driagon.accountsservice.app.services.impl;

import com.driagon.accountsservice.app.clients.CardsFeignClient;
import com.driagon.accountsservice.app.clients.LoansFeignClient;
import com.driagon.accountsservice.app.dto.AccountsDto;
import com.driagon.accountsservice.app.dto.CardsDto;
import com.driagon.accountsservice.app.dto.CustomerDetailsDto;
import com.driagon.accountsservice.app.dto.LoansDto;
import com.driagon.accountsservice.app.entities.Account;
import com.driagon.accountsservice.app.entities.Customer;
import com.driagon.accountsservice.app.exceptions.ResourceNotFoundException;
import com.driagon.accountsservice.app.mapper.AccountMapper;
import com.driagon.accountsservice.app.mapper.CustomerMapper;
import com.driagon.accountsservice.app.repositories.AccountRepository;
import com.driagon.accountsservice.app.repositories.CustomerRepository;
import com.driagon.accountsservice.app.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = this.accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);

        if (null != loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);

        if (null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}