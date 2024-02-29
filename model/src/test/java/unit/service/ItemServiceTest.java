package unit.service;

import dao.ItemDao;
import dto.ItemReadDto;
import entity.Item;
import mapper.ItemReadMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ItemService;
import util.SessionUtil;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({
        MockitoExtension.class
})
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;
    @Mock
    private ItemDao itemDao;
    @Mock
    private ItemReadMapper itemReadMapper;

    @Test
    @DisplayName("Find all items in database")
    void findAll_WithValidData_ReturnsListOfItemsDto() {
        Item item1 = Item.builder()
                .item_id(1)
                .name("PS 5")
                .description("description1")
                .price(BigDecimal.valueOf(20000))
                .quantity_left(1)
                .build();
        Item item2 = Item.builder()
                .item_id(2)
                .name("PS 4")
                .description("description2")
                .price(BigDecimal.valueOf(15000))
                .quantity_left(3)
                .build();
        List<Item> items = List.of(item1, item2);

        when(itemDao.findAll()).thenReturn(items);
        items.forEach(item -> when(itemReadMapper.mapFrom(item)).thenReturn(new ItemReadDto(
                item.getItem_id(),
                item.getName(),
                item.getDescription(),
                item.getQuantity_left()
        )));
        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            List<ItemReadDto> itemsDtoList = itemService.findAll();

            assertThat(itemsDtoList).isNotEmpty()
                    .hasSize(2)
                    .extracting("item_id", "name", "description", "quantity_left")
                    .containsExactly(
                            tuple(item1.getItem_id(), item1.getName(), item1.getDescription(), item1.getQuantity_left()),
                            tuple(item2.getItem_id(), item2.getName(), item2.getDescription(), item2.getQuantity_left())
                    );

            verify(itemDao, times(1)).findAll();
        }
    }
}
