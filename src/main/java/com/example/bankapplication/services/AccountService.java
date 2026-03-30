package com.example.bankapplication.services;

import com.example.bankapplication.models.Account;
import com.example.bankapplication.models.dto.AccountRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService{

    Account createAccount(AccountRequestDTO accountRequest);

    void deactivateAccount(UUID uuid);

    Page<Account> getAccountByUuidAsList(UUID uuid, Pageable pageable);

    Page<Account> getAllAccounts(Pageable pageable);

    Account getAccountByUuid(UUID uuid);

}
