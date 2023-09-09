package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubtractorActivity extends AppCompatActivity {

    Button increaseButton;
    Button backButton;

    Button subtractButton;
    TextView num;

    Button increaseButton2;
    TextView num2;

    int counter = 0;
    int counter2 = 0;


    TextView subtracted;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtractor);

        increaseButton = findViewById(R.id.increaseButton);
        subtractButton = findViewById(R.id.SubtractButton);
        backButton = findViewById(R.id.backButton);
        num = findViewById(R.id.num);

        increaseButton2 = findViewById(R.id.increaseButton2);
        num2 = findViewById(R.id.num2);
        subtracted = findViewById(R.id.subtracted);



        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                num.setText(String.valueOf(++counter));
            }
        });

        increaseButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                num2.setText(String.valueOf(++counter2));
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { subtracted.setText(String.valueOf(counter-counter2)); }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SubtractorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
