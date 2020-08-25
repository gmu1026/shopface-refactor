package com.dreamsecurity.shopface.enums;

public enum MemberType {
    BUSINESSMAN("B"),
    EMPLOYEE("E");

    private String type;

    MemberType (String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
