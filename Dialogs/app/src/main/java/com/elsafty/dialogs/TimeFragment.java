package com.elsafty.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;

import java.util.Calendar;


public class TimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.processTimePickerResult(hours,minutes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hours,minute,false);
    }
}