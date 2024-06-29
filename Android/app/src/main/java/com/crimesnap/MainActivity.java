package com.crimesnap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        // Set click listeners for each button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button1 click
                Toast.makeText(MainActivity.this, "Report Crime button clicked", Toast.LENGTH_SHORT).show();
                // Add your logic for handling this button's action
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button2 click
                Toast.makeText(MainActivity.this, "View Reported Crime button clicked", Toast.LENGTH_SHORT).show();
                // Add your logic for handling this button's action
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button3 click
                Toast.makeText(MainActivity.this, "Crime Ratio button clicked", Toast.LENGTH_SHORT).show();
                // Add your logic for handling this button's action
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button4 click
                Toast.makeText(MainActivity.this, "Sign Out button clicked", Toast.LENGTH_SHORT).show();
                // Add your logic for handling this button's action
            }
        });
    }
}
