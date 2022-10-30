package com.example.todolist;

import static com.example.todolist.CalendarUtils.daysInMonthArray;
import static com.example.todolist.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, EventAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventsListViewMonth;
    private ArrayList<Event> dailyEvents;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now(ZoneId.systemDefault());
        }
        EventUtils.selectedEvent = null;
        setMonthView();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventsListViewMonth = findViewById(R.id.eventsListViewMonth);
        registerForContextMenu(eventsListViewMonth);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setEventAdapter();
    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onCalendarItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));
        finish();
    }

    public void listViewAction(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
        finish();
    }

    public void newEventAction(View view) {
        Intent intent = new Intent(this, EventEditActivity.class);
        intent.putExtra("NEW", -1);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);

        eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents, this);

        eventsListViewMonth.setAdapter(eventAdapter);
    }

    private void updateEventAdapter() {
        dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.eventsListViewMonth) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.item_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Event e = (Event) eventsListViewMonth.getItemAtPosition(info.position);

        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                EventUtils.selectedEvent = e;
                Intent intent = new Intent(this, EventEditActivity.class);
                intent.putExtra("eventId", e.getId());
                startActivity(intent);
                return true;
            case R.id.delete:
                setDeleteDialog(e.getId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onChecked(CheckBox cb, Event e) {

        if (cb.isChecked()) {
            cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            e.setChecked(true);
        } else {
            cb.setPaintFlags(cb.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            e.setChecked(false);
        }
        updateEventAdapter();
    }

    private void setDeleteDialog(int eventId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Eliminare attività");
        alert.setMessage("Sei sicuro di voler eliminare questa attività?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Event> newList = new ArrayList<>();
                for (Event e : Event.eventsList) {
                    if (e.getId() != eventId) {
                        newList.add(e);
                    }
                }
                Event.eventsList.clear();
                Event.eventsList.addAll(newList);
                eventAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}