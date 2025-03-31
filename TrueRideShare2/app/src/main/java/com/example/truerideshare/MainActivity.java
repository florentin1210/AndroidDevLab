package com.example.truerideshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeText = findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("firstName")) {
            String firstName = intent.getStringExtra("firstName");
            String location = intent.getStringExtra("location");
            welcomeText.setText(String.format("Welcome %s from %s!", firstName, location));
        }

        Button btnPlanCommute = findViewById(R.id.btnPlanCommute);
        Button btnYourCommutes = findViewById(R.id.btnYourCommutes);

        btnPlanCommute.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PlanCommuteActivity.class));
        });

        btnYourCommutes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, YourCommutesActivity.class));
        });
    }
}