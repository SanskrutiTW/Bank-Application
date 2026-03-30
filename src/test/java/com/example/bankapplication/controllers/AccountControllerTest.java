package com.example.bankapplication.controllers;

import com.example.bankapplication.models.Account;
import com.example.bankapplication.models.dto.AccountRequestDTO;
import com.example.bankapplication.models.Customer;
import com.example.bankapplication.models.enums.AccountType;
import com.example.bankapplication.services.AccountService;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void shouldCreateAccount_AndReturn201Status() throws Exception{
//        // 1. Prepare Request DTO
//        AccountRequestDTO request=new AccountRequestDTO("123456789012",1L, AccountType.SAVINGS,
//                new BigDecimal("1000.00"),"ICIC0001234", "Downtown Branch");
//
//        // 2. Mock the Service Response
//        Account mockSavedAccount = new Account();
//        // 3. mock customer
//        Customer mockCustomer=new Customer();
//        mockCustomer.setId(1L);
//        mockCustomer.setFirstName("Riya");
//        mockCustomer.setLastName("Patel");
//        mockCustomer.setEmail("riya14@gmail.com");
//        mockCustomer.setPhoneNumber("+919987789023");
//        mockCustomer.setAddress("TWU");
//        mockSavedAccount.setId(101L);
//        mockSavedAccount.setAccountNumber("123456789012");
//        mockSavedAccount.setCustomer(mockCustomer);
//        mockSavedAccount.setAccountType(AccountType.SAVINGS);
//        mockSavedAccount.setBalance(new BigDecimal("1000.00"));
//        mockSavedAccount.setIfscCode("ICIC0001234");
//        mockSavedAccount.setBranch("Downtown Branch");
//
//        // 4.Setting the response when service is called
//        when(accountService.createAccount(any(AccountRequestDTO.class))).thenReturn(mockSavedAccount);
//
//        // 5.Perform POST request
//        mockMvc.perform(post("/api/accounts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(101))
//                .andExpect(jsonPath("$.accountNumber").value("123456789012"));
//
//    }
}
