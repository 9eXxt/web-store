package com.webstore.controller;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.CustomerUpdateDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController{
    private final CustomerService customerService;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerReadDto> findAll() {
        return customerService.findAll();
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerReadDto findById(@PathVariable("id") Integer id) {
        return customerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/cabinet", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerReadDto findByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String email = (String) authentication.getPrincipal();
        return customerService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerReadDto> updateCustomer(@RequestBody CustomerUpdateDto customerUpdateDto, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            CustomerReadDto updatedCustomer = customerService.updateCustomer(customerUpdateDto);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
