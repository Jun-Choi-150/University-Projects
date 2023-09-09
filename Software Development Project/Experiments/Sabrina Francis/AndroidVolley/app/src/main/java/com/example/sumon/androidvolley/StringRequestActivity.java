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

public class StringRequestActivity extends Activity implements OnClickListener {

    private String TAG = StringRequestActivity.class.getSimpleName();
    private Button btnJsonObj;
    //private TextView msgResponse;
    private ProgressDialog pDialog;

    private TextView email;
    private TextView password;

    private String name;

    private Button back;


    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.string_request);

        btnJsonObj = (Button) findViewById(R.id.btnJsonObj);
//        btnJsonArray = (Button) findViewById(R.id.btnJsonArray);
        //msgResponse = (TextView) findViewById(R.id.msgResponse);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress);
        password = (TextView) findViewById(R.id.editTextTextPassword);
        back = (Button) findViewById(R.id.button5);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnJsonObj.setOnClickListener(this);
        back.setOnClickListener(this);
//        btnJsonArray.setOnClickListener(this);
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
    private void makeJsonArrayReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean correctUser = false;
                            JSONArray jsonArray = new JSONArray(response.toString());

                            for(int i = 0; i < jsonArray.length(); i++) {
                                if (email.getText().toString().equals(jsonArray.getJSONObject(i).getString("emailId")) && password.getText().toString().equals(jsonArray.getJSONObject(i).getString("password"))) {
                                    startActivity(new Intent(StringRequestActivity.this, HomeActivity.class));
                                    correctUser = true;
                                }
                            }
                            if(!correctUser) {
                                Toast.makeText(getApplicationContext(), "incorrect username or password", Toast.LENGTH_SHORT).show();
                            }


                        } catch(JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d(TAG, response.toString());
                        //msgResponse.setText(name);
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
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("emailId", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObReq,
//                tag_json_obj);
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);

        // Cancelling request
         //AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnJsonObj:
                makeJsonArrayReq();
                break;
            case R.id.button5:
                startActivity(new Intent(StringRequestActivity.this,
                        MainActivity.class));
                break;
            default:
                break;
        }


        }

    }


