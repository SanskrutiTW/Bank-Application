package com.example.bankapplication.repositories;

import com.example.bankapplication.models.Account;
import com.example.bankapplication.models.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountUuidAndStatus(UUID accountUuid, AccountStatus accountStatus);
    Page<Account> findAllByStatus(Pageable pageable,AccountStatus accountStatus);
    Page<Account> findByAccountUuidAndStatus(UUID accountUuid, AccountStatus accountStatus,Pageable pageable);

}
