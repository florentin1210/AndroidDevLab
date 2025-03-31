package com.example.truerideshare;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NetworkUtils {
    private static final String SERVER_IP = "10.0.2.2"; // Use your server IP (10.0.2.2 for Android emulator)
    private static final int SERVER_PORT = 8000;
    private static final String TAG = "NetworkUtils";

    public interface NetworkCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public static void sendToServer(final String message, final NetworkCallback callback) {
        new AsyncTask<Void, Void, String>() {
            private Exception exception;

            @Override
            protected String doInBackground(Void... voids) {
                Socket socket = null;
                BufferedWriter writer = null;
                BufferedReader reader = null;

                try {
                    // 1. Connect to server
                    socket = new Socket(SERVER_IP, SERVER_PORT);

                    // 2. Send message
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(message + "\n");
                    writer.flush();

                    // 3. Read response
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    return reader.readLine();

                } catch (IOException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                    exception = e;
                    return null;
                } finally {
                    try {
                        if (writer != null) writer.close();
                        if (reader != null) reader.close();
                        if (socket != null) socket.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing resources: " + e.getMessage());
                    }
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (exception != null || response == null) {
                    callback.onFailure("Connection failed");
                } else {
                    callback.onSuccess(response);
                }
            }
        }.execute();
    }
}