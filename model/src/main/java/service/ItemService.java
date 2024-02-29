package service;

import dao.ItemDao;
import dto.ItemReadDto;
import lombok.AllArgsConstructor;
import mapper.ItemReadMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.SessionUtil;

import java.util.List;

@AllArgsConstructor
public class ItemService {
    private final ItemDao itemDao;
    private final ItemReadMapper itemReadMapper;
    private final SessionFactory sessionFactory;

    public List<ItemReadDto> findAll() {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return itemDao.findAll().stream()
                    .map(itemReadMapper::mapFrom)
                    .toList();
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }
}
