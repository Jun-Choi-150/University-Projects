package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author sabrinaf
 * the SearchActivty class connects the activity_search page layout
 * and makes a JSON array request to get and display the movie names
 * for a movie search page
 */
public class SearchActivity extends Activity implements View.OnClickListener, SearchView.OnQueryTextListener{


    private ListView list;
    private static Context context;


    private ListViewAdapter adapter;
    SearchView editsearch;
    String[] MovieNameList = null;
    ArrayList<MovieNames> arraylist = new ArrayList<MovieNames>();
    private String tag_json_arry = "jarray_req";
    private String TAG = SearchActivity.class.getSimpleName();
    public static int currentMovie;
    private ImageButton home;
    private ImageButton personalAccount;

    public static int size = 0;
    int[] MovieIdList = null;


    /**
     * connects this class to the activity_search page layout along with
     * all of its elements
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = (ListView) findViewById(R.id.listview);
        SearchActivity.context = getApplicationContext();
        list.setClickable(true);
        currentMovie = -1;

        personalAccount = (ImageButton) findViewById(R.id.SpersonalAccountButton);
        home = (ImageButton) findViewById(R.id.SimageButton2);
        home.setOnClickListener(this);
        personalAccount.setOnClickListener(this);


        // Generate sample data
        makeJsonArrayReq();
        editsearch = (SearchView) findViewById(R.id.searchView);


        editsearch.setOnQueryTextListener(this);



    }

    /**
     * makes a JSON array request to get the array of all
     * movie title names in the database
     * @return an array of the string value of all of the movie titles
     */
    public void makeJsonArrayReq() {
//        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_MOVIE,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response.toString());
                            MovieNameList = new String[jsonArray.length()];
                            MovieIdList = new int[jsonArray.length()];
                            size = jsonArray.length();

                            for(int i = 0; i < size; i++) {
                                MovieNameList[i] = jsonArray.getJSONObject(i).getString("title");
                                MovieIdList[i] = jsonArray.getJSONObject(i).getInt("id");
//                                Toast.makeText(getApplicationContext(), MovieListJ[i], Toast.LENGTH_SHORT).show();

                            }
                            for (int i = 0; i < MovieNameList.length; i++) {
                                MovieNames MovieNames = new MovieNames(MovieNameList[i], MovieIdList[i]);
                                // Binds all strings into an array
                                arraylist.add(MovieNames);
                            }





                        } catch(JSONException e) {
                            e.printStackTrace();
                        }


                        // Pass results to ListViewAdapter Class
                        adapter = new ListViewAdapter(context, arraylist);

                        // Binds the Adapter to the ListView
                        list.setAdapter(adapter);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                currentMovie = adapter.getItem(position).getMovieId();
                                LoginActivity.homeOrSearch = 0;
                                startActivity(new Intent(SearchActivity.this,
                                        MoviePageActivity.class));

                            }
                        });



//                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        });

            // Locate the EditText in listview_main.xml


        AppController.getInstance().addToRequestQueue(req,tag_json_arry);


    }

    /**
     * deals with the search bar text submissions
     * @param query the query text that is to be submitted
     *
     * @return boolean value of if there was a submission or not
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    /**
     * deals with any text change in the search bar
     * @param newText the new content of the query text field.
     *
     * @return the boolean value of if text has changed or not
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    /**
     * deals with any button clicks on the activity_search page
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.SimageButton2:
                startActivity(new Intent(SearchActivity.this,
                        LoginActivity.class));
                break;
            case R.id.SpersonalAccountButton:
                startActivity(new Intent(SearchActivity.this,
                        PersonalAccountActivity.class));
                break;
        }
    }
}