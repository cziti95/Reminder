package com.example.czirjktibor.reminderapp.utils;

import android.arch.persistence.room.TypeConverter;

import com.example.czirjktibor.reminderapp.entitys.Color;

import static com.example.czirjktibor.reminderapp.entitys.Color.*;

public class ColorConverter {

    @TypeConverter
    public static Color toColor(int color) {
        if (color == BLUE.getCode()) {
            return BLUE;
        }
        else if (color == RED.getCode()) {
            return RED;
        }
        else if (color == YELLOW.getCode()) {
            return YELLOW;
        }
        else if (color == GREEN.getCode()) {
            return GREEN;
        }
        else if (color == ORANGE.getCode()) {
            return ORANGE;
        }
        else if (color == CYAN.getCode()) {
            return CYAN;
        }
        else {
            throw new IllegalArgumentException("Could not recognize color.");
        }
    }

    @TypeConverter
    public static int toInt(Color color) {
        return color.getCode();
    }
}
