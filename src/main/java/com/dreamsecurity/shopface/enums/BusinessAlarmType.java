package com.dreamsecurity.shopface.enums;

public enum BusinessAlarmType {
    EMPLOYEE_JOIN("JOINING"),
    SUCCESSFUL_COMMUTE("COMMUTE_SUCCESS"),
    FAILED_TO_COMMUTE("COMMUTE_FAIL");

    private String type;

    BusinessAlarmType (String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }
}
