package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.time.LocalDate;

@Entity(primaryKeys = {"eventId","dateChecked"})
public class EventCheckedDate {
    @ColumnInfo(name = "eventId")
    @NonNull
    int eventId;
    @ColumnInfo(name = "dateChecked")
    @NonNull
    LocalDate dateChecked;

    public EventCheckedDate(int eventId, LocalDate dateChecked) {
        this.eventId = eventId;
        this.dateChecked = dateChecked;
    }

    @Ignore
    public int getEventId() {
        return eventId;
    }

    @Ignore
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Ignore
    public LocalDate getDateChecked() {
        return dateChecked;
    }

    @Ignore
    public void setDateChecked(LocalDate dateChecked) {
        this.dateChecked = dateChecked;
    }
}
