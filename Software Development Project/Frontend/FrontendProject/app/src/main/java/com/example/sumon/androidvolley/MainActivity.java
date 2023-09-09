package com.example.sumon.androidvolley;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author sabrinaf
 * the MainActivity class controls the landing page of the app
 */
public class MainActivity extends Activity implements OnClickListener {
    private Button btnString;
    private Button signUp;

    /**
     * this method connects the activity_main page layout to the class
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnString = (Button) findViewById(R.id.btnStringRequest);
        signUp = (Button) findViewById(R.id.button2);


        // button click listeners
        btnString.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    /**
     * this method handles all of the button clicks for
     * the corresponding page layout
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStringRequest:
                startActivity(new Intent(MainActivity.this,
                        StringRequestActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this,
                        SignUpActivity.class));
                break;
            default:
                break;
        }
    }

}
