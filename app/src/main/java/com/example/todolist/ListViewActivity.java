package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));
    }

    public void newEventAction(View view) {
    }
}