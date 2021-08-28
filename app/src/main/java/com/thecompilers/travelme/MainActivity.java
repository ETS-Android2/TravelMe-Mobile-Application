package com.thecompilers.travelme;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements Response.Listener<String>,Response.ErrorListener {
    EditText email,password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.emailField);
        password=findViewById(R.id.passwordField);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void onResume() {
        super.onResume();
        email.setText("");
        password.setText("");
        progressBar.setVisibility(View.INVISIBLE);
    }

    String emaillog;
    @SuppressWarnings("UnusedDeclaration")
    public void loginHome(View v){
        progressBar.setVisibility(View.VISIBLE);
        final String emailF=email.getText().toString();
        final String passwordF=password.getText().toString();

        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/index.php";

        StringRequest request=new StringRequest(Request.Method.POST,url,this,this){
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("email",emailF);
                params.put("password",passwordF);
                emaillog=emailF;
                return params;
            }
        };
        queue.add(request);
    }
    @SuppressWarnings("UnusedDeclaration")
    public void registeruser(View v){
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResponse(String response) {
        try {
            response = response.replace("\"", "");
            System.out.println(response);
            String[] part = response.split("(?<=\\D)(?=\\d)");
            String username=part[0];
            System.out.println(part[1]);
            if(part[1].equals("0")){

                Intent i=new Intent(this,HomeActivity.class);
                SharedPreferences prefs=getSharedPreferences("profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor  editor=prefs.edit();
                editor.putString("username",username);
                editor.putString("email",emaillog);
                editor.apply();

                startActivity(i);

            }else{
                Toast.makeText(this,""+response,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Email or Password Incorrect",Toast.LENGTH_SHORT).show();
        }



    }
}