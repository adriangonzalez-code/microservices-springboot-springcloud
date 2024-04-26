package com.driagon.accounts.app.mapper;

import com.driagon.accounts.app.dto.AccountsDto;
import com.driagon.accounts.app.entities.Account;

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