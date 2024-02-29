package integration.util;

import entity.*;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                // Import Customer data
                List<Customer> customers = Arrays.asList(
                        createCustomer("John", "Doe", "john@example.com", "123456", "1234567890", Role.USER),
                        createCustomer("Jane", "Doe", "jane@example.com", "654321", "0987654321", Role.USER),
                        createCustomer("Admin", "Adminovich", "admin@example.com", "admin", "0987654322", Role.ADMIN)
                );

                customers.forEach(session::persist);

                // Import UserSession data
                List<UserSession> userSessions = Arrays.asList(
                        createUserSession("token123", customers.get(0), "192.168.0.1", "Chrome on Windows"),
                        createUserSession("token456", customers.get(1), "10.0.0.1", "Safari on Mac")
                );

                userSessions.forEach(session::persist);

                // Import Item data
                List<Item> items = Arrays.asList(
                        createItem("Item 1", "Description 1", new BigDecimal("100.00"), 10),
                        createItem("Item 2", "Description 2", new BigDecimal("200.00"), 20)
                );

                items.forEach(session::persist);

                // Import Order data
                List<Order> orders = Arrays.asList(
                        createOrder(customers.get(0)),
                        createOrder(customers.get(1))
                );

                orders.forEach(session::persist);

                // Import OrderItem data
                List<OrderItem> orderItems = Arrays.asList(
                        createOrderItem(orders.get(0), items.get(0), "Item 1", 2),
                        createOrderItem(orders.get(1), items.get(1), "Item 2", 1)
                );

                orderItems.forEach(session::persist);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    private Customer createCustomer(String firstName, String lastName, String email, String password, String phoneNumber, Role role) {
        return Customer.builder()
                .personalInfo(PersonalInfo.builder()
                        .first_name(firstName)
                        .last_name(lastName)
                        .build())
                .email(email)
                .password(password)
                .phone_number(phoneNumber)
                .role(role)
                .build();
    }

    private UserSession createUserSession(String sessionToken, Customer customer, String ipAddress, String deviceInfo) {
        return UserSession.builder()
                .session_token(sessionToken)
                .customer(customer)
                .ip_address(ipAddress)
                .device_info(deviceInfo)
                .build();
    }

    private Item createItem(String name, String description, BigDecimal price, int quantityLeft) {
        return Item.builder()
                .name(name)
                .description(description)
                .price(price)
                .quantity_left(quantityLeft)
                .build();
    }

    private Order createOrder(Customer customer) {
        return Order.builder()
                .order_date(LocalDateTime.now())
                .customer(customer)
                .build();
    }

    private OrderItem createOrderItem(Order order, Item item, String nameItem, Integer count) {
        return OrderItem.builder()
                .order_number(order)
                .item(item)
                .name_item(nameItem)
                .count(count)
                .build();
    }
}
