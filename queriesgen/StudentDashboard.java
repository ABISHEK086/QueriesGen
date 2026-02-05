package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;  
import androidx.activity.EdgeToEdge; 
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
 
public class StudentDashboard extends AppCompatActivity {

    private CardView generateQuestionCard, myQuestionsCard;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); 
        setContentView(R.layout.activity_student_dashboard);

        generateQuestionCard = findViewById(R.id.generate_question_card);
        myQuestionsCard = findViewById(R.id.my_questions_card);

        generateQuestionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDashboard.this, GenerateQuestion.class);
                startActivity(intent);
            }
        });

        myQuestionsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDashboard.this, MyQuestions.class);
                startActivity(intent);
            }
        });
    }
}
