package com.example.bankapplication.models;

import com.example.bankapplication.models.dto.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor  // Necessary for JPA
@AllArgsConstructor // Great for Testing
@ToString(exclude = "accounts")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,updatable=false, nullable=false)
    private UUID customerUuid;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable=false, length=255)
    private String address;

    // Soft-delete compatible: No REMOVE or ORPHAN_REMOVAL here
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account){
        if (accounts == null) accounts = new ArrayList<>();
        accounts.add(account);
        account.setCustomer(this);
    }

    // Lifecycle Hook: Ensure UUID is generated before saving
    @PrePersist
    protected void onCreate() {
        if (this.customerUuid == null) {
            this.customerUuid = UUID.randomUUID();
        }
    }
    public Customer(CustomerRequestDTO dto) {
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.email = dto.email();
        this.address = dto.address();
        this.phoneNumber = dto.phoneNumber();
    }

}
