package com.example.czirjktibor.reminderapp.entitys;

public enum Color {
    BLUE(0),
    RED(1),
    YELLOW(2),
    GREEN(3),
    ORANGE(4),
    CYAN(5);

    private int code;

    Color(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
