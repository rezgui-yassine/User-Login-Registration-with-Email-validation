package com.yessinCoding.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account"); // Ensure this matches the template file name

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}