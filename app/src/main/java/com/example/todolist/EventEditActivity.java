package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private CheckBox importantCB;
    private Integer eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();

        if (EventUtils.selectedEvent != null) {
            String name = EventUtils.selectedEvent.getName();
            eventNameET.setText(name);
            importantCB.setChecked(EventUtils.selectedEvent.isImportant());
        }

        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        importantCB = findViewById(R.id.importantCB);
    }

    public void goBackAction(View view) {
        super.onBackPressed();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        boolean important = importantCB.isChecked();

        if (eventId == -1) {
            Integer order = Event.eventsList.size() + 1;
            Event newEvent = new Event(eventName, CalendarUtils.selectedDate, important, order);
            Event.eventsList.add(newEvent);
        } else {
            for (Event e : Event.eventsList) {
                if (e.getId() == eventId) {
                    e.setName(eventName);
                    e.setImportant(important);
                }
            }
        }

        finish();
    }
}