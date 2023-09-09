package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * @author Haris Khan
 * The PostActivity class is a page for users to be able
 * to create posts and ratings about movies and send a POST request to the server
 *
 */
public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView rating;

    private TextView review;

    private Button post;

    private Button back;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    private String TAG = SignUpActivity.class.getSimpleName();


    /**
     * creates the layout for the PostActivity page
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}. <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        rating = (TextView) findViewById(R.id.editTextNumber);
        review = (TextView) findViewById(R.id.editTextTextPersonName5);
        back = (Button) findViewById(R.id.button7);
        post = (Button) findViewById(R.id.button6);

        post.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    /**
     * makes json object request to post a users review on the server
     */
    private void makeJsonObjReq() {
        /*
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rating",rating.getText().toString());
            jsonObject.put("review", review.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.Post, Const.URL_JSON_ARRAY, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(PostActivity.this));
                    }
                }, {
                    @Override
                    public void onErrorRespons;(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressDialog();
                    }
                });
                    AppController.getInstance().addToRequestQueue(request, tag_json_obj);
                }
                */

    }

    /**
     * Handles button clicks for this page, if the user presses the "back" button it will go to the
     * previous page or if the user presses "post" it will create the post request
     * @param view the view clicked
     *
     */
    @Override
    public void onClick(View view) {
        /*
        switch (view.getId()) {
            case R.id.button6:
                makeJsonObjReq();

                break;
            case R.id.button7:
                startActivity(MainActivity.class);
                break;
            default:
                break;
        }
        */
    }
}
