package com.elsafty.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE = "message";
    private String mSendMessage;

    private TextView donutTextView,iceCreamTextView,froyoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        donutTextView = findViewById(R.id.donut_description);
        iceCreamTextView= findViewById(R.id.iceCream_description);
        froyoTextView = findViewById(R.id.froyo_description);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
                orderIntent.putExtra(MESSAGE,mSendMessage);
                startActivity(orderIntent);
            }
        });
        registerForContextMenu(donutTextView);
        registerForContextMenu(iceCreamTextView);
        registerForContextMenu(froyoTextView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(donutTextView);
        unregisterForContextMenu(iceCreamTextView);
        unregisterForContextMenu(froyoTextView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                displayToast(getString(R.string.action_share));
                return true;
            case R.id.action_edit:
                displayToast(getString(R.string.action_edit));
                return true;
            case R.id.action_delete:
                displayToast(getString(R.string.action_delete));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_order:
                Intent intent= new Intent(this,OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_status:
                displayToast(getString(R.string.action_status_message));
                return true;
            case R.id.action_favourite:
                displayToast(getString(R.string.action_favorites_message));
                return true;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showFroyoOrder(View view) {
        mSendMessage = getString(R.string.froyo_order_message);
        displayToast(mSendMessage);
    }

    public void showIceCreamOrder(View view) {
        mSendMessage = getString(R.string.ice_cream_order_message);
        displayToast(mSendMessage);
    }

    public void showDonutOrder(View view) {
        mSendMessage=getString(R.string.donut_order_message);
        displayToast(mSendMessage);
    }
}