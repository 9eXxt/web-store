package dao;


import com.querydsl.jpa.impl.JPAQuery;
import entity.Order;
import util.SessionUtil;

import java.util.List;

import static entity.QItem.*;
import static entity.QOrderItem.*;
import static entity.QOrder.*;

public class OrderDao extends CRUD<Integer, Order> {

    public OrderDao() {
        super(Order.class);
    }

    public List<Order> findOrdersByCustomer(Integer customer_id) {
        var session = SessionUtil.getSession();
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(order.customer.customer_id.eq(customer_id))
                .fetch();
    }

    public List<Order> findOrdersByItem(Integer items_id) {
        var session = SessionUtil.getSession();
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .join(orderItem)
                .on(order.order_id.eq(orderItem.order_number.order_id))
                .join(item)
                .on(item.item_id.eq(orderItem.item.item_id))
                .where(item.item_id.eq(items_id))
                .fetch();
    }
}
