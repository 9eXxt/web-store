
-- Данные для таблицы "customer"
INSERT INTO customer (customer_id, first_name, last_name, email, password, phone_number, role)
VALUES
    (1,'John', 'Doe', 'john@example.com', '123456', '1234567890', 'USER'),
    (2, 'Jane', 'Doe', 'jane@example.com', '654321', '0987654321', 'USER'),
    (3, 'Admin', 'Adminovich', 'admin@example.com', 'admin', '0987654322', 'ADMIN');

SELECT SETVAL('customer_customer_id_seq', (SELECT MAX(customer_id) FROM customer));

-- Данные для таблицы "user_session"
INSERT INTO user_session (session_token, customer_id, ip_address, device_info, expires_at)
VALUES
    ('token123', 1, '192.168.0.1', 'Chrome on Windows', null),
    ('token456', 2, '10.0.0.1', 'Safari on Mac', null);

-- Данные для таблицы "item"
INSERT INTO item (item_id, name, description, price, quantity_left)
VALUES
    (1, 'Item 1', 'Description 1', 100.00, 10),
    (2, 'Item 2', 'Description 2', 200.00, 20);


-- Данные для таблицы "order"
INSERT INTO "order" (order_id, order_date, customer_id)
VALUES
(1, NOW(), 1),
(2, NOW(), 1),
(3, NOW(), 2),
(4, NOW(), 2);

SELECT SETVAL('order_order_id_seq', (SELECT MAX(order_id) FROM "order"));

-- Данные для таблицы "order_item"
INSERT INTO order_item (order_id, item_id, name_item, count)
VALUES
(1, 1, 'Item 1', 2),
(2, 2, 'Item 2', 1);

--SELECT SETVAL('item_items_id_seq', (SELECT MAX(item_id) FROM item));
