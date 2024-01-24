package service;

import dao.ItemsDao;
import dto.ItemsDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ItemsService {
    private final ItemsDao itemsDao;

    public List<ItemsDto> findAll() {
        return itemsDao.findAll().stream()
                .map(items -> new ItemsDto(items.getItems_id(), items.getName(), items.getQuantity_left()))
                .collect(Collectors.toList());
    }
}
