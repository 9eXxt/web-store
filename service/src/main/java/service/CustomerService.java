package service;

import dao.CustomerDao;
import dto.CreateCustomerDto;
import dto.CustomerDto;
import entity.Customer;
import exception.ValidationException;
import mapper.CreateCustomerMapper;
import validator.CreateCustomerValidator;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CustomerService {
    private final CustomerDao customerDao;
    private final CreateCustomerValidator createCustomerValidator;
    private final CreateCustomerMapper createCustomerMapper;
    public CustomerService(CustomerDao customerDao, CreateCustomerValidator createCustomerValidator,
                           CreateCustomerMapper createCustomerMapper) {
        this.customerDao = customerDao;
        this.createCustomerValidator = createCustomerValidator;
        this.createCustomerMapper = createCustomerMapper;
    }

    public Optional<CustomerDto> login(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password)
                .map(customer ->
                        new CustomerDto(customer.getCustomer_id(),
                                customer.getLast_name() + " " + customer.getFirst_name(), customer.getEmail()));
    }
    public Optional<CustomerDto> findByToken(String token) {
        return customerDao.findByToken(token)
                .map(customer ->
                        new CustomerDto(customer.getCustomer_id(),
                                customer.getLast_name() + " " + customer.getFirst_name(),customer.getEmail()));
    }
    public List<CustomerDto> findAll() {
        return customerDao.findAll().stream()
                .map(customer ->
                        new CustomerDto(customer.getCustomer_id(),
                                customer.getLast_name() + " " + customer.getFirst_name(), customer.getEmail()))
                .collect(toList());
    }
    public Optional<CustomerDto> findById(Integer customer_id) {
        return customerDao.findById(customer_id)
                .map(customer ->
                        new CustomerDto(customer.getCustomer_id(),
                                customer.getLast_name() + " " + customer.getFirst_name(), customer.getEmail()));
    }
    public void create(CreateCustomerDto createCustomerDto) {
        ValidationResult validationResult = createCustomerValidator.isValid(createCustomerDto);
        if(!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Customer customer = createCustomerMapper.mapFrom(createCustomerDto);
        customerDao.save(customer);
    }
}
