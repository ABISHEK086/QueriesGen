package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Front extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        Button getStartedButton = findViewById(R.id.bottom_button);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SecondActivity
                Intent intent = new Intent(Front.this, MainActivity.class);
                startActivity(intent); // Start the activity
            }
        });
    }
}
