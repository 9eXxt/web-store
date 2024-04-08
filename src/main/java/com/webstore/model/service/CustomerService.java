package com.webstore.model.service;

import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.Role;
import com.webstore.model.mapper.CustomerMapper;
import com.webstore.model.mapper.CustomerReadMapper;
import com.webstore.model.repository.CustomerRepository;
import com.webstore.model.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerReadMapper customerReadMapper;

    public Optional<CustomerReadDto> login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password)
                .map(customerReadMapper::mapFrom);
    }

    public Optional<CustomerReadDto> findByToken(String token) {
        return customerRepository.findByToken(token)
                .map(customerReadMapper::mapFrom);
    }

    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerReadMapper::mapFrom)
                .toList();
    }

    public Optional<CustomerReadDto> findById(Integer customer_id) {
        return customerRepository.findById(customer_id)
                .map(customerReadMapper::mapFrom);
    }

    public Optional<CustomerReadDto> findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerReadMapper::mapFrom);
    }

    public List<CustomerReadDto> findCustomersByItem(Integer item_id) {
        return customerRepository.findCustomersByItem_id(item_id).stream()
                .map(customerReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public void createUser(CustomerCreateDto customerCreateDto) {
        create(customerCreateDto, Role.USER);
    }

    @Transactional
    public void createAdmin(CustomerCreateDto customerCreateDto) {
        create(customerCreateDto, Role.ADMIN);
    }

    private void create(CustomerCreateDto customerCreateDto, Role role) {
        ValidationUtil.validate(customerCreateDto);

        Customer customer = customerMapper.mapFrom(customerCreateDto);
        customer.setRole(role);
        customerRepository.save(customer);
    }
}
