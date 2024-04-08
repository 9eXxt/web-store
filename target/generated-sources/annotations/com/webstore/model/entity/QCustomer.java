package com.webstore.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = 119637456L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomer customer = new QCustomer("customer");

    public final NumberPath<Integer> customer_id = createNumber("customer_id", Integer.class);

    public final StringPath email = createString("email");

    public final ListPath<Order, QOrder> order = this.<Order, QOrder>createList("order", Order.class, QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final QPersonalInfo personalInfo;

    public final StringPath phone_number = createString("phone_number");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QCustomer(String variable) {
        this(Customer.class, forVariable(variable), INITS);
    }

    public QCustomer(Path<? extends Customer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomer(PathMetadata metadata, PathInits inits) {
        this(Customer.class, metadata, inits);
    }

    public QCustomer(Class<? extends Customer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.personalInfo = inits.isInitialized("personalInfo") ? new QPersonalInfo(forProperty("personalInfo")) : null;
    }

}

