package com.diemdanh.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CoverStringToTime {
    public static LocalDateTime cover(String dateString){
        dateString = dateString.substring(0,10) + "T00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime date = LocalDateTime.parse(dateString,formatter);
        return date;
    }
}
