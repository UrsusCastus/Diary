package com.example.diary;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private static String LOG_CALENDAR_ACTIVITY = "LogCalendarActivity";

    private long mDateInMillis;
    private String mDateString;
    private int mDayOfMonth;
    private int mMonth;
    private int mYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        CalendarView calendarView = findViewById(R.id.activity_calendar_calendar_view);
        final Calendar calendar = Calendar.getInstance();
        mDateString = mSimpleDateFormat.format(calendar.getTime());
        Log.e(LOG_CALENDAR_ACTIVITY, mDateString);
        mDateInMillis = calendar.getTimeInMillis();
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            mDateString = mSimpleDateFormat.format(calendar.getTime());
            mDateInMillis = calendar.getTimeInMillis();
            mDayOfMonth = dayOfMonth;
            mMonth = month;
            mYear = year;
        });
        FloatingActionButton floatingActionButtonAddEvent =
                findViewById(R.id.activity_calendar_fab_add_event);
        floatingActionButtonAddEvent.setOnClickListener(view -> {
            Intent intent = new Intent(this, EventCreateActivity.class);
            intent.putExtra("DateOfCalendar", mDateString);
            intent.putExtra("DateInMillis", mDateInMillis);
            intent.putExtra("DayOfMonth", mDayOfMonth);
            intent.putExtra("Month", mMonth);
            intent.putExtra("Year", mYear);
            startActivity(intent);
        });
    }
}
