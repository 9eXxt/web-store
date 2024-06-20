package com.webstore.unit.service;

import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.PersonalInfo;
import com.webstore.model.entity.Role;
import com.webstore.model.mapper.CustomerCreateMapper;
import com.webstore.model.mapper.CustomerReadMapper;
import com.webstore.model.repository.CustomerRepository;
import com.webstore.model.service.CustomerService;
import com.webstore.model.util.SessionUtil;
import com.webstore.model.util.ValidationUtil;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({
        MockitoExtension.class
})
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerCreateMapper customerCreateMapper;
    @Mock
    private CustomerReadMapper customerReadMapper;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private PasswordEncoder passwordEncoder;
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
        CustomerReadDto customerReadDto = new CustomerReadDto(
                customer.getCustomer_id(),
                customer.getPersonalInfo().getFirst_name(),
                customer.getPersonalInfo().getLast_name(),
                customer.getEmail(),
                customer.getPhone_number(),
                customer.getPersonalInfo().getAddress()
        );

        when(customerRepository.findByEmailAndPassword(email, password)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(customerReadDto);

        try (MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class)) {
            mockedStatic.when(() -> SessionUtil.openSession(sessionFactory)).thenReturn(session);
            Optional<CustomerReadDto> optionalCustomerDto = customerService.login(email, password);

            assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        assertThat(customerDto.getFirst_name())
                                .isEqualTo(customer.getPersonalInfo().getFirst_name());
                        assertThat(customerDto.getLast_name())
                                .isEqualTo(customer.getPersonalInfo().getLast_name());
                        assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerRepository, Mockito.times(1)).findByEmailAndPassword(email, password);
        }
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "11223344";
        CustomerReadDto customerReadDto = new CustomerReadDto(
                customer.getCustomer_id(),
                customer.getPersonalInfo().getFirst_name(),
                customer.getPersonalInfo().getLast_name(),
                customer.getEmail(),
                customer.getPhone_number(),
                customer.getPersonalInfo().getAddress()
        );

        when(customerRepository.findByToken(token)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(customerReadDto);

        try (MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class)) {
            Optional<CustomerReadDto> optionalCustomerDto = customerService.findByToken(token);

            assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        Assertions.assertThat(customerDto.getLast_name() + " "
                                              + customerDto.getFirst_name())
                                .isEqualTo(customer.getPersonalInfo().getLast_name()
                                        + " " + customer.getPersonalInfo().getFirst_name());
                        assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerRepository, Mockito.times(1)).findByToken(token);
        }
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

        List<Customer> customerList = List.of(customer, customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customerList);
        customerList.forEach(customerTemp -> Mockito.when(customerReadMapper.mapFrom(customerTemp))
                .thenReturn(new CustomerReadDto(
                        customerTemp.getCustomer_id(),
                        customerTemp.getPersonalInfo().getFirst_name(),
                        customerTemp.getPersonalInfo().getLast_name(),
                        customerTemp.getEmail(),
                        customerTemp.getPhone_number(),
                        customerTemp.getPersonalInfo().getAddress()
                )));

        try (MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class)) {
            List<CustomerReadDto> customers = customerService.findAll();

            assertThat(customers)
                    .isNotEmpty()
                    .hasSize(3)
                    .allSatisfy(customerDto -> {
                        assertThat(customerDto.getFirst_name() + " "
                                              + customerDto.getLast_name()).matches("[^0-9]+");
                        assertThat(customerDto.getEmail()).matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    })
                    .extracting("customer_id", "first_name", "last_name", "email", "phone_number", "address")
                    .containsExactly(
                            tuple(customer.getCustomer_id(),
                                    customer.getPersonalInfo().getFirst_name(),
                                    customer.getPersonalInfo().getLast_name(),
                                    customer.getEmail(),
                                    customer.getPhone_number(),
                                    customer.getPersonalInfo().getAddress()),
                            tuple(customer1.getCustomer_id(),
                                    customer1.getPersonalInfo().getFirst_name(),
                                    customer1.getPersonalInfo().getLast_name(),
                                    customer1.getEmail(),
                                    customer1.getPhone_number(),
                                    customer1.getPersonalInfo().getAddress()),
                            tuple(customer2.getCustomer_id(),
                                    customer2.getPersonalInfo().getFirst_name(),
                                    customer2.getPersonalInfo().getLast_name(),
                                    customer2.getEmail(),
                                    customer2.getPhone_number(),
                                    customer2.getPersonalInfo().getAddress())
                    )
            ;

            verify(customerRepository, Mockito.times(1)).findAll();
        }
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(new CustomerReadDto(
                customer.getCustomer_id(),
                customer.getPersonalInfo().getFirst_name(),
                customer.getPersonalInfo().getLast_name(),
                customer.getEmail(),
                customer.getPhone_number(),
                customer.getPersonalInfo().getAddress()
        ));

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            Optional<CustomerReadDto> optionalCustomerDto = customerService.findById(1);

            assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        assertThat(customerDto.getFirst_name()).isEqualTo(customer.getPersonalInfo().getFirst_name());
                        assertThat(customerDto.getLast_name()).isEqualTo(customer.getPersonalInfo().getLast_name());
                        assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerRepository, Mockito.times(1)).findById(1);
        }
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", null);

//        Customer createdCustomer = Customer.builder()
//                .customer_id(1)
//                .personalInfo(PersonalInfo.builder()
//                        .first_name("Bob")
//                        .last_name("Biden")
//                        .address("123 Main St, Город, Страна")
//                        .build())
//                .email("ivan.ivanov@example.com")
//                .phone_number("123-424-2222")
//                .password("encodedPassword123")
//                .role(Role.USER)
//                .build();

        when(customerCreateMapper.mapFrom(customerCreateDto)).thenReturn(customer);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
//        when(customerRepository.save(customer)).thenReturn(createdCustomer);

        try (MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class);
             MockedStatic<ValidationUtil> mockedStatic1 = mockStatic(ValidationUtil.class)) {

            customerService.createUser(customerCreateDto);

            verify(customerRepository).save(customer);
        }
    }
}
