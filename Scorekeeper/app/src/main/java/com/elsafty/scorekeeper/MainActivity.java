package com.elsafty.scorekeeper;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class MainActivity extends AppCompatActivity {
    private static final String SCORE_1 = "score-1";
    private static final String SCORE_2 = "score-2";
    private int mScore_1;
    private int mScore_2;
    private TextView mScore_1TextView;
    private TextView mScore_2TextView;
    private StateListDrawable mStateListDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore_1TextView = findViewById(R.id.score_1);
        mScore_2TextView = findViewById(R.id.score_2);

        if (savedInstanceState != null) {
            mScore_1 = savedInstanceState.getInt(SCORE_1);
            mScore_2 = savedInstanceState.getInt(SCORE_2);
        }
        mScore_1TextView.setText(String.valueOf(mScore_1));
        mScore_2TextView.setText(String.valueOf(mScore_2));

        mStateListDrawable = (StateListDrawable) getDrawable(R.drawable.button_states);

    }



    public void increaseScore(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.increaseTeam1:
                mScore_1 += 1;
                mScore_1TextView.setText(String.valueOf(mScore_1));
                break;
            case R.id.increaseTeam2:
                mScore_2 += 1;
                mScore_2TextView.setText(String.valueOf(mScore_2));
                break;
        }
    }

    public void decreaseScore(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.decreaseTeam1:
                if (mScore_1 == 0) return;
                mScore_1 -= 1;
                mScore_1TextView.setText(String.valueOf(mScore_1));
                break;
            case R.id.decreaseTeam2:
                if (mScore_2 == 0) return;
                mScore_2 -= 1;
                mScore_2TextView.setText(String.valueOf(mScore_2));
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SCORE_1, mScore_1);
        outState.putInt(SCORE_2, mScore_2);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (item.getItemId() == R.id.night_mode) {
            if ((nightMode == AppCompatDelegate.MODE_NIGHT_YES)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return true;
    }
}