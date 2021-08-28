package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChallengePOSTActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {
    EditText location,challenge;
    Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_p_o_s_t);
        location=findViewById(R.id.challengelocation);
        challenge=findViewById(R.id.challengepost);
        post=findViewById(R.id.challengepostbu);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void submit(){
        SharedPreferences preferences=getSharedPreferences("profile", MODE_PRIVATE);

        final String clocation=location.getText().toString();
        final String cchallenge=challenge.getText().toString();
        final String cusername=preferences.getString("username", "guest");

        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/challenge/insert.php";

        StringRequest request=new StringRequest(Request.Method.POST,url,this,this){
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("username",cusername);
                params.put("location",clocation);
                params.put("challenge",cchallenge);


                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this,""+response,Toast.LENGTH_SHORT).show();
        Intent i=new Intent(ChallengePOSTActivity.this,UserChallenge.class);
        startActivity(i);
    }
}