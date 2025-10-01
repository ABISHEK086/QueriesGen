package com.example.queriesgen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bill), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView addAdminCard = findViewById(R.id.addAdminCard);
        CardView addStaffCard = findViewById(R.id.addStaffCard);
        CardView viewAdminCard = findViewById(R.id.viewAdminCard);
        CardView viewStaffCard = findViewById(R.id.viewStaffCard);
        CardView manageAdminCard = findViewById(R.id.manageAdminCard);
        CardView manageStaffCard = findViewById(R.id.manageStaffCard);

        addAdminCard.setOnClickListener(view -> openActivity(AddAdmin.class));
        addStaffCard.setOnClickListener(view -> openActivity(AddStaff.class));
        viewAdminCard.setOnClickListener(view -> openActivity(ViewAdmin.class));
        viewStaffCard.setOnClickListener(view -> openActivity(ViewStaff.class));
        manageAdminCard.setOnClickListener(view -> openActivity(ManageAdmin.class));
        manageStaffCard.setOnClickListener(view -> openActivity(ManageStaff.class));
    }
    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(AdminDashboard.this, activityClass);
        startActivity(intent);
    }
}
