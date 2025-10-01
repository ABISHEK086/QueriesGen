package com.example.queriesgen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CreatedQuestion extends AppCompatActivity {

    private TextView subjectText, difficultyText, pdfOpenText;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_question);
        subjectText = findViewById(R.id.subject_text);
        difficultyText = findViewById(R.id.difficulty_text);
        pdfOpenText = findViewById(R.id.pdf_open_text); 
        String selectedSubject = getIntent().getStringExtra("subject");
        String selectedDifficulty = getIntent().getStringExtra("difficulty");
        if (selectedSubject != null && selectedDifficulty != null) {
            subjectText.setText("Subject: " + selectedSubject);
            difficultyText.setText("Difficulty: " + selectedDifficulty);

          
            fileName = selectedSubject.toLowerCase() + "-" + selectedDifficulty.toLowerCase() + ".pdf";

            savePdfToInternalStorage(fileName);

            pdfOpenText.setText("Click to Open " + fileName);

            pdfOpenText.setOnClickListener(view -> openPDF(fileName));
        } else {
            subjectText.setText("No subject selected");
            difficultyText.setText("No difficulty selected");
            pdfOpenText.setText("No PDF available");
        }
    }

    private void savePdfToInternalStorage(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            if (!file.exists()) {
                InputStream inputStream = getAssets().open(fileName);
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPDF(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);

            Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

            // Open the PDF using an Intent
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
