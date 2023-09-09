package com.example.sumon.androidvolley;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The MovieActivity class is the movie page which displays relevent info about a specific movie
 * @author Haris Khan
 */
public class MovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static TextView movieTitle;
    public static TextView movieYear;
    public static TextView movieOverallRating;
    public static TextView movieGenre;
    public static TextView movieActor;
    public static TextView movieDirector;

    private Button makePost;

    private Button back;

   // public static ImageView moviePoster;

    /**
    * contructs the layout for this class
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieTitle = (TextView) findViewById(R.id.textView4);
        movieYear = (TextView) findViewById(R.id.textView2);
        movieOverallRating = (TextView) findViewById(R.id.textView5);
        movieGenre = (TextView) findViewById(R.id.textView6);
        movieActor = (TextView) findViewById(R.id.textView7);
        movieDirector = (TextView) findViewById(R.id.textView8);

        back = (Button) findViewById(R.id.button9);
        makePost = (Button) findViewById(R.id.button8);
        makePost.setOnClickListener(this);
        back.setOnClickListener(this);
        //image = (NetworkImageView) findViewById(R.id.UimageView);

    }
    /**
     *
     *
     */
    private void makeJsonObjReq() {


    }

    /**
     * handles button clicks for this page, if the user presses the "back" button it will go to the
     * previous page or if the user presses "post" it will go to the PostActivity page
     * @param v the view clicked
     *
     */
    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.button8:
                startActivity(new Intent(PostActivity.this,
                        PostActivity.class));

                break;
            case R.id.button9:
                startActivity(new Intent(PostActivity.this,
                        MainActivity.class));
                break;
            default:
                break;
        }
        */
    }

}
