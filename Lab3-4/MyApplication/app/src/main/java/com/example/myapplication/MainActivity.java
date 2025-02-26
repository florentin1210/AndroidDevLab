package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button ex1btn = findViewById(R.id.ex1Button);
        Button ex2btn = findViewById(R.id.ex2Button);
        Button ex3Btn = findViewById(R.id.ex3Button);

        ex1btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ex1.class);
            startActivity(intent);
        });

        ex2btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ex2.class);
            startActivity(intent);
        });

        ex3Btn.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ex3.class);
            startActivity(intent);
        });
    }
}
