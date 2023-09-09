package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
/**
 * @author sabrinaf
 */
public class FollowUnfollow {

    public static String FOLLOWTAG = PersonalAccountActivity.class.getSimpleName();

    public static String Followtag_json_obj = "jobj_req";

//    private int user;
//    private int friend;

    public static void follow(int user, int friend) {


        String url = Const.URL_JSON_USER + "/" + user + "/addFriend/" + friend;
        //showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                url, null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONObject response) {


                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FOLLOWTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, Followtag_json_obj);

    }

    public static void unfollow(int user, int friend) {


        String url = Const.URL_JSON_USER + "/" + user + "/removeFriend/" + friend;
        //showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONObject response) {


                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(FOLLOWTAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, Followtag_json_obj);

    }
}
