package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityAdminLoginBinding;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminLoginActivity extends AppCompatActivity {

//    public AdminLoginActivity(Context context){
//
//    }
    public boolean validateAdmin(String user, String password) {
        if (user.equals("admin") && password.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }


    public String validate(String user, String password) {
        if (user.equals("admin") && password.equals("admin")) {
            return "Login was succesful";
        } else {
            return "Invalid login";
        }
    }

    public String validate_email(String userName){
        String regex = "^[a-zA-Z0-9_][a-zA-Z0-9_]+@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(userName);

        if (matcher.matches())
            return "Valid Username";
        else
            return "Invalid Username";
    }

    ActivityAdminLoginBinding adminLogin;

    String role = "Admin";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminLogin = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(adminLogin.getRoot());


        adminLogin.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = adminLogin.loginUsername.getText().toString();
                String password = adminLogin.loginPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(AdminLoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean valid = validateAdmin(username, password);
                    if (valid) {
                        Toast.makeText(AdminLoginActivity.this, "Login was successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdminLoginActivity.this, "Login attempt failed. Password or Username incorrect", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }

}
