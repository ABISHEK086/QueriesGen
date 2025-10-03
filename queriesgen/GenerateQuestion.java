package com.example.queriesgen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class GenerateQuestion extends AppCompatActivity {

    private Spinner subjectSpinner;
    private TextView pdfStatusText;
    private Button openPdfButton, generateButton;
    private String selectedSubject;
    private String pdfFilePath;
    private String selectedPdfFileName; 

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_question);

        subjectSpinner = findViewById(R.id.subject_spinner);
        pdfStatusText = findViewById(R.id.pdf_status_text);
        openPdfButton = findViewById(R.id.open_pdf_button);
        generateButton = findViewById(R.id.generate_button);

        // Set up Spinner
        String[] subjects = {"Tamil", "English", "Maths", "Science", "Social"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);
        subjectSpinner.setAdapter(adapter);

        // Handle Subject Selection
        subjectSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjects[position].toLowerCase(); // Convert to lowercase for consistency
                checkPdfAvailability(selectedSubject);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                pdfStatusText.setText("");
                openPdfButton.setVisibility(View.GONE);
            }
        });

        // Open PDF Button Click
        openPdfButton.setOnClickListener(view -> openPDF());

        // Generate Button Click - Save PDF in MyQuestions
        generateButton.setOnClickListener(view -> {
            if (selectedPdfFileName != null) {
                savePdfToMyQuestions(selectedPdfFileName);
                Toast.makeText(GenerateQuestion.this, "PDF saved to My Questions", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GenerateQuestion.this, "No PDF to save!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPdfAvailability(String subject) {
        String[] difficulties = {"low", "mid", "advanced"};
        boolean pdfExists = false;

        for (String difficulty : difficulties) {
            String fileName = subject + "-" + difficulty + ".pdf";
            pdfFilePath = getFilesDir().getAbsolutePath() + "/" + fileName;
            File pdfFile = new File(pdfFilePath);

            if (pdfFile.exists()) {
                pdfStatusText.setText("PDF Available: " + fileName);
                openPdfButton.setVisibility(View.VISIBLE);
                selectedPdfFileName = fileName; // Store only file name
                pdfExists = true;
                break;  // Stop checking if at least one PDF exists
            }
        }

        if (!pdfExists) {
            pdfStatusText.setText("No question papers created yet");
            openPdfButton.setVisibility(View.GONE);
            selectedPdfFileName = null;
        }
    }

    private void openPDF() {
        try {
            File pdfFile = new File(getFilesDir(), selectedPdfFileName);
            Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void savePdfToMyQuestions(String fileName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyQuestionsPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> pdfSet = sharedPreferences.getStringSet("my_questions", new HashSet<>());
        pdfSet.add(fileName);  // Save only the file name (e.g., "tamil-low.pdf")

        editor.putStringSet("my_questions", pdfSet);
        editor.apply();
    }
}
