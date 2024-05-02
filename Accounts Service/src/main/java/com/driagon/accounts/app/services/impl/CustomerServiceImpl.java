package com.driagon.accounts.app.services.impl;

import com.driagon.accounts.app.clients.CardsFeignClient;
import com.driagon.accounts.app.clients.LoansFeignClient;
import com.driagon.accounts.app.dto.AccountsDto;
import com.driagon.accounts.app.dto.CardsDto;
import com.driagon.accounts.app.dto.CustomerDetailsDto;
import com.driagon.accounts.app.dto.LoansDto;
import com.driagon.accounts.app.entities.Account;
import com.driagon.accounts.app.entities.Customer;
import com.driagon.accounts.app.exceptions.ResourceNotFoundException;
import com.driagon.accounts.app.mapper.AccountMapper;
import com.driagon.accounts.app.mapper.CustomerMapper;
import com.driagon.accounts.app.repositories.AccountRepository;
import com.driagon.accounts.app.repositories.CustomerRepository;
import com.driagon.accounts.app.services.ICustomerService;
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
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = this.accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}