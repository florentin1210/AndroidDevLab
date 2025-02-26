package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;


public class ex3 extends AppCompatActivity {
    Boolean lightBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ex3);

        View rootView = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button lightBtn = findViewById(R.id.lightButton);
        Switch autoSwch = findViewById(R.id.autoSwitch);

        lightBtn.setOnClickListener(v->{
            if(!lightBool) {
                rootView.setBackgroundColor(Color.YELLOW);
                lightBool = true;
            }
            else {
                lightBool = false;
                rootView.setBackgroundColor(Color.DKGRAY);
            }
        });
        autoSwch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ex3.this, "Switch on!", Toast.LENGTH_SHORT).show();
                    updateLight(rootView);
                } else {
                    Toast.makeText(ex3.this, "Switch off!", Toast.LENGTH_SHORT).show();
                    rootView.setBackgroundColor(Color.DKGRAY);
                }
            }
        });
    }
    private void updateLight(View rootView) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        int backgroundColor;
        if (hourOfDay >= 7 && hourOfDay < 18) {
            backgroundColor = Color.WHITE;
        } else if (hourOfDay >= 18 && hourOfDay < 22) {
            backgroundColor = Color.parseColor("#FFA500");
        } else {
            backgroundColor = Color.parseColor("#333333");
        }

        rootView.setBackgroundColor(backgroundColor);
    }
}
