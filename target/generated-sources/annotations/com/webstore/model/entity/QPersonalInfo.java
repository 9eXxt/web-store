package com.webstore.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonalInfo is a Querydsl query type for PersonalInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonalInfo extends BeanPath<PersonalInfo> {

    private static final long serialVersionUID = -1487430656L;

    public static final QPersonalInfo personalInfo = new QPersonalInfo("personalInfo");

    public final StringPath address = createString("address");

    public final StringPath first_name = createString("first_name");

    public final StringPath last_name = createString("last_name");

    public QPersonalInfo(String variable) {
        super(PersonalInfo.class, forVariable(variable));
    }

    public QPersonalInfo(Path<? extends PersonalInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonalInfo(PathMetadata metadata) {
        super(PersonalInfo.class, metadata);
    }

}

