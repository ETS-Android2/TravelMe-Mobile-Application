package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.*;

public class MyPlanActicity extends AppCompatActivity {
    RecyclerView triphistoryList;
    ImageView newTrip;
    ImageView refresghI;
    RvHAdapter myAdapter;
    String username="";
    private List<historyPlan> lsttrip = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan_acticity);

        triphistoryList=findViewById(R.id.trpHistorylist);
        newTrip=findViewById(R.id.addnewtour);
        newTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyPlanActicity.this,plantrip.class);
                startActivity(i);
            }
        });


        refresghI=findViewById(R.id.refresh);
        refresghI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdata();
            }
        });

        SharedPreferences profile=getSharedPreferences("profile",MODE_PRIVATE);
        username=profile.getString("username","");

        load();
        loadTrips();
    }
    public void load(){
        Toast.makeText(this,"Loading...",Toast.LENGTH_SHORT).show();
    }

    public void rdata(){
        lsttrip.clear();
        myAdapter.notifyDataSetChanged();
        loadTrips();
    }



    public void loadTrips(){

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="http://beezzserver.com/hasintha/travelingApp/plantrip/index.php?username="+username+" ";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        setData(response);
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(MyPlanActicity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }
    //load the data to recycler view of trip
    public void setData(JSONArray response){

        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            for (int i=0;i<response.length();i++){
                historyPlan hplan=new historyPlan();
                JSONObject obj=response.getJSONObject(i);
                hplan.setId(obj.getInt("id"));
                hplan.setName(obj.getString("name"));
                hplan.setEnddate(obj.getString("enddate"));
                hplan.setStartdate(obj.getString("startdate"));
                hplan.setLocation(obj.getString("location"));
                hplan.setNote(obj.getString("note"));
                hplan.setPlaces(obj.getString("places"));
                lsttrip.add(hplan);
            }
            myAdapter = new RvHAdapter(this,lsttrip) ;
            new LinearLayoutManager(this).setOrientation(LinearLayoutManager.VERTICAL);
            triphistoryList.setLayoutManager(new LinearLayoutManager(this));
            triphistoryList.setAdapter(myAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}