package com.example.truerideshare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YourCommutesActivity extends AppCompatActivity {

    private RecyclerView commuteRecyclerView;
    private CommuteAdapter commuteAdapter;
    private List<Commute> commuteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourcommutes);

        commuteRecyclerView = findViewById(R.id.commuteRecyclerView);
        commuteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        commuteList = new ArrayList<>();
        commuteAdapter = new CommuteAdapter(commuteList);
        commuteRecyclerView.setAdapter(commuteAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String firstName = sharedPreferences.getString("firstName", "");
        String lastName = sharedPreferences.getString("lastName", "");

        getCommutesFromServer(firstName, lastName);
    }

    private void getCommutesFromServer(String firstName, String lastName) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("10.0.2.2", 8000);
                OutputStream out = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                List<Commute> commutes = new ArrayList<>();
                boolean hasMoreCommutes = true;

                while (hasMoreCommutes) {
                    String requestMessage = "get-commutes-" + firstName + "|" + lastName + "\n";
                    out.write(requestMessage.getBytes(StandardCharsets.UTF_8));
                    out.flush();

                    String response = reader.readLine();

                    if (response != null && response.startsWith("get-commute-ok")) {
                        Log.d("YourCommutesActivity", "Response: " + response);

                        String commuteData = response.substring(16);
                        String[] parts = commuteData.split("\\|");

                        if (parts.length == 6) {
                            String date = parts[0];
                            String time = parts[1];
                            String departure = parts[2];
                            String destination = parts[3];
                            int maxSeats = Integer.parseInt(parts[4]);
                            int occupiedSeats = Integer.parseInt(parts[5]);

                            commutes.add(new Commute(date, time, maxSeats, occupiedSeats, destination, departure));
                        }

                    } else if (response != null && response.startsWith("get-commutes-err")) {
                        hasMoreCommutes = false;
                    }
                }

                runOnUiThread(() -> updateCommuteList(commutes));

            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(YourCommutesActivity.this, "Connection error", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }




    private void updateCommuteList(List<Commute> commutes) {
        commuteList.clear();
        commuteList.addAll(commutes);
        commuteAdapter.notifyDataSetChanged();
    }
}
