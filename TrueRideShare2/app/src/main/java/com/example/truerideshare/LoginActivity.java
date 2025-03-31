package com.example.truerideshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String SERVER_IP = "10.0.2.2"; // 10.0.2.2 for emulator
    private static final int SERVER_PORT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText lastNameField = findViewById(R.id.lastNameEditText);
        EditText firstNameField = findViewById(R.id.firstNameEditText);
        EditText passwordField = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        lastNameField.setText("Moldovan");
        firstNameField.setText("Florentin");
        passwordField.setText("test123");

        loginButton.setOnClickListener(v -> {
            String lastName = lastNameField.getText().toString().trim();
            String firstName = firstNameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (lastName.isEmpty() || firstName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                boolean loginSuccess = attemptLogin(lastName, firstName, password);
                runOnUiThread(() -> {
                    if (loginSuccess) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }).start();
        });

        signUpButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

    }

    private boolean attemptLogin(String lastName, String firstName, String password) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            String message = "login-" + lastName + "|" + firstName + "|" + password + "\n";

            OutputStream out = socket.getOutputStream();
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
            Log.d(TAG, "Sent: " + message.trim());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String response = reader.readLine();
            Log.d(TAG, "Received: " + response);

            if (response != null && response.startsWith("login-ok")) {
                String[] parts = response.split("\\|");

                if (parts.length >= 3) {
                    String welcome = "Welcome " + parts[1] + parts[2];
                    runOnUiThread(() -> Toast.makeText(this, welcome, Toast.LENGTH_LONG).show());

                    storeUserData(firstName, lastName, password);

                    return true;
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Invalid response format", Toast.LENGTH_LONG).show());
                    return false;
                }
            } else {
                String error = response != null ?
                        response.substring(response.indexOf('-') + 1) : "Connection error";
                runOnUiThread(() ->
                        Toast.makeText(this, "Login failed: " + error, Toast.LENGTH_LONG).show());
                return false;
            }

        } catch (IOException e) {
            Log.e(TAG, "Error: ", e);
            runOnUiThread(() ->
                    Toast.makeText(this, "Connection error", Toast.LENGTH_LONG).show());
            return false;
        }
    }

    private void storeUserData(String firstName, String lastName, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("password", password);
        editor.apply();
    }
}
