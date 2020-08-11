package com.dreamsecurity.shopface.enums;

public enum ScheduleState {
    REGISTER("R"),
    GO_WORKING("W"),
    LATE("L"),
    ABSENTEEISM("A"),
    COMPLETE("C");

    private String state;

    ScheduleState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
