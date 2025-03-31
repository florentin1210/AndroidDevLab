package com.example.truerideshare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PlanCommuteActivity extends AppCompatActivity {

    private TextView dateTextView;
    private TextView timeTextView;
    private Button dateButton;
    private Button timeButton;
    private EditText maxSeatsEditText;
    private EditText occupiedSeatsEditText;
    private EditText destinationEditText;
    private Button addCommuteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plancommute);

        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        maxSeatsEditText = findViewById(R.id.maxSeatsEditText);
        occupiedSeatsEditText = findViewById(R.id.occupiedSeatsEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        addCommuteButton = findViewById(R.id.addCommuteButton);

        dateButton.setOnClickListener(v -> openDatePicker());
        timeButton.setOnClickListener(v -> openTimePicker());
        addCommuteButton.setOnClickListener(v -> addCommute());

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            dateTextView.setText("Selected Date: " + selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    boolean isPM = selectedHour >= 12;
                    String amPm = isPM ? "PM" : "AM";
                    String formattedTime = String.format("%02d:%02d %s", selectedHour % 12 == 0 ? 12 : selectedHour % 12, selectedMinute, amPm);
                    timeTextView.setText("Selected Time: " + formattedTime);
                },
                hour,
                minute,
                false
        );

        timePickerDialog.show();
    }

    private void addCommute() {
        String date = dateTextView.getText().toString().replace("Selected Date: ", "").trim();
        String time = timeTextView.getText().toString().replace("Selected Time: ", "").trim();
        String destination = destinationEditText.getText().toString().trim();
        String maxSeatsText = maxSeatsEditText.getText().toString().trim();
        String occupiedSeatsText = occupiedSeatsEditText.getText().toString().trim();

        if (date.isEmpty() || time.isEmpty() || destination.isEmpty() || maxSeatsText.isEmpty() || occupiedSeatsText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int maxSeats;
        int occupiedSeats;
        try {
            maxSeats = Integer.parseInt(maxSeatsText);
            occupiedSeats = Integer.parseInt(occupiedSeatsText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number for seats", Toast.LENGTH_SHORT).show();
            return;
        }

        if (occupiedSeats > maxSeats) {
            Toast.makeText(this, "Occupied seats cannot exceed max seats", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = "moldovanflorentin"; // Replace this with logged-in user's email
        String message = "add-commute-" + date + "|" + time + "|Poienita|" + destination + "|" + maxSeats + "|" + occupiedSeats + "|" + email;

        NetworkUtils.sendToServer(message, new NetworkUtils.NetworkCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> Toast.makeText(PlanCommuteActivity.this, "Commute added successfully!", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(PlanCommuteActivity.this, "Failed to add commute: " + error, Toast.LENGTH_LONG).show());
            }
        });
    }
}
