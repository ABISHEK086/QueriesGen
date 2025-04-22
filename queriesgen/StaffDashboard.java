package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StaffDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_dashboard);

        // Handling window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializing CardView components
        CardView addStudentCard = findViewById(R.id.add_student_card);
        CardView generateque = findViewById(R.id.generate_question_card);
        CardView viewStudentsCard = findViewById(R.id.view_students_card);
        CardView removeStudentCard = findViewById(R.id.remove_student_card);
        CardView createQuestionCard = findViewById(R.id.create_question_card);

        // Setting click listeners
        addStudentCard.setOnClickListener(view -> {
            Toast.makeText(StaffDashboard.this, "Add Student Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffDashboard.this, AddStudent.class));
        });

        generateque.setOnClickListener(view -> {
            Toast.makeText(StaffDashboard.this, "Generate Question Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffDashboard.this, Generate.class));
        });

        viewStudentsCard.setOnClickListener(view -> {
            Toast.makeText(StaffDashboard.this, "View Students Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffDashboard.this, ViewStudent.class));
        });

        removeStudentCard.setOnClickListener(view -> {
            Toast.makeText(StaffDashboard.this, "Remove Student Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffDashboard.this, RemoveStudent.class));
        });

        // Create Question Card Click
        createQuestionCard.setOnClickListener(view -> {
            Toast.makeText(StaffDashboard.this, "Create Question Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StaffDashboard.this, Question.class));

        });
    }
}
