package com.thecompilers.travelme;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mtoolbar;
    TextView t1;

    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawerLayout);

        mtoolbar=(Toolbar) findViewById(R.id.nav_action_title);
        setSupportActionBar(mtoolbar);


        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //slid show
        ImageView imageViewslideshow=findViewById(R.id.imageslide);
        AnimationDrawable x=(AnimationDrawable)imageViewslideshow.getDrawable();
        x.start();

        //get the data from tempory database
        prefs=getSharedPreferences("profile",Context.MODE_PRIVATE);
        String username=prefs.getString("username","");
        t1=findViewById(R.id.logusername);
        t1.setTextColor(getResources().getColor(R.color.whitecolor));
        t1.setTextSize(18);
        t1.setText(username);


        SharedPreferences.Editor  editor=prefs.edit();
        editor.putString("username",username);
        editor.apply();



    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void loginplantrip(View v){
        Intent i=new Intent(this,plantrip.class);
        startActivity(i);
    }


    public void googleMap(View v){
        Intent i=new Intent(this,GoogleMapActivity.class);
        i.putExtra("placename","Anurdhapura");
        startActivity(i);
    }

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


    public void news(View v){
        Intent i=new Intent(this,NewsFeedActivity.class);
        startActivity(i);
    }

    public void registeruser(View v){
        Intent i=new Intent(this,ProfileUpdateActivity.class);
        String useremail=prefs.getString("email","Guest");
        i.putExtra("logemail",useremail);
        startActivity(i);
    }

    public void reviews(View v){
      Intent i=new Intent(this,UserChallenge.class);
      startActivity(i);
    }

    public void homeclick(View v){

    }





}