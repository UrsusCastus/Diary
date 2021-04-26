package com.example.diary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class EventCreateActivity extends AppCompatActivity implements DateTimePickerDialog.DateTimePickerDialogListener {

    private static final String LOG_EVENT_CREATE_ACTIVITY = "LogEventCreateActivity";

    private ConstraintLayout mStartTimeConstraintLayout;
    private ConstraintLayout mEndTimeConstraintLayout;
    private ImageButton mCloseEventCreateActivityImageButton;
    private ImageButton mEventDoneImageButton;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private TextView mDateStartTextView;
    private TextView mDateEndTextView;

    private int mDayOfMonth;
    private int mMonth;
    private int mYear;

    private long mStartTime;
    private long mEndTime;

    private boolean mStartDateTimePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_event_create);

        Intent intent = getIntent();
        String dateStartString = intent.getStringExtra("DateOfCalendar");
        mDayOfMonth = intent.getIntExtra("DayOfMonth", 0);
        mMonth = intent.getIntExtra("Month", 0);
        mYear = intent.getIntExtra("Year", 2021);

        mStartTimeConstraintLayout = findViewById(R.id.constraint_layout_start_time);
        mEndTimeConstraintLayout = findViewById(R.id.constraint_layout_end_time);

        mCloseEventCreateActivityImageButton = findViewById(R.id.event_toolbar_button_close);
        mEventDoneImageButton = findViewById(R.id.event_toolbar_button_done);
        mEventDoneImageButton.setEnabled(false);

        mTitleEditText = findViewById(R.id.activity_event_create_edit_text_title);
        mDescriptionEditText = findViewById(R.id.activity_event_create_edit_text_description);

        mDateStartTextView = findViewById(R.id.activity_event_create_start_time);
        mDateEndTextView = findViewById(R.id.activity_event_create_end_time);

        mDateStartTextView.setText(dateStartString);

        mCloseEventCreateActivityImageButton.setOnClickListener(v -> {
            finish();
        });

        mEventDoneImageButton.setOnClickListener(v -> {
            createEvent();
            finish();
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEnablerEventDoneImageButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mTitleEditText.addTextChangedListener(textWatcher);
        mDescriptionEditText.addTextChangedListener(textWatcher);

        mStartTimeConstraintLayout.setOnClickListener(v -> {
            DialogFragment dateTimePickerDialog = new DateTimePickerDialog();
            Bundle bundleArguments = new Bundle();
            mStartDateTimePickerDialog = true;
            bundleArguments.putInt("DayOfMonth", mDayOfMonth);
            bundleArguments.putInt("Month", mMonth);
            bundleArguments.putInt("Year", mYear);
            bundleArguments.putBoolean("StartTime", mStartDateTimePickerDialog);
            dateTimePickerDialog.setArguments(bundleArguments);
            dateTimePickerDialog.show(getSupportFragmentManager(), "startTimePickerDialogFragment");
        });

        mEndTimeConstraintLayout.setOnClickListener(v -> {
            DialogFragment dateTimePickerDialog = new DateTimePickerDialog();
            Bundle bundleArguments = new Bundle();
            mStartDateTimePickerDialog = false;
            bundleArguments.putInt("DayOfMonth", mDayOfMonth);
            bundleArguments.putInt("Month", mMonth);
            bundleArguments.putInt("Year", mYear);
            bundleArguments.putBoolean("EndTime", mStartDateTimePickerDialog);
            dateTimePickerDialog.setArguments(bundleArguments);
            dateTimePickerDialog.show(getSupportFragmentManager(), "EndTimePickerDialogFragment");
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void createEvent() {
        String title = mTitleEditText.getText().toString();
        String description = mDescriptionEditText.getText().toString();

        Event event = new Event(title, mStartTime, mEndTime, description);

        //запись события в json-файл (в новом потоке) - DataReceiver
        //поставить метку о событии в календаре
    }

    private void showAlertDialogCancel() {

    }

    @Override
    public void onReturnSelectedDate(long date, boolean startDate) {
        Log.e("LogDateTimePickerDialog", date + " ");
        Log.e("LogDateTimePickerDialog", startDate + " ");

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(date * 1000L);
        String dateString = DateFormat.format("dd.MM.yyyy HH:mm", calendar).toString();

        if (startDate) {
            mStartTime = date;
            mDateStartTextView.setText(dateString);
            TextView startTextView = findViewById(R.id.activity_event_create_start_time_title);
            startTextView.setTextColor(getColor(R.color.colorFont));
        } else {
            mEndTime = date;
            mDateEndTextView.setText(dateString);
            TextView endTextView = findViewById(R.id.activity_event_create_end_time_title);
            endTextView.setTextColor(getColor(R.color.colorFont));
        }
        if (!mEventDoneImageButton.isEnabled()) {
            setEnablerEventDoneImageButton();
        }
    }

    private void setEnablerEventDoneImageButton() {
        mEventDoneImageButton.setImageResource(R.drawable.ic_active_toolbar_event_done);
        mEventDoneImageButton.setEnabled(true);
    }
}
