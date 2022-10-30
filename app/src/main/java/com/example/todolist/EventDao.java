package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM Event")
    List<Event> getEventList();

    @Query("SELECT * FROM Event WHERE date = :date")
    List<Event> getEventPerDate(LocalDate date);

    @Query("SELECT * FROM Event WHERE checked = 0")
    List<Event> getEventNotChecked();

    @Insert
    void insertEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}
