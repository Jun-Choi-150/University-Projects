package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
 * the FriendPageActivity contains a JSON object request
 * to the information of the user logged in to be displayed on
 * the corresponding page
 * @author sabrinaf
 */
public class FriendPageActivity extends AppCompatActivity implements View.OnClickListener{

    public static String FTAG = FriendPageActivity.class.getSimpleName();


    private static Context contextF;

    private TextView Pusername2;
    private NetworkImageView imgNetWorkView;
    private String Ptag_json_obj = "jobj_req";
    private String Iurl;
    private NetworkImageView image;

    private ListView list;
    private ArrayList<String> titles;
    private AccountListViewAdapter adapter;
    private int postSize;
    private int[] reviews;
    private ArrayList<int[]> stars;
    private ImageButton back;
    private Button unfollow;
    private ArrayList<String> postContent;


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
        setContentView(R.layout.activity_friend_page);
        FriendPageActivity.contextF = getApplicationContext();
        Pusername2 = (TextView) findViewById(R.id.Uusername);
        Iurl = Const.URL_IMAGE + "/" + StringRequestActivity.currUser + "/friends";
        image = (NetworkImageView) findViewById(R.id.UimageView);
        titles = new ArrayList<>();
        stars = new ArrayList<>();
        postContent = new ArrayList<String>();
        back = (ImageButton) findViewById(R.id.backButton2);
        unfollow = (Button) findViewById(R.id.friendFollowButton);
        unfollow.setOnClickListener(this);
        postSize = 0;
        back.setOnClickListener(this);


        makeJsonObjReq();


    }



    /**
     * Making JSON object request to get current user's information
     */
    public void makeJsonObjReq() {
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String url = Const.URL_JSON_USER + "/" + StringRequestActivity.currUser + "/get/" + FriendListActivity.currentFriend;
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


                        list = (ListView) findViewById(R.id.UlistviewAccount);
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
                        adapter = new AccountListViewAdapter(FriendPageActivity.getContext(),Iurl, username, titles, stars, postContent);
                        list.setAdapter(adapter);



                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, Ptag_json_obj);

    }

    public static Context getContext() {
        return contextF;
    }


    /**
     * handles button clicks of this page
     * @param v The view that was clicked.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backButton2:
                startActivity(new Intent(FriendPageActivity.this,
                        FriendListActivity.class));
                break;
            case R.id.friendFollowButton:
                if(unfollow.getText().toString().toLowerCase().equals("unfollow")) {
                    FollowUnfollow.unfollow(StringRequestActivity.currUser, FriendListActivity.currentFriend);
                    unfollow.setText("follow");
                } else {
                    FollowUnfollow.follow(StringRequestActivity.currUser, FriendListActivity.currentFriend);
                    unfollow.setText("unfollow");
                }
                break;
        }
    }
}