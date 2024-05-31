package com.webstore.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROOT,
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
