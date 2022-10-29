package com.example.todolist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();

    private int id;
    private String name;
    private LocalDate date;
    private boolean important;
    private Integer order;

    private boolean checked;

    public Event(String name, LocalDate date, boolean important, Integer order) {
        this.id = eventsList.size();
        this.name = name;
        this.date = date;
        this.important = important;
        this.order = order;
        this.checked = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (event.getDate().equals(date)) {
                events.add(event);
            }
        }

        return events;
    }

    public static  void sortEvents() {
        Collections.sort(eventsList, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return (lhs.getOrder() > rhs.getOrder() || lhs.isChecked()) ? -1 : (lhs.getOrder() < rhs.getOrder() || !lhs.isChecked()) ? 1 : 0;
            }
        });
    }
}
