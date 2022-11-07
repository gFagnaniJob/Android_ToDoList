package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private CheckBox importantCB;
    private CheckBox repeatedCB;
    private Integer eventId;
    private EventDatabase appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        appDb = EventDatabase.getInstance(this);

        initWidgets();

        if (EventUtils.selectedEvent != null) {
            String name = EventUtils.selectedEvent.getName();
            eventNameET.setText(name);
            importantCB.setChecked(EventUtils.selectedEvent.isImportant());
            repeatedCB.setChecked(EventUtils.selectedEvent.isRepeated());
        }

        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        importantCB = findViewById(R.id.importantCB);
        repeatedCB = findViewById(R.id.repeatedCB);
    }

    public void goBackAction(View view) {
        super.onBackPressed();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        boolean important = importantCB.isChecked();
        boolean repeated = repeatedCB.isChecked();

        ArrayList<Event> events = (ArrayList<Event>) appDb.eventDao().getEventList();

        if (eventId == -1) {
            Integer order = events.size() + 1;
            Event newEvent = new Event(eventName, CalendarUtils.selectedDate, important, order, repeated, repeated ? CalendarUtils.selectedDate : null);
            //Event.eventsList.add(newEvent);
            appDb.eventDao().insertEvent(newEvent);
        } else {
            for (Event e : events) {
                if (e.getId() == eventId) {
                    e.setName(eventName);
                    e.setImportant(important);
                    e.setRepeated(repeated);
                    e.setDateStartRepetition(repeated ? CalendarUtils.selectedDate : null);
                    appDb.eventDao().updateEvent(e);
                }
            }
        }
        EventUtils.selectedEvent = null;
        finish();
    }
}