package com.dreamsecurity.shopface.enums;

public enum EmployState {
    INVITE("I"),
    EMPLOY("E"),
    FIRE("F");

    private String state;

    EmployState (String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
