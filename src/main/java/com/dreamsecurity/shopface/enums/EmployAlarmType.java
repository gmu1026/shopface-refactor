package com.dreamsecurity.shopface.enums;

public enum EmployAlarmType {
    REGISTERED_MY_SCHEDULE("ADD_SCHEDULE");

    private String type;

    EmployAlarmType (String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }
}
