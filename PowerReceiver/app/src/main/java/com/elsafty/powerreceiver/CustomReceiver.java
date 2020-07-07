package com.elsafty.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {

        String toastMessage;
        String stringAction = intent.getAction();
        String message = intent.getStringExtra(ACTION_CUSTOM_BROADCAST);
        if (stringAction == null) {
            return;
        }
        switch (stringAction) {
            case Intent.ACTION_POWER_CONNECTED:
                toastMessage = "ACTION POWER CONNECTED";
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                toastMessage = "ACTION POWER DISCONNECTED";
                break;
            case ACTION_CUSTOM_BROADCAST:
                toastMessage = "ACTION CUSTOM BROADCAST\n" + message;
                break;
            case Intent.ACTION_HEADSET_PLUG:
                toastMessage = "ACTION HEADSET PLUG";
                break;
            default:
                toastMessage = context.getString(R.string.unknown_action);
        }
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();

    }
}
