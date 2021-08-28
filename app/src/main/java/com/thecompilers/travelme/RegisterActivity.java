package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {
    EditText username,address,emailField,passwordField;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        address=findViewById(R.id.address);
        emailField=findViewById(R.id.emailField);
        passwordField=findViewById(R.id.passwordField);
        update=findViewById(R.id.buttonRegister);


    }




    //save new user
    public void confirmUser(View v){
        final String Username=username.getText().toString();
        final String Address=address.getText().toString();
        final String email=emailField.getText().toString();
        final String password=passwordField.getText().toString();

        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/insert.php";

        StringRequest  request=new StringRequest(Request.Method.POST,url,this,this){
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("username",Username);
                params.put("email",email);
                params.put("address",Address);
                params.put("password",password);


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
        if(response.equals("[\"Registration Success!\"]")){
            username.setText("");
            emailField.setText("");
            passwordField.setText("");
            address.setText("");
        }

    }
}