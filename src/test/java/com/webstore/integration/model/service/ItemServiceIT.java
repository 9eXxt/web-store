package com.webstore.integration.model.service;


import com.webstore.integration.IntegrationTestBase;
import com.webstore.model.dto.ItemReadDto;
import com.webstore.model.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor
public class ItemServiceIT extends IntegrationTestBase {
    private final ItemService itemService;

    @Test
    @DisplayName("Find all items in database")
    void findAll_WithValidData_ReturnsListOfItemsDto() {
        List<ItemReadDto> itemsDtoList = itemService.findAll();

        assertThat(itemsDtoList).isNotEmpty()
                .hasSize(2);
    }
}
