package com.webstore.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -2131342843L;

    public static final QItem item = new QItem("item");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> item_id = createNumber("item_id", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<OrderItem, QOrderItem> orderItem = this.<OrderItem, QOrderItem>createList("orderItem", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final NumberPath<Integer> quantity_left = createNumber("quantity_left", Integer.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

