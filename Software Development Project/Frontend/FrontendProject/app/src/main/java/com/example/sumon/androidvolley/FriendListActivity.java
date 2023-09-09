package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * FriendListActivity class is for the friends list page
 * this class contains a JSON array request to get the friends of the user
 * @author sabrinaf
 */
public class FriendListActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout friends;
    private String Ftag_json_arry = "jarray_req";;
    private int numberOfFriends;
    private String FTAG = FriendListActivity.class.getSimpleName();
    public static int currentFriend = -1;
    private ImageButton backButton;


    /**
     * This method is setting the class to the friends list page and calling the
     * JSON request
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friends = (TableLayout) findViewById(R.id.friends);
        backButton = (ImageButton) findViewById(R.id.backButton1);
        backButton.setOnClickListener(this);

        makeJsonArrayReq();

    }

    /**
     * This method is a JSON array request to get the
     * friends of the signed in user to display on the friends list page
     */
    public void makeJsonArrayReq() {
        //showProgressDialog();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String url = Const.URL_JSON_USER + "/" + StringRequestActivity.currUser + "/friends";
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        JSONArray friendList = null;
                        String fusername;
                        int x = 0;
                        try {
                            friendList = new JSONArray(response.toString());
                            numberOfFriends = friendList.length();
                            int[] friendId = new int[numberOfFriends];
                            for(int i = 0; i < numberOfFriends; i++) {
                                friendId[i] = friendList.getJSONObject(i).getInt("id");
                            }

                            for (int i = 0; i < friendList.length(); i++) {
                                fusername = friendList.getJSONObject(i).getString("username");
                                String friendPic = friendList.getJSONObject(i).getString("profilePic");
                                TableRow FtableRow = new TableRow(friends.getContext());
                                FtableRow.setBackgroundColor(friends.getResources().getColor(R.color.colorPrimary));
                                FtableRow.setId(View.generateViewId());
                                FtableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));


                                NetworkImageView FriendsPic = new NetworkImageView(friends.getContext());
                                FriendsPic.setId(View.generateViewId());
                                FriendsPic.setImageUrl(friendPic,imageLoader);

                                FtableRow.addView(FriendsPic);
                                FriendsPic.getLayoutParams().height = 250;
//
                                Button fuser = new Button(friends.getContext());

                                fuser.setTag("friendsButton"+x);
                                fuser.setId(View.generateViewId());
                                x++;
                                fuser.setGravity(Gravity.CENTER);
                                fuser.setBackgroundColor(friends.getResources().getColor(R.color.colorPrimary));
                                fuser.setTextColor(friends.getResources().getColor(R.color.white));
                                fuser.setTextSize(30);
                                fuser.setText(fusername);
                                FtableRow.addView(fuser);

                                friends.addView(FtableRow);



                                fuser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        for(int i = 0; i < numberOfFriends; i++) {
                                            if(v.getTag().toString().equals("friendsButton"+i)) {
                                                currentFriend = friendId[i];
                                            }
                                        }
                                        startActivity(new Intent(FriendListActivity.this,
                                                FriendPageActivity.class));
                                    }
                                });

                            }
                        } catch (
                        JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(request, Ftag_json_arry);


    }

    /**
     * Class that handles the buttons of this page
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backButton1:
                startActivity(new Intent(FriendListActivity.this,
                        PersonalAccountActivity.class));
                break;
        }
    }


}