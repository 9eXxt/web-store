package com.webstore.model.service;

import com.webstore.model.dto.*;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.Item;
import com.webstore.model.entity.Order;
import com.webstore.model.mapper.CustomerReadMapper;
import com.webstore.model.mapper.ItemReadMapper;
import com.webstore.model.mapper.OrderReadMapper;
import com.webstore.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final ItemReadMapper itemReadMapper;
    private final CustomerReadMapper customerReadMapper;
    public CustomerOrdersDto findOrdersByCustomer(Integer customer_id) {
        List<Object[]> result = orderRepository.findOrdersByCustomer_id(customer_id);
        Customer customerTemp = (Customer) result.get(0)[0];
        CustomerReadDto customer = new CustomerReadDto(customerTemp.getCustomer_id(), customerTemp.getPersonalInfo().getFirst_name(),
                customerTemp.getPersonalInfo().getLast_name(), customerTemp.getEmail(), customerTemp.getPhone_number(),
                customerTemp.getPersonalInfo().getAddress());
        List<OrderReadDto> orders = result.stream()
                .map(order -> (Order) order[1])
                .map(order -> new OrderReadDto(order.getOrder_id(), order.getOrder_date(), order.getOrder_status(),
                        order.getCustomer().getCustomer_id()))
                .toList();
        return new CustomerOrdersDto(customer, orders);
    }

    public ItemOrdersDto findOrdersByItem(Integer item_id) {
        List<Object[]> result = orderRepository.findOrdersByItem_id(item_id);

        Item itemTemp = (Item) result.get(0)[0];
        ItemReadDto item = itemReadMapper.mapFrom(itemTemp);

        List<CustomerOrderDto> customerOrder = result.stream()
                .map(customerOrderTemp -> {
                    CustomerReadDto customerReadDto = customerReadMapper.mapFrom((Customer) customerOrderTemp[1]);
                    OrderReadDto orderReadDto = orderReadMapper.mapFrom((Order) customerOrderTemp[2]);
                    return new CustomerOrderDto(customerReadDto, orderReadDto);
                }).toList();

        return new ItemOrdersDto(item, customerOrder);
    }
}
