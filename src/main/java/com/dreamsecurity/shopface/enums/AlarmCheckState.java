package com.dreamsecurity.shopface.enums;

public enum AlarmCheckState {
    READ("Y"),
    UNREAD("N");

    private String state;

    AlarmCheckState (String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
