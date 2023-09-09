package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    Button b1, b2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b1= (Button) findViewById(R.id.ButtonMessage);
        b2= (Button) findViewById(R.id.ButtonNext);

        b1.setOnClickListener(new view.onClickListener(){
            @override
            public void onClick(View view){
                Toast.makeText(applicationContext(), text: "Hello Android". Toast.LENGTH_LONG.show();
            }
        });

        Button yourButton = (Button) findViewById(R.id.your_buttons_id);

        yourButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(YourCurrentActivity.this, YourNewActivity.class));
            }
        });
    }
}