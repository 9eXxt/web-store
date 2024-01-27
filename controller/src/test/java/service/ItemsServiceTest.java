package service;

import dao.ItemsDao;
import dto.ItemsDto;
import entity.Items;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({
        MockitoExtension.class
})
public class ItemsServiceTest {
    @InjectMocks
    private ItemsService itemsService;
    @Mock
    private ItemsDao itemsDao;

    @Test
    @DisplayName("Find all items in database")
    void findAll_WithValidData_ReturnsListOfItemsDto() {
        Items itemExample1 = new Items(1, "PS 5",
                "description", BigDecimal.valueOf(20000), 1);
        Items itemExample2 = new Items(2, "PS 4",
                "description", BigDecimal.valueOf(15000), 1);

        when(itemsDao.findAll()).thenReturn(List.of(itemExample1, itemExample2));

        List<ItemsDto> itemsDtoList = itemsService.findAll();


        assertThat(itemsDtoList).isNotEmpty()
                .hasSize(2)
                .extracting("item_id", "name", "quantity_left")
                .containsExactly(
                        tuple(itemExample1.getItems_id(), itemExample1.getName(), itemExample1.getQuantity_left()),
                        tuple(itemExample2.getItems_id(), itemExample2.getName(), itemExample2.getQuantity_left())
                );

        verify(itemsDao, times(1)).findAll();
    }
}
