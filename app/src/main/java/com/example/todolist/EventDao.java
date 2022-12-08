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

    @Query("SELECT * FROM Event ORDER BY checked ASC, `order` DESC")
    List<Event> getEventList();

    @Query("SELECT * FROM ( " +
            "SELECT id, name, date, important, [order], checked, repeated FROM Event WHERE date = :date AND repeated = 0 " +
            "UNION SELECT id, name, date, important, [order], CASE WHEN ecd.eventId IS NULL THEN 0 ELSE 1 END as checked, repeated FROM Event e LEFT JOIN EventCheckedDate ecd ON e.id = ecd.eventId AND ecd.dateChecked = :date WHERE repeated = 1 AND dateStartRepetition <= :date " +
            ") ORDER BY checked ASC, `order` DESC")
    List<Event> getEventPerDate(LocalDate date);

    @Query("SELECT * FROM Event WHERE checked = 0 AND repeated = 0 ORDER BY checked ASC, `order` DESC")
    List<Event> getEventNotChecked();

    @Query("SELECT * FROM Event WHERE checked = 0 AND repeated = 0 AND date <= DATE('now') ORDER BY checked ASC, `order` DESC")
    List<Event> getPastEventNotChecked();

    @Insert
    void insertEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}
