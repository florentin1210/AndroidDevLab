package com.example.lab9_10ex4;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, surnameInput, scoreInput;
    private ListView participantListView;
    private ArrayList<Participant> participants = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> participantDisplayList = new ArrayList<>();
    private int selectedParticipantIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        surnameInput = findViewById(R.id.surnameInput);
        scoreInput = findViewById(R.id.scoreInput);
        participantListView = findViewById(R.id.participantListView);

        Button addButton = findViewById(R.id.addButton);
        Button filterButton = findViewById(R.id.filterButton);
        Button sortByNameButton = findViewById(R.id.sortByNameButton);
        Button sortByScoreButton = findViewById(R.id.sortByScoreButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participantDisplayList);
        participantListView.setAdapter(adapter);

        addButton.setOnClickListener(v -> addParticipant());
        filterButton.setOnClickListener(v -> filterParticipants());
        sortByNameButton.setOnClickListener(v -> sortParticipantsByName());
        sortByScoreButton.setOnClickListener(v -> sortParticipantsByScore());

        registerForContextMenu(participantListView);
    }

    private void addParticipant() {
        String name = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String scoreText = scoreInput.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || scoreText.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int score = Integer.parseInt(scoreText);
        if (score < 0 || score > 100) {
            Toast.makeText(this, "Score must be between 0 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        participants.add(new Participant(name, surname, score));
        updateDisplayList();
        clearInputs();
    }

    private void filterParticipants() {
        String filterText = scoreInput.getText().toString().trim();
        if (filterText.isEmpty()) {
            Toast.makeText(this, "Enter a score to filter by", Toast.LENGTH_SHORT).show();
            return;
        }

        int scoreThreshold = Integer.parseInt(filterText);
        participantDisplayList.clear();
        for (Participant participant : participants) {
            if (participant.getScore() < scoreThreshold) {
                participantDisplayList.add(formatParticipant(participant));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void sortParticipantsByName() {
        Collections.sort(participants, Comparator.comparing(Participant::getName));
        updateDisplayList();
    }

    private void sortParticipantsByScore() {
        Collections.sort(participants, Comparator.comparingInt(Participant::getScore));
        updateDisplayList();
    }

    private void updateDisplayList() {
        participantDisplayList.clear();
        for (Participant participant : participants) {
            participantDisplayList.add(formatParticipant(participant));
        }
        adapter.notifyDataSetChanged();
    }

    private String formatParticipant(Participant participant) {
        return participant.getName() + " " + participant.getSurname() + " - " + participant.getScore();
    }

    private void clearInputs() {
        nameInput.setText("");
        surnameInput.setText("");
        scoreInput.setText("");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.participantListView) {
            menu.setHeaderTitle("Choose an action");
            menu.add(0, v.getId(), 0, "Update");
            menu.add(0, v.getId(), 1, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedParticipantIndex = info.position;

        if (item.getTitle().equals("Update")) {
            updateParticipant();
        } else if (item.getTitle().equals("Delete")) {
            deleteParticipant();
        }

        return true;
    }

    private void updateParticipant() {
        if (selectedParticipantIndex >= 0 && selectedParticipantIndex < participants.size()) {
            Participant participant = participants.get(selectedParticipantIndex);

            nameInput.setText(participant.getName());
            surnameInput.setText(participant.getSurname());
            scoreInput.setText(String.valueOf(participant.getScore()));

            Toast.makeText(this, "Edit fields and click Add Participant to save changes", Toast.LENGTH_SHORT).show();

            participants.remove(selectedParticipantIndex);
            updateDisplayList();
        }
    }

    private void deleteParticipant() {
        if (selectedParticipantIndex >= 0 && selectedParticipantIndex < participants.size()) {
            participants.remove(selectedParticipantIndex);
            updateDisplayList();
            Toast.makeText(this, "Participant deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
