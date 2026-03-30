package com.example.bankapplication.models;

import com.example.bankapplication.models.dto.AccountRequestDTO;
import com.example.bankapplication.models.enums.AccountStatus;
import com.example.bankapplication.models.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="accounts")
@Getter
@Setter
@NoArgsConstructor  // Necessary for JPA
@AllArgsConstructor // Great for Testing
@ToString(exclude = "customer") // Prevents infinite loops!
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,length=20)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id",nullable = false)
    @JsonIgnore
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private AccountType accountType;

    @Column(unique=true,updatable=false, nullable=false)
    private UUID accountUuid;

    @Column(precision = 15, scale = 2,nullable = false)
    private BigDecimal balance;

    @Column(name = "min_balance",precision = 15, scale = 2)
    private BigDecimal minimumBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status=AccountStatus.ACTIVE;

    @Column(length = 11, nullable = false)
    private String ifscCode;

    @Column(nullable=false,length = 100)
    private String branch;

    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime accountOpenedAt;

    public Account(AccountRequestDTO accountRequestDTO,Customer customer) {
        this.accountNumber = accountRequestDTO.accountNumber();
        this.customer = customer;
        this.accountType = accountRequestDTO.accountType();
        this.balance = accountRequestDTO.balance();
        this.ifscCode = accountRequestDTO.ifscCode();
        this.branch = accountRequestDTO.branch();
        this.balance = (accountRequestDTO.balance() != null) ? accountRequestDTO.balance() : BigDecimal.ZERO;
    }

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.accountOpenedAt == null) this.accountOpenedAt = now;
        if (this.accountUuid == null) this.accountUuid = UUID.randomUUID();
        if (this.minimumBalance == null && this.accountType != null) {
            this.minimumBalance = this.accountType.getMinBalance();
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }




}
