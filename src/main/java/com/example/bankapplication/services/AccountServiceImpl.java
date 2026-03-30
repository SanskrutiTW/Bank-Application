package com.example.bankapplication.services;
import com.example.bankapplication.models.Account;
import com.example.bankapplication.models.dto.AccountRequestDTO;
import com.example.bankapplication.models.Customer;
import com.example.bankapplication.models.enums.AccountStatus;
import com.example.bankapplication.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final CustomerService customerService;
    private final AccountRepository accountRepository;

    @Transactional // Ensures "All or Nothing" database operations
   public Account createAccount(AccountRequestDTO accountRequestDTO){
       // 1.Business Logic : Check Minimum Balance Requirement by Enum
        BigDecimal requiredMin= accountRequestDTO.accountType().getMinBalance();
        if(accountRequestDTO.balance().compareTo(requiredMin)<0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_CONTENT,"Opening balance is below the minimum required for " +
                    accountRequestDTO.accountType()+"("+requiredMin+")"
            );
        }
        // 2.Fetch the customer
        Customer customer=customerService.getCustomerById(accountRequestDTO.customerId());
        // 3.Mapping DTO to Entity
       Account account=new Account();
       account.setAccountNumber(accountRequestDTO.accountNumber());
       account.setCustomer(customer);
       account.setAccountType(accountRequestDTO.accountType());
       account.setBalance(accountRequestDTO.balance());
       account.setIfscCode(accountRequestDTO.ifscCode());
       account.setBranch(accountRequestDTO.branch());
       // 4.Persistence
       return accountRepository.save(account);

   }

    public Page<Account> getAccountByUuidAsList(UUID uuid,Pageable pageable){
        return findAccountByUuid(uuid,pageable);
    }

    public Page<Account> getAllAccounts(Pageable pageable){
        return accountRepository.findAllByStatus(pageable,AccountStatus.ACTIVE);
    }

    public Page<Account> findAccountByUuid(UUID uuid, Pageable pageable){
        Page<Account> accountPage = accountRepository.findByAccountUuidAndStatus(uuid, AccountStatus.ACTIVE,pageable);

        if (accountPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found: " + uuid);
        }
        return accountPage;
    }

   public void deactivateAccount(UUID uuid){
        Account account=getAccountByUuid(uuid);
        account.setStatus(AccountStatus.INACTIVE);
       accountRepository.save(account);
   }

   public Account getAccountByUuid(UUID uuid){
        return accountRepository.findByAccountUuidAndStatus(uuid,AccountStatus.ACTIVE)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not Found with number: "+uuid));
   }

}
