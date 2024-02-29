package dao;

import entity.Item;

public class ItemDao extends CRUD<Integer, Item> {
    public ItemDao() {
        super(Item.class);
    }
}
