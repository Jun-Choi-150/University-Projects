package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class   MainActivity extends AppCompatActivity {
    Button b1, b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    b1 = (Button) findViewById(R.id.ButtonMessage);
    b2 = (Button) findViewById(R.id.ButtonNext);

        b1.setOnClickListener(new view.onClickListener(){
        @override
        public void onClick(View view){
            Toast.makeText(applicationContext(), text: "Hello Android". Toast.LENGTH_LONG.show();
        }
    });

    Button yourButton = (Button) findViewById(R.id.ButtonPage);

        yourButton.setOnClickListener(new OnClickListener(){
        public void onClick(View v){
            startActivity(new Intent(YourCurrentActivity.this, YourNewActivity.class));
        }
    });
}