package com.example.todolist;

import static com.example.todolist.CalendarUtils.daysInWeekArray;
import static com.example.todolist.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements EventAdapter.OnItemListener {

    private ListView eventsListView;
    ArrayList<Event> dailyEvents;
    EventAdapter eventAdapter;
    private EventDatabase appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        appDb = EventDatabase.getInstance(this);
        initWidgets();
        EventUtils.selectedEvent = null;
        setView();
    }

    private void initWidgets() {
        eventsListView = findViewById(R.id.eventsListView);

        registerForContextMenu(eventsListView);
    }

    private void setView() {
        setEventAdapter();
    }

    private void setEventAdapter() {
        dailyEvents = Event.eventsNotChecked(appDb);
        eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents, this);

        eventsListView.setAdapter(eventAdapter);
    }

    private void updateEventAdapter() {
        dailyEvents.clear();
        dailyEvents.addAll(Event.eventsNotChecked(appDb));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //eventAdapter.notifyDataSetChanged();
                eventsListView.setAdapter(eventAdapter);
            }
        }, 500);
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.eventsWeekListView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.item_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Event e = (Event) eventsListView.getItemAtPosition(info.position);

        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                EventUtils.selectedEvent = e;
                Intent intent = new Intent(this, EventEditActivity.class);
                intent.putExtra("eventId", e.getId());
                startActivity(intent);
                return true;
            case R.id.delete:
                setDeleteDialog(e);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onChecked(CheckBox cb, Event e) {
        if (cb.isChecked()) {
            e.setChecked(true);
        } else {
            e.setChecked(false);
        }

        appDb.eventDao().updateEvent(e);
        updateEventAdapter();
    }

    private void setDeleteDialog(Event e) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ListViewActivity.this);
        alert.setTitle("Eliminare attivit??");
        alert.setMessage("Sei sicuro di voler eliminare questa attivit???");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                appDb.eventDao().deleteEvent(e);
                ArrayList<EventCheckedDate> eventsCheckedDate = (ArrayList<EventCheckedDate>) appDb.eventCheckedDateDao().getEventsById(e.getId());
                for (EventCheckedDate ecd : eventsCheckedDate) {
                    appDb.eventCheckedDateDao().deleteEvent(ecd);
                }
                updateEventAdapter();
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