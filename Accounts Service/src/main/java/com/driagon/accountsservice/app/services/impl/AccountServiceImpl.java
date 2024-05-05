package com.driagon.accountsservice.app.services.impl;

import com.driagon.accountsservice.app.constants.AccountsConstants;
import com.driagon.accountsservice.app.dto.AccountsDto;
import com.driagon.accountsservice.app.dto.CustomerDto;
import com.driagon.accountsservice.app.entities.Account;
import com.driagon.accountsservice.app.entities.Customer;
import com.driagon.accountsservice.app.exceptions.CustomerAlreadyException;
import com.driagon.accountsservice.app.exceptions.ResourceNotFoundException;
import com.driagon.accountsservice.app.mapper.AccountMapper;
import com.driagon.accountsservice.app.mapper.CustomerMapper;
import com.driagon.accountsservice.app.repositories.AccountRepository;
import com.driagon.accountsservice.app.repositories.CustomerRepository;
import com.driagon.accountsservice.app.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    /**
     * @param request - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto request) {
        Customer customer = CustomerMapper.mapToCustomer(request, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyException("Customer already registered with given mobileNumber " + request.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(this.createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = this.accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(account, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto request) {
        boolean isUpdated = false;

        AccountsDto accountsDto = request.getAccountsDto();

        if (accountsDto != null) {
            Account account = this.accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountMapper.mapToAccount(accountsDto, account);
            account = this.accountRepository.save(account);

            Long customerId = account.getCustomerId();

            Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(request, customer);
            this.customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        this.accountRepository.deleteByCustomerId(customer.getCustomerId());
        this.customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);

        return account;
    }
}