package com.dreamsecurity.shopface.enums;

public enum ScheduleColor {
    BLUE("#0070C0"),
    GREEN("#00B050"),
    YELLOW("#FFC000"),
    PURPLE("#7030A0"),
    RED("#C00000"),
    SKYBLUE("#4BACC6"),
    LIGHTGREEN("#9BBB59"),
    LIGHTORANGE("#F79646"),
    OCHER("#C0504D"),
    GRAY("#7F7F7F");

    private String colorCode;

    ScheduleColor(String colorCode){
        this.colorCode = colorCode;
    }

    public String getColorCode(){
        return colorCode;
    }
}
