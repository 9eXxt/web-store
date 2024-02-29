package integration;

import dao.CustomerDao;
import dto.CustomerCreateDto;
import dto.CustomerReadDto;
import entity.Customer;
import entity.Role;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import service.CustomerService;
import integration.util.ConnectionTestUtil;
import integration.util.TestDataImporter;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerServiceIT {
    private CustomerService customerService;
    private Customer customer;
    private static final SessionFactory sessionFactory = ConnectionTestUtil.buildSessionFactory();

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void setUp() {
        CustomerDao customerDao = new CustomerDao();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerReadMapper customerReadMapper = new CustomerReadMapper();
        customerService = new CustomerService(customerDao, customerMapper, customerReadMapper, sessionFactory);

        Session session = sessionFactory.openSession();
        customer = session.get(Customer.class, 1);
        session.close();
    }

    @Test
    @DisplayName("Login with valid data")
    void login_WithValidData_ReturnsOptionalOfCustomerDto() {
        String email = customer.getEmail();
        String password = customer.getPassword();

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
    }

    @Test
    @DisplayName("Find customer UUID for language cookie")
    void findByToken_WithValidData_ReturnsOptionalOfCustomerDto() {
        String token = "token123";

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

    }

    @Test
    @DisplayName("Find all customers in database")
    void findAll_WithValidData_ReturnsListOfCustomers() {
        List<CustomerReadDto> customers = customerService.findAll();

        Assertions.assertThat(customers)
                .isNotEmpty()
                .hasSizeBetween(3, 4);
    }

    @Test
    @DisplayName("Find customer via his ID")
    void findById_WithValidData_ReturnsOptionalOfCustomers() {
        Optional<CustomerReadDto> optionalCustomerDto = customerService.findById(1);

        assertThat(optionalCustomerDto)
                .isPresent()
                .hasValueSatisfying(customerDto -> {
                    assertThat(customerDto.getCustomer_id()).isEqualTo(customer.getCustomer_id());
                    assertThat(customerDto.getPersonalInfo()).isEqualTo(customer.getPersonalInfo());
                    assertThat(customerDto.getEmail()).isEqualTo(customer.getEmail())
                            .matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                });
    }

    @Test
    @DisplayName("Creating a new customer")
    void create_WithValidData() {
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("Bob", "Biden", "+1234242222",
                "ivan.ivanov@example.com", "paSsword@123", "123 Main St, Город, Страна");

        customerService.create(customerCreateDto, Role.USER);
    }
}
