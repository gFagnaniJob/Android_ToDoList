package com.example.todolist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    private List<Event> events;
    private final EventAdapter.OnItemListener onItemListener;

    public EventAdapter(@NonNull Context context, List<Event> events, EventAdapter.OnItemListener onItemListener) {
        super(context, 0, events);
        this.events = events;
        this.onItemListener = onItemListener;
    }

    public List<Event> getItems() {
        return events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        CheckBox eventCheckBox = convertView.findViewById(R.id.eventCheckBox);

        String eventTitle = event.getName();
        eventCheckBox.setText(eventTitle);

        eventCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.onChecked((CheckBox) view, event);
            }
        });

        if (event.isImportant()) {
            SpannableString str = new SpannableString(eventCheckBox.getText());
            str.setSpan(new BackgroundColorSpan(Color.parseColor("#e8b095")), 0, eventCheckBox.getText().length(), 0);
            eventCheckBox.setText(str);
            eventCheckBox.setTypeface(eventCheckBox.getTypeface(), Typeface.NORMAL);
        }

        if (event.isChecked()) {
            eventCheckBox.setPaintFlags(eventCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            eventCheckBox.setChecked(true);
            event.setChecked(true);
        } else {
            eventCheckBox.setPaintFlags(eventCheckBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            eventCheckBox.setChecked(false);
            event.setChecked(false);
        }

        return convertView;
    }

    public interface OnItemListener {
        void onChecked(CheckBox cb, Event e);
    }
}
