package com.thecompilers.travelme;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class imagesActivity extends AppCompatActivity {
    RecyclerView recyclerImageView;
    List<ModelImage> imageList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerImageView=findViewById(R.id.recyclerImageView);
        fetchImages();




    }

//laod images from database
    public void fetchImages(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="http://beezzserver.com/hasintha/travelingApp/images/retriewimages.php";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setSession(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(imagesActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    public void setSession(JSONArray response){

        try {
            for(int i=0;i<response.length();i++){
                JSONObject obj=response.getJSONObject(i);
                ModelImage modelImage=new ModelImage();
                modelImage.setId(obj.getInt("id")+"");
                modelImage.setLocation(obj.getString("placename"));
                modelImage.setImageurl("http://beezzserver.com/hasintha/travelingApp/imagesdatabase/"+obj.getString("imagesurl"));
                imageList.add(modelImage);


            }

            MyAdapter myAdapter=new MyAdapter(this,imageList);
            recyclerImageView.setLayoutManager(new LinearLayoutManager(this));
            recyclerImageView.setAdapter(myAdapter);




        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}