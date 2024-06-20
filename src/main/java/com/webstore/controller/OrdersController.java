package com.webstore.controller;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.OrderCreateDto;
import com.webstore.model.dto.OrderReadDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderReadDto> create(@RequestBody OrderCreateDto orderCreateDto) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(email+ "    bfdgdfg");
        CustomerReadDto customerReadDto = customerService.findByEmail(email).get();
        return ResponseEntity.ok(orderService.create(orderCreateDto, customerReadDto));
    }

//    @GetMapping("customers/{id}/orders")
//    public String getOrdersByCustomer(Model model, @PathVariable("id") Integer customer_id) {
//        model.addAttribute("customerOrders", orderService.findOrdersByCustomer(customer_id));
//        return "/ordersByCustomer";
//    }
//
//    @GetMapping("items/{id}/orders")
//    public String getOrdersByItem(Model model, @PathVariable("id") Integer item_id) {
//        model.addAttribute("itemOrderCustomer", orderService.findOrdersByItem(item_id));
//        return "/ordersByItem";
//    }
}
