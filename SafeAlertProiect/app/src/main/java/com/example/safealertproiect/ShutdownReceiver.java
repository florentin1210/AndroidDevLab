package com.example.safealertproiect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class ShutdownReceiver extends BroadcastReceiver {

    private static final String[] FAVORITE_CONTACTS = {"0732156500", "0732156500", "0732156500", "0732156500", "0732156500"};

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            sendFinalLocation(context);
        }
    }

    private void sendFinalLocation(Context context) {
        String locationMessage = "My last known location is: Latitude: 40.7128, Longitude: -74.0060";
        for (String contact : FAVORITE_CONTACTS) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contact, null, locationMessage, null, null);
        }
    }
}
