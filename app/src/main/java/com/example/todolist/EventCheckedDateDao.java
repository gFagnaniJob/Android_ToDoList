package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface EventCheckedDateDao {
    @Query("SELECT * FROM EventCheckedDate WHERE eventId = :id AND dateChecked = :date")
    List<EventCheckedDate> getEventByIdDate(Integer id, LocalDate date);
    @Query("SELECT * FROM EventCheckedDate WHERE eventId = :id")
    List<EventCheckedDate> getEventsById(Integer id);

    @Insert
    void insertEvent(EventCheckedDate event);

    @Update
    void updateEvent(EventCheckedDate event);

    @Delete
    void deleteEvent(EventCheckedDate event);
}
