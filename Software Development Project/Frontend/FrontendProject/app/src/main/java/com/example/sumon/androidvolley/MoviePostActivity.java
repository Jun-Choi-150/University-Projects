package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
/**
 * @author sabrinaf
 */
public class MoviePostActivity extends Activity implements View.OnClickListener {

    private String TAG = MoviePostActivity.class.getSimpleName();
    private String tag_json_obj = "jobj_req";
    private NetworkImageView movieImage;
    private TextView movieTitle;
    private Button cancel;
    private Button post;
    private String ImageUrl;
    private ImageButton star1;
    private ImageButton star2;
    private ImageButton star3;
    private ImageButton star4;
    private ImageButton star5;
    private int rating;
    private EditText review;
    private String title;
    private String date;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_post);
        movieImage = (NetworkImageView) findViewById(R.id.postImage);
        movieTitle = (TextView) findViewById(R.id.PostTitle);
        cancel = (Button) findViewById(R.id.cancelButton);
        post = (Button) findViewById(R.id.postButton);
        star1 = (ImageButton) findViewById(R.id.postStar1);
        star2 = (ImageButton) findViewById(R.id.postStar2);
        star3 = (ImageButton) findViewById(R.id.postStar3);
        star4 = (ImageButton) findViewById(R.id.postStar4);
        star5 = (ImageButton) findViewById(R.id.postStar5);
        review = (EditText) findViewById(R.id.inputBox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDateTime.now().toString();
        }


        rating = 1;


        cancel.setOnClickListener(this);
        post.setOnClickListener(this);
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);

        makeJsonObjReq();

    }

    public void makeJsonObjReq() {
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String url = Const.URL_SINGLE_MOVIE + "/" + (SearchActivity.currentMovie);
        //showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            ImageUrl = "http://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");
                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                            movieImage.setImageUrl(ImageUrl, imageLoader);

                            movieTitle.setText(jsonObject.getString("title"));


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        title = movieTitle.getText().toString();

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);

    }


    private void makeJsonObjPostReq() {


        JSONObject jsonObject = new JSONObject();
        try {
                jsonObject.put("contents", review.getText().toString());
                jsonObject.put("rating", rating);
                jsonObject.put("date", date);
                jsonObject.put("title", title);
//                jsonObject.put("movie_id", SearchActivity.currentMovie);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Const.URL_JSON_USER+"/"+StringRequestActivity.currUser+"/create/post/movie/"+SearchActivity.currentMovie, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(MoviePostActivity.this, MoviePageActivity.class));

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        //Volley.newRequestQueue(getApplicationContext()).add(req);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
        //AppController.getInstance().addToRequestQueue(req,
        // tag_json_arry);

        // Cancelling request
        //AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.cancelButton:
                startActivity(new Intent(MoviePostActivity.this,
                        MoviePageActivity.class));
                break;
            case R.id.postStar1:
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_off);
                star3.setImageResource(android.R.drawable.btn_star_big_off);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                rating = 1;
                break;
            case R.id.postStar2:
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_off);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                rating = 2;
                break;
            case R.id.postStar3:
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_off);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                rating = 3;
                break;
            case R.id.postStar4:
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_on);
                star5.setImageResource(android.R.drawable.btn_star_big_off);
                rating = 4;
                break;
            case R.id.postStar5:
                star1.setImageResource(android.R.drawable.btn_star_big_on);
                star2.setImageResource(android.R.drawable.btn_star_big_on);
                star3.setImageResource(android.R.drawable.btn_star_big_on);
                star4.setImageResource(android.R.drawable.btn_star_big_on);
                star5.setImageResource(android.R.drawable.btn_star_big_on);
                rating = 5;
                break;
            case R.id.postButton:
                if(!review.getText().toString().equals("")) {
                    makeJsonObjPostReq();
                } else {
                    Toast.makeText(getApplicationContext(), "required field empty", Toast.LENGTH_SHORT).show();
                }
        }
    }
}