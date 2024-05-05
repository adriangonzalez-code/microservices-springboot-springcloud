package com.driagon.accountsservice.app.mapper;

import com.driagon.accountsservice.app.dto.AccountsDto;
import com.driagon.accountsservice.app.entities.Account;

public class AccountMapper {

    public static AccountsDto mapToAccountsDto(Account account, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());

        return accountsDto;
    }

    public static Account mapToAccount(AccountsDto accountsDto, Account account) {
        account.setAccountNumber(accountsDto.getAccountNumber());
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());

        return account;
    }
}