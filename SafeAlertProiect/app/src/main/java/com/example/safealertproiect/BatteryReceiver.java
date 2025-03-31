package com.example.safealertproiect;

import static com.example.safealertproiect.MainActivity.FAVORITE_CONTACTS;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            int level = intent.getIntExtra("level", -1);
            if (level <= 10) {
                sendLowBatterySMS(context);
                showPowerSavingTips(context);
                sendFinalLocation(context);
            }
        }
    }

    private void sendLowBatterySMS(Context context) {
        String message = "Warning! My battery is below 10%. Please contact me immediately.";
        for (String contact : FAVORITE_CONTACTS) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contact, null, message, null, null);
            Toast.makeText(context, "Low battery SMS sent to: " + contact, Toast.LENGTH_SHORT).show();
        }
    }

    private void showPowerSavingTips(Context context) {
        String powerSavingTips = "Power Saving Tips: \n1. Turn off Wi-Fi and Bluetooth\n2. Use Battery Saver Mode\n3. Reduce screen brightness";
        Toast.makeText(context, powerSavingTips, Toast.LENGTH_LONG).show();
    }

    private void sendFinalLocation(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = getLastKnownLocation(context);
            if (location != null) {
                String locationMessage = "My last known location is: Latitude: " + location.getLatitude() +
                        ", Longitude: " + location.getLongitude();
                for (String contact : FAVORITE_CONTACTS) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact, null, locationMessage, null, null);
                    Toast.makeText(context, "Location sent to: " + contact, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Unable to retrieve location.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Location permission is not granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private Location getLastKnownLocation(final Context context) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> locationTask = fusedLocationClient.getLastLocation();

            locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();
                        String locationMessage = "My last known location is: Latitude: " + location.getLatitude() +
                                ", Longitude: " + location.getLongitude();
                        for (String contact : FAVORITE_CONTACTS) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(contact, null, locationMessage, null, null);
                        }
                    } else {
                        Toast.makeText(context, "Unable to get the location.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return null;
        } else {
            Toast.makeText(context, "Location permission is not granted.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
