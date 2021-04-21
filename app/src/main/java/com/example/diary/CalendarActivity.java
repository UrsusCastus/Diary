package com.example.diary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        FloatingActionButton floatingActionButtonAddEvent =
                findViewById(R.id.activity_calendar_fab_add_event);
        floatingActionButtonAddEvent.setOnClickListener(view -> {
            Intent intent = new Intent(this, EventCreateActivity.class);
            startActivity(intent);
        });
    }
}
