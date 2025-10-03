package com.example.queriesgen;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class Generate extends AppCompatActivity {

    private Spinner subjectSpinner;
    private TextView pdfStatusText;
    private Button openPdfButton;
    private String selectedSubject;
    private String pdfFilePath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        // Initiali
        subjectSpinner = findViewById(R.id.subject_spinner);
        pdfStatusText = findViewById(R.id.pdf_status_text);
        openPdfButton = findViewById(R.id.open_pdf_button);

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
    }

    private void checkPdfAvailability(String subject) {
        String[] difficulties = {"low", "mid", "advanced"};
        boolean pdfExists = false;

        for (String difficulty : difficulties) {
            pdfFilePath = getFilesDir().getAbsolutePath() + "/" + subject + "-" + difficulty + ".pdf";
            File pdfFile = new File(pdfFilePath);
            if (pdfFile.exists()) {
                pdfStatusText.setText("PDF Available: " + subject + "-" + difficulty + ".pdf");
                openPdfButton.setVisibility(View.VISIBLE);
                pdfExists = true;
                break;  // Stop checking if at least one PDF exists
            }
        }

        if (!pdfExists) {
            pdfStatusText.setText("No question papers created yet");
            openPdfButton.setVisibility(View.GONE);
        }
    }

    private void openPDF() {
        try {
            File pdfFile = new File(pdfFilePath);
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
}
