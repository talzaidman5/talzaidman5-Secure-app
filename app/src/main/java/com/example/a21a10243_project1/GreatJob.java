package com.example.a21a10243_project1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GreatJob extends AppCompatActivity {

    private Button activity_great_job_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_great_job);
        getSupportActionBar().hide();
        activity_great_job_BTN_back = findViewById(R.id.activity_great_job_BTN_back);
        activity_great_job_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GreatJob.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
