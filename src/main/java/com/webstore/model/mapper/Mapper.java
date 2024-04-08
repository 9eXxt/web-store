package com.webstore.model.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
