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
public class UserPageActivity extends AppCompatActivity implements View.OnClickListener{

    public static String USERTAG = UserPageActivity.class.getSimpleName();


    private static Context contextU;

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
    private Button follow;
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
        setContentView(R.layout.activity_user_page);
        UserPageActivity.contextU = getApplicationContext();
        Pusername2 = (TextView) findViewById(R.id.UserUsername);
        Iurl = Const.URL_IMAGE + "/" + StringRequestActivity.currUser + "/friends";
        image = (NetworkImageView) findViewById(R.id.UserImageView);
        follow = (Button) findViewById(R.id.FollowButton);
        postContent = new ArrayList<String>();
        titles = new ArrayList<>();
        stars = new ArrayList<>();
        back = (ImageButton) findViewById(R.id.UserBackButton2);
        postSize = 0;
        back.setOnClickListener(this);
        follow.setOnClickListener(this);



        makeJsonObjReq();


    }



    /**
     * Making JSON object request to get current user's information
     */
    public void makeJsonObjReq() {
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String url = Const.URL_JSON_USER + "/" + UserSearchActivity.currentFriendId;
        //showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint({"ResourceType", "SetTextI18n"})
                    @Override
                    public void onResponse(JSONObject response) {

                        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                        String username = null;
                        JSONArray posts = null;


                        list = (ListView) findViewById(R.id.UserListviewAccount);
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

                            if(StringRequestActivity.currUser == UserSearchActivity.currentFriendId) {
                                follow.setVisibility(View.GONE);
                            }
                            int amountOfFriends = response.getJSONArray("friends").length();
                            for(int i = 0; i < amountOfFriends; i++) {
                                if(StringRequestActivity.currUser == response.getJSONArray("friends").getJSONObject(i).getInt("id")) {
                                    follow.setText("unfollow");
                                }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Pusername2.setText(username);
                        adapter = new AccountListViewAdapter(UserPageActivity.getContext(),Iurl, username, titles, stars, postContent);
                        list.setAdapter(adapter);




                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(USERTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, Ptag_json_obj);

    }

    public static Context getContext() {
        return contextU;
    }


    /**
     * handles button clicks of this page
     * @param v The view that was clicked.
     */
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.UserBackButton2:
                startActivity(new Intent(UserPageActivity.this,
                        UserSearchActivity.class));
                break;
            case R.id.FollowButton:
                if(follow.getText().toString().toLowerCase().equals("unfollow")) {
                    FollowUnfollow.unfollow(StringRequestActivity.currUser, UserSearchActivity.currentFriendId);
                    follow.setText("follow");
                } else {
                    FollowUnfollow.follow(StringRequestActivity.currUser, UserSearchActivity.currentFriendId);
                    follow.setText("unfollow");
                }
                break;
        }
    }
}