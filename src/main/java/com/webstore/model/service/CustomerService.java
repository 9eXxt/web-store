package com.webstore.model.service;

import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.CustomerUpdateDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.Role;
import com.webstore.model.mapper.CustomerCreateMapper;
import com.webstore.model.mapper.CustomerReadMapper;
import com.webstore.model.repository.CustomerRepository;
import com.webstore.model.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerCreateMapper customerCreateMapper;
    private final CustomerReadMapper customerReadMapper;
    private final PasswordEncoder passwordEncoder;
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
    public CustomerReadDto updateCustomer(CustomerUpdateDto customerUpdateDto) {
        ValidationUtil.validate(customerUpdateDto);

        Customer customer = customerRepository.findByEmail(customerUpdateDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customerUpdateDto.getFirst_name() != null) {
            customer.getPersonalInfo().setFirst_name(customerUpdateDto.getFirst_name());
        }
        if (customerUpdateDto.getLast_name() != null) {
            customer.getPersonalInfo().setLast_name(customerUpdateDto.getLast_name());
        }
        if (customerUpdateDto.getPhone_number() != null) {
            customer.setPhone_number(customerUpdateDto.getPhone_number());
        }
        if (customerUpdateDto.getEmail() != null) {
            customer.setEmail(customerUpdateDto.getEmail());
        }
        if (customerUpdateDto.getAddress() != null) {
            customer.getPersonalInfo().setAddress(customerUpdateDto.getAddress());
        }
        return customerReadMapper.mapFrom(customerRepository.save(customer));
    }
    @Transactional
    public CustomerReadDto createUser(CustomerCreateDto customerCreateDto) {
        return create(customerCreateDto, Role.USER);
    }

    @Transactional
    public void createAdmin(CustomerCreateDto customerCreateDto) {
        create(customerCreateDto, Role.ADMIN);
    }

    private CustomerReadDto create(CustomerCreateDto customerCreateDto, Role role) {
        ValidationUtil.validate(customerCreateDto);

        Customer customer = customerCreateMapper.mapFrom(customerCreateDto);
        customer.setRole(role);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerReadMapper.mapFrom(customerRepository.save(customer));
    }

}
