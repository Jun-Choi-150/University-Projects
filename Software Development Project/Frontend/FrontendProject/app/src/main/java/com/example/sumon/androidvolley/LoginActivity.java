package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * the LoginActivity class is started once a user
 * logs in and is for the home page
 * @author sabrinaf
 */
public class LoginActivity extends Activity implements View.OnClickListener, MovieListViewAdapter.ItemClickListener {

    private String LTAG = LoginActivity.class.getSimpleName();
    private String tag_json_arry = "jarray_req";
    public static View.OnClickListener onClickListener;
    private Button backButton;

    private ImageButton personalAccountButton;
    private ImageButton search;
    private Button userSearch;
    private ListView list;
    private MovieListViewAdapter adapterHorror;
    private MovieListViewAdapter adapterComedy;
    private MovieListViewAdapter adapterThriller;
    private static Context movContext;
    private RecyclerView recyclerViewHorror;
    private RecyclerView recyclerViewComedy;
    private RecyclerView recyclerViewThriller;
    public static int homeOrSearch;

    /**
     * this method connects the activity_login page layout to the class
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginActivity.movContext = getApplicationContext();
        backButton = (Button) findViewById(R.id.button);
        personalAccountButton = (ImageButton) findViewById(R.id.personalAccountButton);
        search = (ImageButton) findViewById(R.id.imageButton3);
        userSearch = (Button) findViewById(R.id.userSearch);
        recyclerViewHorror = (RecyclerView) findViewById(R.id.rvMoviesHorror);
        recyclerViewComedy = (RecyclerView) findViewById(R.id.rvMoviesComedy);
        recyclerViewThriller = (RecyclerView) findViewById(R.id.rvMoviesThriller);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(LoginActivity.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManager2
                = new LinearLayoutManager(LoginActivity.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManager3
                = new LinearLayoutManager(LoginActivity.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewHorror.setLayoutManager(horizontalLayoutManager);
        recyclerViewComedy.setLayoutManager(horizontalLayoutManager2);
        recyclerViewThriller.setLayoutManager(horizontalLayoutManager3);

        // button click listeners
        userSearch.setOnClickListener(this);
        backButton.setOnClickListener(this);
        personalAccountButton.setOnClickListener(this);
        search.setOnClickListener(this);



        makeJsonArrayReq();
    }


    public static Context getContext() {
        return movContext;
    }

    public void makeJsonArrayReq() {
//        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_MOVIE,
                new Response.Listener<JSONArray>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONArray response) {
                        List<MovieNames> moviesHorror = new ArrayList<>();
                        List<MovieNames> moviesComedy = new ArrayList<>();
                        List<MovieNames> moviesThriller = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());




                            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                            for(int i = 0; i < jsonArray.length(); i++) {
                                int[] genres = new int[jsonArray.getJSONObject(i).getJSONArray("genre_ids").length()];
                                for(int k = 0; k < genres.length; k++) {
                                    genres[k] = jsonArray.getJSONObject(i).getJSONArray("genre_ids").getInt(k);
                                }

                                for(int j = 0; j < genres.length; j++) {
                                    if(genres[j] == 27) {
                                        moviesHorror.add(new MovieNames("http://image.tmdb.org/t/p/w500" + jsonArray.getJSONObject(i).getString("poster_path"),jsonArray.getJSONObject(i).getInt("id")));
                                    }
                                    if(genres[j] == 12) {
                                        moviesComedy.add(new MovieNames("http://image.tmdb.org/t/p/w500" + jsonArray.getJSONObject(i).getString("poster_path"),jsonArray.getJSONObject(i).getInt("id")));
                                    }
                                    if(genres[j] == 53) {
                                        moviesThriller.add(new MovieNames("http://image.tmdb.org/t/p/w500" + jsonArray.getJSONObject(i).getString("poster_path"),jsonArray.getJSONObject(i).getInt("id")));
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        adapterHorror = new MovieListViewAdapter(LoginActivity.getContext(), moviesHorror);
                        recyclerViewHorror.setAdapter(adapterHorror);
                        adapterHorror.setClickListener(new MovieListViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                homeOrSearch = 1;
                                SearchActivity.currentMovie = moviesHorror.get(position).getMovieId();
                                startActivity(new Intent(LoginActivity.this,
                                        MoviePageActivity.class));
                            }
                        });
                        adapterComedy = new MovieListViewAdapter(LoginActivity.getContext(), moviesComedy);
                        recyclerViewComedy.setAdapter(adapterComedy);
                        adapterComedy.setClickListener(new MovieListViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                homeOrSearch = 1;
                                SearchActivity.currentMovie = moviesComedy.get(position).getMovieId();
                                startActivity(new Intent(LoginActivity.this,
                                        MoviePageActivity.class));
                            }
                        });
                        adapterThriller = new MovieListViewAdapter(LoginActivity.getContext(), moviesThriller);
                        recyclerViewThriller.setAdapter(adapterThriller);
                        adapterThriller.setClickListener(new MovieListViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                homeOrSearch = 1;
                                SearchActivity.currentMovie = moviesThriller.get(position).getMovieId();
                                startActivity(new Intent(LoginActivity.this,
                                        MoviePageActivity.class));
                            }
                        });

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(LTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

    }

    /**
     * this method handles all of the button clicks for
     * the corresponding page layout
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                StringRequestActivity.currUser = -1;
                startActivity(new Intent(LoginActivity.this,
                        MainActivity.class));
                break;
            case R.id.personalAccountButton:
                startActivity(new Intent(LoginActivity.this,
                        PersonalAccountActivity.class));
                break;
            case R.id.imageButton3 :
                startActivity(new Intent(LoginActivity.this,
                        SearchActivity.class));
                break;
            case R.id.userSearch :
                startActivity(new Intent(LoginActivity.this,
                        UserSearchActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}