package service;

import dao.CustomerDao;
import dto.CreateCustomerDto;
import dto.CustomerDto;
import entity.Customer;
import entity.PersonalInfo;
import entity.Role;
import exception.ValidationException;
import mapper.CreateCustomerMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.*;
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

    private Customer customer;

    @BeforeEach
    void setUp() {
        PersonalInfo personalInfo = PersonalInfo.builder()
                .first_name("Bob")
                .last_name("Biden")
                .address("123 Main St, Город, Страна")
                .build();

        customer = Customer.builder()
                .customer_id(1)
                .personalInfo(personalInfo)
                .email("ivan.ivanov@example.com")
                .phone_number("123-424-2222")
                .password("password123")
                .role(Role.USER)
                .build();
    }

    @Test
    @DisplayName("Login with valid data")
    void login_WithValidData_ReturnsOptionalOfCustomerDto() {
        String email = customer.getEmail();
        String password = customer.getPassword();

        when(customerDao.findByEmailAndPassword(email, password)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.login(email, password);

        assertThat(optionalCustomerDto)
                .isPresent()
                .hasValueSatisfying(customerDto -> {
                    assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                    assertThat(customerDto.getName())
                            .isEqualTo(customer.getPersonalInfo().getLast_name()
                                    + " " + customer.getPersonalInfo().getFirst_name());
                    assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                            .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                });

        verify(customerDao, times(1)).findByEmailAndPassword(email, password);
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "11223344";

        when(customerDao.findByToken(token)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.findByToken(token);

        assertThat(optionalCustomerDto).isPresent()
                .hasValueSatisfying(customerDto -> {
                    assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                    assertThat(customerDto.getName())
                            .isEqualTo(customer.getPersonalInfo().getLast_name()
                                    + " " + customer.getPersonalInfo().getFirst_name());
                    assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                            .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                });

        verify(customerDao, times(1)).findByToken(token);
    }

    @Test
    @DisplayName("Find all customers in database")
    void findAll_WithValidData_ReturnsListOfCustomers() {
        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .first_name("Иван")
                .last_name("Иванов")
                .address("123 Main St, Город, Страна")
                .build();
        Customer customer1 = Customer.builder()
                .customer_id(1)
                .personalInfo(personalInfo1)
                .email("ivan.ivanov@example.com")
                .phone_number("123-456-7890")
                .password("password123")
                .role(Role.USER)
                .build();

        PersonalInfo personalInfo2 = PersonalInfo.builder()
                .first_name("Мария")
                .last_name("Петрова")
                .address("456 Side Ave, Другой Город, Страна")
                .build();
        Customer customer2 = Customer.builder()
                .customer_id(2)
                .personalInfo(personalInfo2)
                .email("maria.petrova@example.com")
                .phone_number("098-765-4321")
                .password("securePassword!")
                .role(Role.ADMIN)
                .build();

        when(customerDao.findAll()).thenReturn(List.of(customer1, customer2));

        List<CustomerDto> customers = customerService.findAll();

        assertThat(customers)
                .isNotEmpty()
                .hasSize(2)
                .allSatisfy(customerDto -> {
                    assertThat(customerDto.getName()).matches("[^0-9]+");
                    assertThat(customerDto.getEmail()).matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                })
                .extracting("customer_id", "name", "email")
                .containsExactly(
                        tuple(customer1.getCustomer_id(),
                                customer1.getPersonalInfo().getLast_name()
                                        + " " + customer1.getPersonalInfo().getFirst_name(), customer1.getEmail()),
                        tuple(customer2.getCustomer_id(),
                                customer2.getPersonalInfo().getLast_name()
                                        + " " + customer2.getPersonalInfo().getFirst_name(), customer2.getEmail())
                );

        verify(customerDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        when(customerDao.findById(1)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> optionalCustomerDto = customerService.findById(1);

        assertThat(optionalCustomerDto)
                .isPresent()
                .hasValueSatisfying(customerDto -> {
                    assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                    assertThat(customerDto.getName())
                            .isEqualTo(customer.getPersonalInfo().getLast_name()
                                    + " " + customer.getPersonalInfo().getFirst_name());
                    assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                            .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                });

        verify(customerDao, times(1)).findById(1);
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");
        ValidationResult validationResult = new ValidationResult();
        when(createCustomerValidator.isValid(createCustomerDto)).thenReturn(validationResult);
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
