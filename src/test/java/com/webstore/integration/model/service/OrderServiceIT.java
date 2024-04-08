package com.webstore.integration.model.service;


import com.webstore.integration.IntegrationTestBase;
import com.webstore.model.dto.OrderReadDto;
import com.webstore.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor
public class OrderServiceIT extends IntegrationTestBase {
    private final OrderService orderService;
    @Test
    void findOrdersByCustomer_WithValidData_ReturnsListOfOrdersDto() {
        List<OrderReadDto> orderDtoList = orderService.findOrdersByCustomer(1);

        assertThat(orderDtoList).isNotEmpty();
    }

    @Test
    void findOrdersByItem_WithValidData_ReturnsListOfOrdersDto() {
        List<OrderReadDto> ordersDtoList = orderService.findOrdersByItem(1);

        assertThat(ordersDtoList).isNotEmpty();
    }
}
