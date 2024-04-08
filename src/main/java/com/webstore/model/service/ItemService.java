package com.webstore.model.service;

import com.webstore.model.dto.ItemReadDto;
import com.webstore.model.mapper.ItemReadMapper;
import com.webstore.model.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemReadMapper itemReadMapper;

    public List<ItemReadDto> findAll() {
        return itemRepository.findAll().stream()
                .map(itemReadMapper::mapFrom)
                .toList();
    }

    public Optional<ItemReadDto> findById(Integer id) {
        return itemRepository.findById(id)
                .map(itemReadMapper::mapFrom);
    }
}
