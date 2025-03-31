package com.example.lab5_6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int nr1,nr2;

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

        TextView nr1text = findViewById(R.id.editTextNumber1);
        TextView nr2text = findViewById(R.id.editTextNumber2);
        TextView result = findViewById(R.id.resultTextNumber);

        Button addBtn = findViewById(R.id.addButton);
        Button subBtn = findViewById(R.id.subButton);
        Button divBtn = findViewById(R.id.divButton);
        Button mulBtn = findViewById(R.id.mulButton);

        addBtn.setOnClickListener(v->{
            nr1 = Integer.parseInt(nr1text.getText().toString());
            nr2 = Integer.parseInt(nr2text.getText().toString());
            result.setText(String.valueOf(nr1+nr2));
        });

        subBtn.setOnClickListener(v->{
            nr1 = Integer.parseInt(nr1text.getText().toString());
            nr2 = Integer.parseInt(nr2text.getText().toString());
            result.setText(String.valueOf(nr1-nr2));
        });

        divBtn.setOnClickListener(v -> {
            nr1 = Integer.parseInt(nr1text.getText().toString());
            nr2 = Integer.parseInt(nr2text.getText().toString());
            result.setText(String.valueOf(nr1/nr2));
        });

        mulBtn.setOnClickListener(v->{
            nr1 = Integer.parseInt(nr1text.getText().toString());
            nr2 = Integer.parseInt(nr2text.getText().toString());
            result.setText(String.valueOf(nr1*nr2));
        });

    }
}