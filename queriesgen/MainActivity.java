package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.queriesgen.R;

public class MainActivity extends AppCompatActivity {

    private CardView studentCard, staffCard, adminCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize CardViews
        studentCard = findViewById(R.id.d1);
        staffCard = findViewById(R.id.d2);
        adminCard = findViewById(R.id.d3);

        // Set onClickListeners
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to StudentActivity
                Intent intent = new Intent(MainActivity.this, LoginStudent.class);
                startActivity(intent);
            }
        });

        staffCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to StaffActivity
                Intent intent = new Intent(MainActivity.this, LoginStaff.class);
                startActivity(intent);
            }
        });

        adminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdminActivity
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
            }
        });
    }
}
