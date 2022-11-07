package com.example.todolist;

import androidx.room.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Entity()
public class Event {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "date")
    LocalDate date;
    @ColumnInfo(name = "important")
    boolean important;
    @ColumnInfo(name = "order")
    Integer order;
    @ColumnInfo(name = "checked")
    boolean checked;
    @ColumnInfo(name = "repeated")
    boolean repeated;

    @ColumnInfo(name = "dateStartRepetition")
    LocalDate dateStartRepetition;

    public Event(int id, String name, LocalDate date, boolean important, Integer order, boolean repeated, LocalDate dateStartRepetition) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.important = important;
        this.order = order;
        this.checked = false;
        this.repeated = repeated;
        this.dateStartRepetition = dateStartRepetition;
    }

    @Ignore
    public Event(String name, LocalDate date, boolean important, Integer order, boolean repeated, LocalDate dateStartRepetition) {
        this.name = name;
        this.date = date;
        this.important = important;
        this.order = order;
        this.checked = false;
        this.repeated = repeated;
        this.dateStartRepetition = dateStartRepetition;
    }

    @Ignore
    public int getId() {
        return id;
    }
    @Ignore
    public String getName() {
        return name;
    }
    @Ignore
    public void setName(String name) {
        this.name = name;
    }
    @Ignore
    public LocalDate getDate() {
        return date;
    }
    @Ignore
    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Ignore
    public boolean isImportant() {
        return important;
    }
    @Ignore
    public void setImportant(boolean important) {
        this.important = important;
    }
    @Ignore
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    @Ignore
    public boolean isChecked() {
        return checked;
    }
    @Ignore
    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }
    @Ignore
    public boolean isRepeated() {
        return repeated;
    }
    @Ignore
    public Integer getOrder() {
        return checked ? -1 : order;
    }
    @Ignore
    public void setOrder(Integer order) {
        this.order = order;
    }


    @Ignore
    public LocalDate getDateStartRepetition() {
        return dateStartRepetition;
    }

    @Ignore
    public void setDateStartRepetition(LocalDate dateStartRepetition) {
        this.dateStartRepetition = dateStartRepetition;
    }
    @Ignore
    public static ArrayList<Event> eventsForDate(LocalDate date, EventDatabase db) {
        ArrayList<Event> events = new ArrayList<>();
        events = (ArrayList<Event>) db.eventDao().getEventPerDate(date);
        return events;
    }

    @Ignore
    public static ArrayList<Event> eventsNotChecked(EventDatabase db) {
        ArrayList<Event> events = new ArrayList<>();
        events = (ArrayList<Event>) db.eventDao().getEventNotChecked();
        return events;
    }
}
