package com.elsafty.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ShowAlert(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click OK to continue, or Cancel to stop:");
        builder.setTitle("ALERT");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Continued", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void ShowDate(View view) {
        DialogFragment dialogFragment = new DateFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String yearString = Integer.toString(year);
        String monthString = Integer.toString(month + 1);
        String dayString = Integer.toString(day);
        String dateMessage = dayString + "/" + monthString + "/" + yearString;
        Toast.makeText(this, "Date: " + dateMessage, Toast.LENGTH_LONG).show();
    }

    public void processTimePickerResult(int hours,int minutes){
        String hourString = Integer.toString(hours);
        String minuteString = Integer.toString(minutes);
        String TimeMessage = hourString + ":"+minuteString;
        Toast.makeText(this,TimeMessage,Toast.LENGTH_LONG).show();
    }

    public void ShowTime(View view) {
        DialogFragment dialogFragment = new TimeFragment();
        dialogFragment.show(getSupportFragmentManager(),"timePicker");
    }
}