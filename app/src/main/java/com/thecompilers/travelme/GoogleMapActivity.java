package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapApi;
    SupportMapFragment mapFragment;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        searchView=findViewById(R.id.sv_location);



        //search text code
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location=searchView.getQuery().toString();
                List<Address> addresses=null;
                if(location !=null || !location.equals("")){

                    try {
                        Geocoder geocoder=new Geocoder(GoogleMapActivity.this);
                        addresses=geocoder.getFromLocationName(location,1);

                    }catch (Exception ex){
                            ex.printStackTrace();
                    }
                    Address address=addresses.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    mapApi.addMarker(new MarkerOptions().position(latLng).title(location));
                    mapApi.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15 ));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }


    public void callOnplce(String value){
        List<Address> addresses=null;
        if(value !=null || !value.equals("")){

            try {
                Geocoder geocoder=new Geocoder(GoogleMapActivity.this);
                addresses=geocoder.getFromLocationName(value,1);

            }catch (Exception ex){
                ex.printStackTrace();
            }
            Address address=addresses.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            mapApi.addMarker(new MarkerOptions().position(latLng).title(value));
            mapApi.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15 ));
        }


    }

    //first loading mp
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapApi=googleMap;
        LatLng latLng=new LatLng(8.19063, 80.24552);
        mapApi.addMarker(new MarkerOptions().position(latLng).title("Anuradhapura"));
        mapApi.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        String name=getIntent().getExtras().getString("placename","Anuradhapura");
        System.out.println(name);
        callOnplce(name);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    public void homeclick(View v){
        Intent i=new Intent(this,HomeActivity.class);
        startActivity(i);
    }

}