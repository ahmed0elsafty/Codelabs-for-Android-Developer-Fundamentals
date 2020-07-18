package com.elsafty.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.processDatePickerResult(year, month, day);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
}