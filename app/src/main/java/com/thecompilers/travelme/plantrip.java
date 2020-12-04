package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Generate the trip want to visit

public class plantrip extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {
    Button startD,endD;
    EditText tripname;
    AutoCompleteTextView locationName;
    DatePickerDialog dpd;
    Calendar c;

    Map<String,String> locations=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantrip);

        startD=findViewById(R.id.startdate);
        endD=findViewById(R.id.enddate);
        tripname=findViewById(R.id.tripName);
        locationName=findViewById(R.id.tripLocation);


        //select dates from start date
        startD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int date=c.get(Calendar.DAY_OF_MONTH);
                c.getTime();
                dpd= new DatePickerDialog(plantrip.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startD.setText(+year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },year,month,date);
                dpd.show();
            }
        });

        //select dates from end date
        endD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int date=c.get(Calendar.DAY_OF_MONTH);
                c.getTime();
                dpd= new DatePickerDialog(plantrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endD.setText(+year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },year,month,date);
                dpd.show();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocation();
    }


    //retriew data to autocomplete text view in plan trip activity
    public void loadLocation(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="http://beezzserver.com/hasintha/travelingApp/plantrip/locations.php";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    setLocation(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }

    public void setLocation(JSONArray response){
        List<String> list=new ArrayList<>();
        for(int i=0;i<response.length();i++){
            try{
                JSONObject object=response.getJSONObject(i);
                 list.add(object.getString("location"));
                locations.put(object.getString("location"),object.getString("location"));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        int layout=android.R.layout.simple_list_item_1;
        ArrayAdapter adapter=new ArrayAdapter(this,layout,list);
        locationName.setAdapter(adapter);
    }



    //generate save the trip plan to trip history table after click generate
    public void generateTrip(View v){
        final String loc=locationName.getText().toString();
        final String tname=tripname.getText().toString();
        final String sdate=startD.getText().toString();
        final String edate=endD.getText().toString();
        SharedPreferences profile=getSharedPreferences("profile",MODE_PRIVATE);
        final String username=profile.getString("username","");


        if(!loc.equals("")){
            RequestQueue queue= Volley.newRequestQueue(this);
            String url="http://beezzserver.com/hasintha/travelingApp/plantrip/insert.php";

            StringRequest request=new StringRequest(Request.Method.POST,url,this,this){
                protected Map<String, String> getParams() {
                    HashMap<String,String> params=new HashMap<>();
                    params.put("tname",tname);
                    params.put("sdate",sdate);
                    params.put("edate",edate);
                    params.put("location",loc);
                    params.put("username",username);


                    return params;
                }
            };
            queue.add(request);

            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",loc);
            startActivity(i);
        }

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this,""+response,Toast.LENGTH_SHORT).show();

        locationName.setText("");
        endD.setText("CHOOSE DATE");
        startD.setText("CHOOSE DATE");
        tripname.setText("");
    }


    //bottom layers methods
    public void showplaces(View v){
        Intent i=new Intent(this,placeActivity.class);
        startActivity(i);
    }

    public void hotelplaces(View v){
        Intent i=new Intent(this,HotelActivity.class);
        startActivity(i);
    }

    public void myplan(View v){
        Intent i=new Intent(this,MyPlanActicity.class);
        startActivity(i);
    }

    public void images(View v){
        Intent i=new Intent(this,imagesActivity.class);
        startActivity(i);
    }

    public void homeclick(View v){
        Intent i=new Intent(this,HomeActivity.class);
        startActivity(i);
    }


}