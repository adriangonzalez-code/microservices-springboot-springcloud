package com.driagon.accountsservice.app.services;

import com.driagon.accountsservice.app.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param request  - CustomerDto Object
     */
    void createAccount(CustomerDto request);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto request);

    boolean deleteAccount(String mobileNumber);
}