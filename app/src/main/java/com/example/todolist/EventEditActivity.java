package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private CheckBox importantCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();
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
        Integer order = Event.eventsList.size() + 1;
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, important, order);
        Event.eventsList.add(newEvent);

        finish();
    }
}