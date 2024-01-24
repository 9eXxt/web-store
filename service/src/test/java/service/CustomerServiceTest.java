package service;

import dao.CustomerDao;
import dto.CreateCustomerDto;
import dto.CustomerDto;
import entity.Customer;
import exception.ValidationException;
import mapper.CreateCustomerMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import validator.CreateCustomerValidator;
import validator.Error;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({
        MockitoExtension.class
})
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerDao customerDao;
    @Mock
    private CreateCustomerValidator createCustomerValidator;
    @Mock
    private CreateCustomerMapper createCustomerMapper;

    @Test
    @DisplayName("Login with valid data")
    void login_WithValidData_ReturnsOptionalOfCustomerDto() {
        String email = "bob@gmail.com";
        String password = "password1";
        Customer customer = new Customer(1, "Bob", "Biden", "123-424-2222",
                email, password, "123 Main St, Город, Страна");
        when(customerDao.findByEmailAndPassword(email, password)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.login(email, password);

        CustomerDto customerDto = optionalCustomerDto.get();
        assertEquals(email, customerDto.getEmail());
        assertTrue(customerDto.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"));
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "11223344";
        Customer customer = new Customer(1, "Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        when(customerDao.findByToken(token)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.findByToken(token);

        assertTrue(optionalCustomerDto.isPresent());
        optionalCustomerDto.ifPresent(customerDto -> {
            assertEquals(customerDto.getCustomer_id(), customer.getCustomer_id());
            assertEquals(customerDto.getName(), customer.getLast_name() + " " + customer.getFirst_name());
            assertEquals(customerDto.getEmail(), customer.getEmail());
            assertTrue(customerDto.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"));
        });
    }

    @Test
    @DisplayName("Find all customers in database")
    void findAll_WithValidData_ReturnsListOfCustomers() {
        Customer customer1 = new Customer(1, "Иван", "Иванов",
                "123-456-7890", "ivan.ivanov@example.com", "password123",
                "123 Main St, Город, Страна");
        Customer customer2 = new Customer(2, "Мария", "Петрова",
                "098-765-4321", "maria.petrova@example.com", "securePassword!",
                "456 Side Ave, Другой Город, Страна");
        when(customerDao.findAll()).thenReturn(List.of(customer1, customer2));

        List<CustomerDto> customers = customerService.findAll();

        assertEquals(2, customers.size());
        assertTrue(customers.stream().allMatch(customer -> customer.getName().matches("[^0-9]+")
                && customer.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")));

        verify(customerDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        Integer customerId = 1;
        Customer customer = new Customer(1, "Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        when(customerDao.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.findById(customerId);

        assertTrue(optionalCustomerDto.isPresent());
        optionalCustomerDto.ifPresent(customerDto -> {
            assertEquals(customerDto.getCustomer_id(), customer.getCustomer_id());
            assertEquals(customerDto.getName(), customer.getLast_name() + " " + customer.getFirst_name());
            assertEquals(customerDto.getEmail(), customer.getEmail());
            assertTrue(customerDto.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"));
        });
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        ValidationResult validationResult = new ValidationResult();
        when(createCustomerValidator.isValid(createCustomerDto)).thenReturn(validationResult);

        Customer customer = new Customer(1, "Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        when(createCustomerMapper.mapFrom(createCustomerDto)).thenReturn(customer);

        customerService.create(createCustomerDto);

        verify(customerDao).save(customer);
    }

    @Test
    @DisplayName("Creating customer with its invalid data")
    void create_WithInvalidData() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        ValidationResult validationResult = new ValidationResult();
        validationResult.add(new Error("12", "error"));
        when(createCustomerValidator.isValid(createCustomerDto)).thenReturn(validationResult);

        assertThrows(ValidationException.class, () -> customerService.create(createCustomerDto));
    }
}
