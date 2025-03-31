package com.example.lab9_10ex3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String[][] board = new String[3][3];
    private boolean isXTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
        setupGridListeners();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
    }

    private void setupGridListeners() {
        int[][] buttonIds = {
                {R.id.button_00, R.id.button_01, R.id.button_02},
                {R.id.button_10, R.id.button_11, R.id.button_12},
                {R.id.button_20, R.id.button_21, R.id.button_22}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int row = i;
                int col = j;

                Button button = findViewById(buttonIds[i][j]);
                button.setOnClickListener(v -> onCellClicked(row, col, button));
            }
        }
    }

    private void onCellClicked(int row, int col, Button button) {
        if (board[row][col] != null) {
            Toast.makeText(this, "Cell is already occupied!", Toast.LENGTH_SHORT).show();
            return;
        }

        board[row][col] = isXTurn ? "X" : "O";
        button.setText(board[row][col]);

        if (checkWinner()) {
            String winner = isXTurn ? "X" : "O";
            Toast.makeText(this, winner + " Wins!", Toast.LENGTH_LONG).show();
            resetGame();
            return;
        }

        isXTurn = !isXTurn;
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                return true;
            }
        }

        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return true;
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        initializeBoard();
        isXTurn = true;


        int[][] buttonIds = {
                {R.id.button_00, R.id.button_01, R.id.button_02},
                {R.id.button_10, R.id.button_11, R.id.button_12},
                {R.id.button_20, R.id.button_21, R.id.button_22}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = findViewById(buttonIds[i][j]);
                button.setText("");
            }
        }
    }
}
