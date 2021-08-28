package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserChallenge extends AppCompatActivity {
    Button newChallenge;
    RecyclerView newchallengelist;
    private  List<getsetchallenge> clist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_challenge);

        newchallengelist=findViewById(R.id.listofchallenger);
        newChallenge=findViewById(R.id.newChallenge);
        newChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserChallenge.this,ChallengePOSTActivity.class);
                startActivity(i);
            }
        });
        load();
        reload();
    }
    public void load(){
        Toast.makeText(this,"Loading...",Toast.LENGTH_SHORT).show();
    }

    public void reload(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/challenge/index.php";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    setdata(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserChallenge.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public void setdata(JSONArray response){
        SharedPreferences user=getSharedPreferences("profile",MODE_PRIVATE);
        String cuser=user.getString("username", "guest");
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            for (int i=0;i<response.length();i++){
                getsetchallenge hplan=new getsetchallenge();
                JSONObject obj=response.getJSONObject(i);
                hplan.setId(obj.getInt("id"));
                hplan.setUsername(obj.getString("username"));
                hplan.setDate(obj.getString("date"));
                hplan.setChallenge(obj.getString("challenge"));
                hplan.setLikes(obj.getInt("likes"));
                hplan.setLocation(obj.getString("location"));
                hplan.setComments(obj.getString("comments"));
                hplan.setLoguser(cuser);
                clist.add(hplan);
            }
            challengeAdapter myAdapter = new challengeAdapter(this,clist) ;
            new LinearLayoutManager(this).setOrientation(LinearLayoutManager.VERTICAL);
            newchallengelist.setHasFixedSize(true);
            newchallengelist.setLayoutManager(new LinearLayoutManager(this));
            newchallengelist.setAdapter(myAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updatePhoto(String username){

        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/indexphotoUrl.php?username="+username+"";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null ,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


    }



}