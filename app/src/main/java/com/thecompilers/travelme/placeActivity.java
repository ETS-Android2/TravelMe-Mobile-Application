package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Objects;
//here is show the city of srilank
public class placeActivity extends AppCompatActivity {
    MaterialSearchView materialSearchView;
    LinearLayout lv1,lv2,lv3,lv4,lv5,lv6,lv7,lv8,lv9,lv10,lv11,lv12,lv13,lv14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Place Search");
        toolbar.setTitleTextColor(Color.WHITE);

        materialSearchView=findViewById(R.id.searchbar2);

        lv1=findViewById(R.id.anura);
        lv2=findViewById(R.id.polon);
        lv3=findViewById(R.id.matara);
        lv4=findViewById(R.id.hambantota);
        lv5=findViewById(R.id.nuwaraeliya);
        lv6=findViewById(R.id.badulla);
        lv7=findViewById(R.id.kurunagal);
        lv8=findViewById(R.id.colombo);
        lv9=findViewById(R.id.galle);
        lv10=findViewById(R.id.rathnapura);
        lv11=findViewById(R.id.kalutara);
        lv12=findViewById(R.id.kandy);
        lv13=findViewById(R.id.jaffna);
        lv14=findViewById(R.id.trinco);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchactionmenue);
        materialSearchView.setMenuItem(menuItem);

        return true;
    }


    String location;
    public void viewPlcae(View v){
        LinearLayout l1= (LinearLayout) v;
        if(l1==lv1){
            location="Anuradhapura";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv2){
            location="Polonnaruwa";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv10){
            location="Rathnapura";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }

        if(l1==lv3){
            location="Matara";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv4){
            location="Hambanthota";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv5){
            location="Nuwara-eliya";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv6){
            location="Badulla";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv7){
            location="Kurunegala";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv8){
            location="Colombo";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv9){
            location="Galle";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv11){
            location="Kaluthara";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }
        if(l1==lv12){
            location="Kandy";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }

        if(l1==lv13){
            location="Jaffna";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }

        if(l1==lv14){
            location="Trincomalee";
            Intent i=new Intent(this,ViewPlaceActivity.class);
            i.putExtra("location",location);
            startActivity(i);

        }



    }
}