package com.example.truerideshare;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private static final String SERVER_IP = "10.0.2.2";
    private static final int SERVER_PORT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText lastNameField = findViewById(R.id.signupLastName);
        EditText firstNameField = findViewById(R.id.signupFirstName);
        EditText locationField = findViewById(R.id.signupLocation);
        EditText passwordField = findViewById(R.id.signupPassword);
        Button signupButton = findViewById(R.id.signupSubmitButton);

        signupButton.setOnClickListener(v -> {
            String lastName = lastNameField.getText().toString().trim();
            String firstName = firstNameField.getText().toString().trim();
            String location = locationField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (lastName.isEmpty() || firstName.isEmpty() ||
                    location.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> attemptSignup(lastName, firstName, location, password)).start();
        });
    }

    private void attemptSignup(String lastName, String firstName, String location, String password) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            // Format: signup-lastName|firstName|location|password
            String message = "signup-" + lastName + "|" + firstName + "|" + location + "|" + password + "\n";

            OutputStream out = socket.getOutputStream();
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
            Log.d(TAG, "Sent: " + message.trim());

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String response = reader.readLine();
            Log.d(TAG, "Received: " + response);

            runOnUiThread(() -> {
                if (response != null && response.startsWith("signup-ok")) {
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_LONG).show();
                    finish(); // Return to login
                } else {
                    String error = response != null ? response.substring(response.indexOf('-') + 1) : "Unknown error";
                    Toast.makeText(this, "Signup failed: " + error, Toast.LENGTH_LONG).show();
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "Error: ", e);
            runOnUiThread(() ->
                    Toast.makeText(this, "Connection error", Toast.LENGTH_LONG).show());
        }
    }
}
