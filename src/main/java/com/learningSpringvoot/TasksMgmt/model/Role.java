package com.learningSpringvoot.TasksMgmt.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();    }
}