package com.yessinCoding.email;

import lombok.Getter;

import java.security.PrivilegedAction;

@Getter

public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate-account");


    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
