package com.dreamsecurity.shopface.enums;

public enum MemberState {
    ACTIVATE("A"),
    DEACTIVATE("D");

    private String state;

    MemberState (String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
