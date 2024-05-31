package com.webstore.integration.model.service;


import com.webstore.integration.IntegrationTestApplication;
import com.webstore.integration.IntegrationTestBase;
import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor
public class CustomerServiceIT extends IntegrationTestBase {
    private final CustomerService customerService;

    @Test
    @DisplayName("Login with valid data")
    void login_WithValidData_ReturnsOptionalOfCustomerDto() {
        String email = "john@example.com";
        String password = "123456";

        Optional<CustomerReadDto> optionalCustomerDto = customerService.login(email, password);

        assertThat(optionalCustomerDto)
                .isPresent();
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "token123";

        Optional<CustomerReadDto> optionalCustomerDto = customerService.findByToken(token);

        assertThat(optionalCustomerDto)
                .isPresent();

    }

    @Test
    @DisplayName("Find all customers in database")
    void findAll_WithValidData_ReturnsListOfCustomers() {
        List<CustomerReadDto> customers = customerService.findAll();

        assertThat(customers)
                .isNotEmpty()
                .hasSizeBetween(3, 4);
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        Optional<CustomerReadDto> optionalCustomerDto = customerService.findById(1);

        assertThat(optionalCustomerDto)
                .isPresent();
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("Bob", "Biden", "+1234242222",
                "ivan.ivanov@example.com", "paSsword@123");

        customerService.createUser(customerCreateDto);

        Optional<CustomerReadDto> customerReadDto = customerService.findByEmail(customerCreateDto.getEmail());

        assertThat(customerReadDto).isPresent();
    }
}
