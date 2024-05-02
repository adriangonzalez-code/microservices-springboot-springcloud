package com.driagon.accounts.app.services;

import com.driagon.accounts.app.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}