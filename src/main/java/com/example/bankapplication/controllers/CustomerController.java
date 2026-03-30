package com.example.bankapplication.controllers;

import com.example.bankapplication.models.Customer;
import com.example.bankapplication.models.dto.CustomerRequestDTO;
import com.example.bankapplication.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="CustomerController",description="Customer Operations")
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor // Automatically creates the constructor for the final field
public class CustomerController {
    private final CustomerService customerService;
    @Operation(summary="Register a new customer", description="Creates a customer profile.This is prerequisite for " +
            "opening a bank account")
    @ApiResponses(value={
            @ApiResponse(
                    responseCode="201",
                    description="Customer created successfully",
                    content=@Content(
                            mediaType="application/json",
                            schema=@Schema(implementation=Customer.class))
            ),
            @ApiResponse(
                    responseCode="400",description="Invalid Input",
                    content=@Content(mediaType="application/json")
            ),
            @ApiResponse(
                    responseCode="409",description="Duplicate Email",
                    content=@Content(mediaType="application/json")
            )
    })
    @PostMapping()
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        Customer savedCustomer=customerService.createCustomer(customerRequestDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
