package com.example.queriesgen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyQuestions extends AppCompatActivity {

    private ListView pdfListView;
    private TextView noPdfText;
    private SharedPreferences sharedPreferences;
    private List<String> myPdfs;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);

        pdfListView = findViewById(R.id.pdf_list_view);
        noPdfText = findViewById(R.id.no_pdfs_text);

        sharedPreferences = getSharedPreferences("MyQuestionsPrefs", Context.MODE_PRIVATE);
        loadSavedPdfs();
    }

    private void loadSavedPdfs() {
        Set<String> pdfSet = sharedPreferences.getStringSet("my_questions", new HashSet<>());
        myPdfs = new ArrayList<>(pdfSet);

        if (myPdfs.isEmpty()) {
            noPdfText.setVisibility(View.VISIBLE);
            pdfListView.setVisibility(View.GONE);
        } else {
            noPdfText.setVisibility(View.GONE);
            pdfListView.setVisibility(View.VISIBLE);

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myPdfs);
            pdfListView.setAdapter(adapter);

            // Open PDF on item click
            pdfListView.setOnItemClickListener((parent, view, position, id) -> openPdf(myPdfs.get(position)));

            // Delete PDF on long press
            pdfListView.setOnItemLongClickListener((parent, view, position, id) -> {
                showDeleteDialog(position);
                return true;
            });
        }
    }

    private void openPdf(String fileName) {
        File pdfFile = new File(getFilesDir(), fileName); // Reconstruct file path

        if (!pdfFile.exists()) {
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void showDeleteDialog(int position) {
        String pdfName = myPdfs.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Delete PDF")
                .setMessage("Do you want to delete " + pdfName + "?")
                .setPositiveButton("Delete", (dialog, which) -> deletePdf(position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deletePdf(int position) {
        String pdfName = myPdfs.get(position);
        File pdfFile = new File(getFilesDir(), pdfName);

        if (pdfFile.exists() && pdfFile.delete()) {
            myPdfs.remove(position);
            adapter.notifyDataSetChanged();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> pdfSet = new HashSet<>(myPdfs);
            editor.putStringSet("my_questions", pdfSet);
            editor.apply();

            Toast.makeText(this, "PDF deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
