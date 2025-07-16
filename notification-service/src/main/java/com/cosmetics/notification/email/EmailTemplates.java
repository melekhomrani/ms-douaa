package com.cosmetics.notification.email;

import lombok.Getter;

public enum EmailTemplates {

    NEW_BRAND_ADDED("brand-notification.html", "New brand added"),
    NEW_PRODUCT_ADDED("product-notification.html", "New product added")
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
