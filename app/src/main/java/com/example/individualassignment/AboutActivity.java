package com.example.individualassignment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutText = findViewById(R.id.aboutText);
        aboutText.setText("Name: Umar Haqeem Bin Kasmadi \n" +
                "Student ID: 2024902873\n" +
                "Course: ICT602 / Mobile App Development\n" +
                "GitHub Link : https://github.com/UmrHqeem/IndividualAssignmentElectricityBill.git");

        aboutText.setMovementMethod(LinkMovementMethod.getInstance()); // Make link clickable
    }
}