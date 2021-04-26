package com.example.diary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateTimePickerDialog extends DialogFragment {

    public interface DateTimePickerDialogListener {
        void onReturnSelectedDate(long date, boolean startDate);
    }

    private static String LOG_DATE_TIME_PICKER_DIALOG = "LogDateTimePickerDialog";

    private boolean mStartDate;
    private long mStartTimeStamp;
    private long mEndTimeStamp;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_date_time_picker, null);
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(getActivity());
        builderAlertDialog.setView(dialogView);

        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = dialogView.findViewById(R.id.dialog_fragment_date_picker);
        TimePicker timePicker = dialogView.findViewById(R.id.dialog_fragment_time_picker);

        Bundle bundleArguments = getArguments();
        if (bundleArguments != null) {
            datePicker.updateDate(getArguments().getInt("Year"),
                    getArguments().getInt("Month"),
                    getArguments().getInt("DayOfMonth"));
        } else {
            Log.e(LOG_DATE_TIME_PICKER_DIALOG, "bundleArguments is null");
        }

        timePicker.setIs24HourView(true);
        timePicker.setMinute(0);

        mStartDate = bundleArguments.getBoolean("StartTime", false);

        //на будущее - время окончания события не должно быть раньше времени начала события
        //ставить по умолчанию +1 час
        if (mStartDate) {
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        } else {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            timePicker.setHour(hour + 1);
        }

        //OK
        dialogView.findViewById(R.id.dialog_fragment_date_time_set).setOnClickListener(v -> {
            if (mStartDate) {
                mStartTimeStamp = DateTimePickerDialog.this.getTimeStump(calendar, datePicker, timePicker);
                Log.d(LOG_DATE_TIME_PICKER_DIALOG, "mStartTimeStamp - " + mStartTimeStamp);

                DateTimePickerDialogListener dateTimePickerDialogListener = (DateTimePickerDialogListener) getActivity();
                dateTimePickerDialogListener.onReturnSelectedDate(mStartTimeStamp, true);
            } else {
                mEndTimeStamp = DateTimePickerDialog.this.getTimeStump(calendar, datePicker, timePicker);
                Log.d(LOG_DATE_TIME_PICKER_DIALOG, "mEndTimeStamp - " + mEndTimeStamp);

                DateTimePickerDialogListener dateTimePickerDialogListener = (DateTimePickerDialogListener) getActivity();
                dateTimePickerDialogListener.onReturnSelectedDate(mEndTimeStamp, false);
            }
            Dialog dialog = DateTimePickerDialog.this.getDialog();
            DateTimePickerDialog.this.dismissDialog(dialog);
        });

        //CANCEL
        dialogView.findViewById(R.id.dialog_fragment_date_time_cancel).setOnClickListener(v -> {
            Dialog dialog = getDialog();
            dismissDialog(dialog);

        });
        return builderAlertDialog.create();
    }

    private long getTimeStump(Calendar calendar, DatePicker datePicker, TimePicker timePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis() / 1000L;
    }

    private void dismissDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
