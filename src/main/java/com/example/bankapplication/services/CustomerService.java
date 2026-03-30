package com.example.bankapplication.services;

import com.example.bankapplication.models.Customer;
import com.example.bankapplication.models.dto.CustomerRequestDTO;

public interface CustomerService {
    Customer getCustomerById(Long id);
    Customer createCustomer(CustomerRequestDTO customerRequestDTO);
}
