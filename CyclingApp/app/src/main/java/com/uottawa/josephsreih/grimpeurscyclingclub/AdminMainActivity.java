package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {

    ActivityAdminMainBinding adminMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminMain = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(adminMain.getRoot());


        adminMain.editEventTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, EditEventTypesActivity.class);
                startActivity(intent);

            }
        });
        adminMain.deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, DeleteUsersActivity.class);
                startActivity(intent);

            }
        });
    }
}