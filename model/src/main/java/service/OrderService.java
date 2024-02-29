package service;

import dao.OrderDao;
import dto.OrderReadDto;
import lombok.AllArgsConstructor;
import mapper.OrderReadMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.SessionUtil;

import java.util.List;

@AllArgsConstructor
public class OrderService {
    private final OrderDao orderDao;
    private final OrderReadMapper orderReadMapper;
    private final SessionFactory sessionFactory;

    public List<OrderReadDto> findOrdersByCustomer(Integer customer_id) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return orderDao.findOrdersByCustomer(customer_id).stream()
                    .map(orderReadMapper::mapFrom)
                    .toList();
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

    public List<OrderReadDto> findOrdersByItems(Integer item_id) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return orderDao.findOrdersByItem(item_id).stream()
                    .map(orderReadMapper::mapFrom)
                    .toList();
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }
}
