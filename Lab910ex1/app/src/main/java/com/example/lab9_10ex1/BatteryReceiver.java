package com.example.lab9_10ex1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level >= 0 && scale > 0) {
                int batteryPct = (int) ((level / (float) scale) * 100);
                Toast.makeText(context, "Battery Level: " + batteryPct + "%", Toast.LENGTH_SHORT).show();
            }
        } else if (Intent.ACTION_BATTERY_LOW.equals(action)) {
            Toast.makeText(context, "Battery is Low!", Toast.LENGTH_SHORT).show();
        }
    }
}

