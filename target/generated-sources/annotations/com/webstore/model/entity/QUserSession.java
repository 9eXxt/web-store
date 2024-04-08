package com.webstore.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSession is a Querydsl query type for UserSession
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSession extends EntityPathBase<UserSession> {

    private static final long serialVersionUID = -1543093991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSession userSession = new QUserSession("userSession");

    public final QCustomer customer;

    public final StringPath device_info = createString("device_info");

    public final DateTimePath<java.sql.Timestamp> expires_at = createDateTime("expires_at", java.sql.Timestamp.class);

    public final StringPath ip_address = createString("ip_address");

    public final StringPath session_token = createString("session_token");

    public QUserSession(String variable) {
        this(UserSession.class, forVariable(variable), INITS);
    }

    public QUserSession(Path<? extends UserSession> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSession(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSession(PathMetadata metadata, PathInits inits) {
        this(UserSession.class, metadata, inits);
    }

    public QUserSession(Class<? extends UserSession> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

