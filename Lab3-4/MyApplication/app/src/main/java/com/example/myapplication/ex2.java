package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class ex2 extends AppCompatActivity {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ex2);

        View rootView = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button count = findViewById(R.id.countButton);
        Button toast = findViewById(R.id.toastButton);
        TextView counterText = findViewById(R.id.editTextNumber2);
        counterText.setText(String.valueOf(counter));

        count.setOnClickListener(v->{
            counter++;
            counterText.setText(String.valueOf(counter));
        });

        toast.setOnClickListener(v->{
            Toast.makeText(getBaseContext(), "Toast!", Toast.LENGTH_SHORT).show();
        });

    }
}
