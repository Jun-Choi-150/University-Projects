package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
/**
 * @author sabrinaf
 */
public class MoviePageActivity extends Activity implements View.OnClickListener{

    private String TAG = MoviePageActivity.class.getSimpleName();
    private String tag_json_obj = "jobj_req";
    private NetworkImageView movieImage;
    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieDescription;
    private Button moviePost;
    private String ImageUrl;
    private ImageButton backB;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        movieImage = (NetworkImageView) findViewById(R.id.MovieImage);
        movieTitle = (TextView) findViewById(R.id.MovieTitle);
        movieDate = (TextView) findViewById(R.id.MovieReleaseDate);
        movieDescription = (TextView) findViewById(R.id.MovieOverview);
        moviePost = (Button) findViewById(R.id.createPostButton);
        backB = (ImageButton) findViewById(R.id.movieBackButton);

        backB.setOnClickListener(this);
        moviePost.setOnClickListener(this);

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
                        movieDate.setText(jsonObject.getString("release_date"));
                        movieDescription.setText(jsonObject.getString("overview"));





                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movieBackButton:
                SearchActivity.currentMovie = -1;
                if(LoginActivity.homeOrSearch == 0) {
                    startActivity(new Intent(MoviePageActivity.this,
                            SearchActivity.class));
                } else {
                    startActivity(new Intent(MoviePageActivity.this,
                            LoginActivity.class));
                }
                break;
            case R.id.createPostButton:
                startActivity(new Intent(MoviePageActivity.this,
                        MoviePostActivity.class));
                break;
        }
    }
}