package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.chrono.JapaneseChronology;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity implements View.OnClickListener {

    private TextView name;
    private TextView username;
    private TextView password;
    private TextView email;
    private Button signUp;
    private Button back;

    private String TAG = SignUpActivity.class.getSimpleName();
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (TextView) findViewById(R.id.editTextTextPersonName);
        username = (TextView) findViewById(R.id.editTextTextPersonName2);
        password = (TextView) findViewById(R.id.editTextTextPassword2);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress2);
        back = (Button) findViewById(R.id.button4);
        signUp = (Button) findViewById(R.id.button3);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);




        signUp.setOnClickListener(this);
        back.setOnClickListener(this);
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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name.getText().toString());
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("emailId", email.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        showProgressDialog();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Const.URL_JSON_ARRAY, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        //Volley.newRequestQueue(getApplicationContext()).add(req);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
        //AppController.getInstance().addToRequestQueue(req,
               // tag_json_arry);

        // Cancelling request
        //AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button3:
                if(name.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("") || email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "invalid information", Toast.LENGTH_SHORT).show();
                } else {
                    makeJsonObjReq();
                }
                break;
            case R.id.button4:
                startActivity(new Intent(SignUpActivity.this,
                        MainActivity.class));
                break;
            default:
                break;
        }




    }

}
