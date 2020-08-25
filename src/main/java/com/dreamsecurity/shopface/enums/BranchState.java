package com.dreamsecurity.shopface.enums;

public enum BranchState {
    YES("Y"),
    NO("N");

    private String state;

    BranchState (String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
