package com.example.sumon.androidvolley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonRequestActivity extends Activity implements OnClickListener {

    private String TAG = JsonRequestActivity.class.getSimpleName();
    private Button btnJsonObj, btnJsonArray;
    private TextView msgResponse;
    private ProgressDialog pDialog;

    private TextView id;

    private Button buttonBack;

    private Button findId;

    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        buttonBack = (Button) findViewById(R.id.button);
        buttonBack.setOnClickListener(this);
        //btnJsonObj = (Button) findViewById(R.id.btnJsonObj);
        findId = (Button) findViewById(R.id.button2);
        id = (TextView) findViewById(R.id.editTextTextPersonName2);
        //msgResponse = (TextView) findViewById(R.id.msgResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        //btnJsonObj.setOnClickListener(this);
        findId.setOnClickListener(this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())

            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     * */
    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_ARRAY, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");
                params.put("id", id.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * Making json array request
     * */
    private void makeJsonArrayReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        //msgResponse.setText(response.toString());

                        String title, rating, genre, year, description;
                        Boolean found = false;

                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (id.getText().toString().equals(jsonArray.getJSONObject(i).getString("id"))) {
//                                        Toast.makeText(getApplicationContext(), jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(1).getString("movieTitle"), Toast.LENGTH_SHORT).show();
                                    for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("movies").length(); j++) {
                                        //jsonArray.getJSONObject(i).getJSONArray("movies").length()
                                        title = jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(j).getString("movieTitle");
                                        rating = jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(j).getString("movieRating");
                                        genre = jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(j).getString("movieGenre");
                                        year = jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(j).getString("movieYear");
                                        description = jsonArray.getJSONObject(i).getJSONArray("movies").getJSONObject(j).getString("movieDescription");
                                        Toast.makeText(getApplicationContext(), "Test title: " + title, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Test rate: " + rating, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Test genre: " + genre, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Test year: " + year, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Test desc: " + description, Toast.LENGTH_SHORT).show();

                                        //msgResponse = getApplicationContext(), "Test: " + title, Toast.LENGTH_SHORT;
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(JsonRequestActivity.this,
                        StringRequestActivity.class));
                break;
            case R.id.button2:
                makeJsonArrayReq();
                break;
        }

    }

}
