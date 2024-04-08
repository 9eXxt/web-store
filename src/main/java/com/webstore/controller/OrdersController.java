package com.webstore.controller;

import com.webstore.model.dto.CustomerOrdersDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.ItemOrdersDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final CustomerService customerService;

    @GetMapping("customers/{id}/orders")
    public String getOrdersByCustomer(Model model, @PathVariable("id") Integer customer_id) {
        model.addAttribute("customerOrders", orderService.findOrdersByCustomer(customer_id));
        return "/ordersByCustomer";
    }

    @GetMapping("items/{id}/orders")
    public String getOrdersByItem(Model model, @PathVariable("id") Integer item_id) {
        model.addAttribute("itemOrderCustomer", orderService.findOrdersByItem(item_id));
        return "/ordersByItem";
    }
}
