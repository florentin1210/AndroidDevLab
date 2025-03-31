package com.example.safealertproiect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.location.Location;

public class SOSActivity extends AppCompatActivity {

    private static final String EMERGENCY_CONTACT = "0732156500";
    private static final int DELAY_TIME = 10000; // 10 secunde
    private Handler handler = new Handler();
    private boolean isSOSStopped = false;
    private static final String[] FAVORITE_CONTACTS = {
            "0732156500", "0732156500", "0732156500", "0732156500", "0732156500"
    };

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        TextView sosMessage = findViewById(R.id.sos_message);
        sosMessage.setText("Emergency Alert Triggered! Help is on the way!");

        Toast.makeText(this, "SOS Activity Started", Toast.LENGTH_LONG).show();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isSOSStopped) {
                    sendEmergencySMS();
                    makeEmergencyCall();
                }
            }
        }, DELAY_TIME);

        Button stopSOSButton = findViewById(R.id.stop_sos_button);
        stopSOSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSOS();
            }
        });
    }

    private void sendEmergencySMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Location location = task.getResult();
                                if (location != null) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    String message = "Emergency! Please help! I'm in danger. My location: " +
                                            "Latitude: " + latitude + ", Longitude: " + longitude;

                                    for (String contact : FAVORITE_CONTACTS) {
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(contact, null, message, null, null);
                                        Toast.makeText(SOSActivity.this, "SMS sent to: " + contact, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(SOSActivity.this, "Unable to retrieve location.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Location permission is not granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeEmergencyCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            initiateCall();
        }
    }

    private void initiateCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + EMERGENCY_CONTACT));

        try {
            startActivity(callIntent);
            Toast.makeText(this, "Calling Emergency Contact: " + EMERGENCY_CONTACT, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to make a call", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void stopSOS() {
        handler.removeCallbacksAndMessages(null);
        isSOSStopped = true;

        Toast.makeText(this, "SOS Alert Stopped", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateCall();
            } else {
                Toast.makeText(this, "Permission denied to make calls", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
