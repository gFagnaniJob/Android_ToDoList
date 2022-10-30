package com.example.todolist;

import androidx.room.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Entity()
public class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();

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

    private static ArrayList<Event> tmpEventsList = new ArrayList<>();


    public Event(int id, String name, LocalDate date, boolean important, Integer order) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.important = important;
        this.order = order;
        this.checked = false;
    }

    @Ignore
    public Event(String name, LocalDate date, boolean important, Integer order) {
        this.name = name;
        this.date = date;
        this.important = important;
        this.order = order;
        this.checked = false;
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
    public Integer getOrder() {
        return checked ? -1 : order;
    }
    @Ignore
    public void setOrder(Integer order) {
        this.order = order;
    }
    @Ignore
    public static ArrayList<Event> eventsForDate(LocalDate date, EventDatabase db) {
        tmpEventsList = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (event.getDate().equals(date)) {
                events.add(event);
            }
        }

        events = (ArrayList<Event>) db.eventDao().getEventPerDate(date);

        tmpEventsList = events;

        events = sortEvents();
        return events;
    }
    @Ignore
    public static ArrayList<Event> eventsNotChecked() {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (!event.isChecked()) {
                events.add(event);
            }
        }

        tmpEventsList = events;

        events = sortEvents();

        return events;
    }
    @Ignore
    public static ArrayList<Event> sortEvents() {
        Collections.sort(tmpEventsList, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return (lhs.getOrder() > rhs.getOrder()) ? -1 : (lhs.getOrder() < rhs.getOrder()) ? 1 : 0;
            }
        });
        return tmpEventsList;
    }
}
