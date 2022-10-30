package com.example.todolist;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class DateConverter {

    @TypeConverter
    public LocalDate toDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public Long toTimestamp(LocalDate date) {
        return date.toEpochDay();
    }
}