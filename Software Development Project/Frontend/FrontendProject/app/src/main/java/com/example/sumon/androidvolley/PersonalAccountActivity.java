package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author sabrinaf
 * the PersonalAccountActivity class corresponds with the
 * personal account page layout and makes a JSON request
 * for the specific user's account information
 */
public class PersonalAccountActivity extends AppCompatActivity implements View.OnClickListener{

    public static String PTAG = PersonalAccountActivity.class.getSimpleName();
    public static ProgressDialog PpDialog;
    private static Context context;

    public static TextView Pusername2;
    public static NetworkImageView imgNetWorkView;
    public static String Ptag_json_obj = "jobj_req";
    public static String Iurl;
    public static NetworkImageView image;
    public static TableLayout table;
    public static Button FriendList;
    private ImageButton home;
    private ImageButton search;
    private ListView list;
    private ArrayList<String> titles;
    private ArrayList<String> postContent;

    private AccountListViewAdapter adapter;
    public static int postSize;
    private int[] reviews;
    private ArrayList<int[]> stars;


    /**
     * This method is setting the class to the user's page and calling the
     * JSON request
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);
        PersonalAccountActivity.context = getApplicationContext();
        Pusername2 = (TextView) findViewById(R.id.username);
        Iurl = Const.URL_IMAGE + "/" + StringRequestActivity.currUser;
        image = (NetworkImageView) findViewById(R.id.imageView);
        FriendList = (Button) findViewById(R.id.friendsList);
        titles = new ArrayList<>();
        stars = new ArrayList<>();
        postContent = new ArrayList<String>();
        postSize = 0;
        search = (ImageButton) findViewById(R.id.PimageButton3);
        home = (ImageButton) findViewById(R.id.PimageButton2);
        home.setOnClickListener(this);
        search.setOnClickListener(this);


        FriendList.setOnClickListener(this);


        makeJsonObjReq();





    }



    /**
     * Making JSON object request to get current user's information
     */
    public void makeJsonObjReq() {
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String url = Const.URL_JSON_USER + "/" + StringRequestActivity.currUser;
        //showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONObject response) {

                        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                        String username = null;
                        JSONArray posts = null;


                        list = (ListView) findViewById(R.id.listviewAccount);
                        try {

                            username = response.getString("username");
                            Iurl = response.getString("profilePic");
                            image.setImageUrl(Iurl,imageLoader);


                            posts = response.getJSONArray("post");
                            for(int i = 0; i < posts.length(); i++) {
                                titles.add(posts.getJSONObject(i).getString("title"));
                            }

                            reviews = new int[posts.length()];
                            for(int i = 0; i < posts.length(); i++) {
                                reviews[i] = posts.getJSONObject(i).getInt("rating");
                            }
                            for(int i = 0; i < posts.length(); i++) {
                                postContent.add(posts.getJSONObject(i).getString("contents"));
                            }

                            for(int i = 0; i < reviews.length; i++) {
                                if(reviews[i] == 1) {
                                    stars.add(new int[]{android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off});
                                } else if(reviews[i] == 2) {
                                    stars.add(new int[]{android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off});
                                }
                                else if(reviews[i] == 3) {
                                    stars.add(new int[]{android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_off, android.R.drawable.btn_star_big_off});

                                }
                                else if(reviews[i] == 4) {
                                    stars.add(new int[]{android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_off});

                                }
                                else if(reviews[i] == 5) {
                                    stars.add(new int[]{android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on, android.R.drawable.btn_star_big_on});
                                }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Pusername2.setText(username);
                        adapter = new AccountListViewAdapter(PersonalAccountActivity.getContext(),Iurl, username, titles, stars, postContent);
                        list.setAdapter(adapter);



                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(PTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, Ptag_json_obj);

    }

    public static Context getContext() {
        return context;
    }

    /**
     * handles all of the clicks for the corresponding page layout
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friendsList :
                startActivity(new Intent(PersonalAccountActivity.this,
                        FriendListActivity.class));
                break;
            case R.id.PimageButton2 :
                startActivity(new Intent(PersonalAccountActivity.this,
                        LoginActivity.class));
                break;
            case R.id.PimageButton3 :
                startActivity(new Intent(PersonalAccountActivity.this,
                        SearchActivity.class));
                break;
        }

    }



}