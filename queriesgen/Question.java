package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question extends AppCompatActivity {

    private String selectedSubject = null, selectedDifficulty = null; // Default to null

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        // Initialize Views
        Spinner subjectSpinner = findViewById(R.id.subject_spinner);
        Spinner difficultySpinner = findViewById(R.id.difficulty_spinner);
        Button createQuestionButton = findViewById(R.id.create_question_button);

        // Subject List
        String[] subjects = {"Select Subject", "Tamil", "English", "Maths", "Science", "Social"};
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);
        subjectSpinner.setAdapter(subjectAdapter);

        // Difficulty Levels
        String[] difficultyLevels = {"Select Difficulty", "Low", "Mid", "Advanced"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficultyLevels);
        difficultySpinner.setAdapter(difficultyAdapter);

        // Subject Selection
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Avoid "Select Subject"
                    selectedSubject = subjects[position];
                } else {
                    selectedSubject = null; // Reset if default is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Difficulty Selection
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Avoid "Select Difficulty"
                    selectedDifficulty = difficultyLevels[position];
                } else {
                    selectedDifficulty = null; // Reset if default is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Button Click Event
        createQuestionButton.setOnClickListener(view -> {
            if (selectedSubject == null || selectedDifficulty == null) {
                Toast.makeText(Question.this, "Please select both Subject and Difficulty Level", Toast.LENGTH_SHORT).show();
            } else {
                // Pass the selected data to CreatedQuestion activity
                Intent intent = new Intent(Question.this, CreatedQuestion.class);
                intent.putExtra("subject", selectedSubject);
                intent.putExtra("difficulty", selectedDifficulty);
                startActivity(intent);
            }
        });
    }
}
