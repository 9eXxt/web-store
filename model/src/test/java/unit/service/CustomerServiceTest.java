package unit.service;

import dao.CustomerDao;
import dto.CustomerCreateDto;
import dto.CustomerReadDto;
import entity.Customer;
import entity.PersonalInfo;
import entity.Role;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CustomerService;
import util.SessionUtil;
import util.ValidationUtil;

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
    private CustomerDao customerDao;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerReadMapper customerReadMapper;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
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
                customer.getPersonalInfo(),
                customer.getEmail()
        );

        when(customerDao.findByEmailAndPassword(email, password)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(customerReadDto);

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            mockedStatic.when(() -> SessionUtil.openSession(sessionFactory)).thenReturn(session);
            Optional<CustomerReadDto> optionalCustomerDto = customerService.login(email, password);

            Assertions.assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        Assertions.assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        Assertions.assertThat(customerDto.getPersonalInfo())
                                .isEqualTo(customer.getPersonalInfo());
                        Assertions.assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerDao, times(1)).findByEmailAndPassword(email, password);
        }
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "11223344";
        CustomerReadDto customerReadDto = new CustomerReadDto(
                customer.getCustomer_id(),
                customer.getPersonalInfo(),
                customer.getEmail()
        );

        when(customerDao.findByToken(token)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(customerReadDto);

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            Optional<CustomerReadDto> optionalCustomerDto = customerService.findByToken(token);

            Assertions.assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        assertThat(customerDto.getPersonalInfo().getLast_name() + " "
                                + customerDto.getPersonalInfo().getFirst_name())
                                .isEqualTo(customer.getPersonalInfo().getLast_name()
                                        + " " + customer.getPersonalInfo().getFirst_name());
                        assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerDao, times(1)).findByToken(token);
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

        when(customerDao.findAll()).thenReturn(customerList);
        customerList.forEach(customerTemp -> when(customerReadMapper.mapFrom(customerTemp))
                .thenReturn(new CustomerReadDto(
                        customerTemp.getCustomer_id(),
                        customerTemp.getPersonalInfo(),
                        customerTemp.getEmail()
                )));

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            List<CustomerReadDto> customers = customerService.findAll();

            Assertions.assertThat(customers)
                    .isNotEmpty()
                    .hasSize(3)
                    .allSatisfy(customerDto -> {
                        assertThat(customerDto.getPersonalInfo().getFirst_name() + " "
                                + customerDto.getPersonalInfo().getLast_name()).matches("[^0-9]+");
                        assertThat(customerDto.getEmail()).matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    })
                    .extracting("customer_id", "personalInfo", "email")
                    .containsExactly(
                            Assertions.tuple(customer.getCustomer_id(),
                                    customer.getPersonalInfo(), customer.getEmail()),
                            Assertions.tuple(customer1.getCustomer_id(),
                                    customer1.getPersonalInfo(), customer1.getEmail()),
                            Assertions.tuple(customer2.getCustomer_id(),
                                    customer2.getPersonalInfo(), customer2.getEmail())
                    );

            verify(customerDao, times(1)).findAll();
        }
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        when(customerDao.findById(1)).thenReturn(Optional.of(customer));
        when(customerReadMapper.mapFrom(customer)).thenReturn(new CustomerReadDto(
                customer.getCustomer_id(),
                customer.getPersonalInfo(),
                customer.getEmail()
        ));

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            Optional<CustomerReadDto> optionalCustomerDto = customerService.findById(1);

            Assertions.assertThat(optionalCustomerDto)
                    .isPresent()
                    .hasValueSatisfying(customerDto -> {
                        assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                        assertThat(customerDto.getPersonalInfo()).isEqualTo(customer.getPersonalInfo());
                        assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                                .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                    });

            verify(customerDao, times(1)).findById(1);
        }
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("Bob", "Biden", "123-424-2222",
                "ivan.ivanov@example.com", "password123", "123 Main St, Город, Страна");

        when(session.beginTransaction()).thenReturn(transaction);
        when(session.getTransaction()).thenReturn(transaction);
        when(customerMapper.mapFrom(customerCreateDto)).thenReturn(customer);

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class);
             MockedStatic<ValidationUtil> mockedStatic1 = Mockito.mockStatic(ValidationUtil.class)) {
            mockedStatic.when(() -> SessionUtil.openSession(sessionFactory)).thenReturn(session);

            customerService.create(customerCreateDto, Role.USER);

            verify(customerDao).save(customer);
        }
    }
}
