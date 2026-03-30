package com.example.bankapplication.services;

import com.example.bankapplication.models.Customer;
import com.example.bankapplication.models.dto.CustomerRequestDTO;
import com.example.bankapplication.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor // Automatically creates the constructor for the final field
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Customer not found with ID: "+id
                ));
    }

    @Override
    public Customer createCustomer(CustomerRequestDTO customerRequestDTO){
        // Mapping Entity to DTO
        if(customerRepository.existsByEmail(customerRequestDTO.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Duplicate Email");
        }
        Customer customer=new Customer(customerRequestDTO);
        return customerRepository.save(customer);
    }

}
