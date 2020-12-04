package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
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

public class ViewPlaceActivity extends AppCompatActivity {
    RecyclerView placceListView;
    ImageView homefromplace;
    String location;
    private List<Anime> lstAnime = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        placceListView=findViewById(R.id.locationlistview);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        location=bundle.getString("location");

        load();

        homefromplace=findViewById(R.id.homefromplace);
        homefromplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewPlaceActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });


    }
    public void load(){
        Toast.makeText(this,"Loading data...",Toast.LENGTH_SHORT).show();
        loadsession();
    }


    public void loadsession(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="http://beezzserver.com/hasintha/travelingApp/locationPlace/index.php?location="+location+"";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setSession(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewPlaceActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);

    }


    //load the data recycler view Rv class using anime
    public void setSession(JSONArray response){
        //List<HashMap<String,String>> list=new ArrayList<>();
        try {
            for(int i=0;i<response.length();i++){
                JSONObject obj=response.getJSONObject(i);
                Anime anime=new Anime();

                anime.setId(obj.getInt("id"));
                anime.setPlacename(obj.getString("placename"));
                anime.setRating(obj.getString("rating"));
                anime.setDescription(obj.getString("description"));
                anime.setImage_url(obj.getString("urlimage"));
                anime.setLocation(obj.getString("location"));

                lstAnime.add(anime);
                //setRvadapter(lstAnime);
               /* HashMap<String,String> map=new HashMap<>();
                map.put("id",obj.getString("id"));
                map.put("placename",obj.getString("placename"));
                map.put("description",obj.getString("description"));
                map.put("location",obj.getString("location"));
                map.put("urlimage",obj.getString("urlimage"));
                String url=obj.getString("urlimage");
                System.out.println(url);
                list.add(map);*/


            }
            RvAdapter myAdapter = new RvAdapter(this,lstAnime) ;
            new LinearLayoutManager(this).setOrientation(LinearLayoutManager.VERTICAL);
            placceListView.setLayoutManager(new LinearLayoutManager(this));
            placceListView.setAdapter(myAdapter);

            /*
            int layout=R.layout.activity_listoflocation;
            int []view={R.id.listlocationid,R.id.locationnamelist,R.id.locationdeslist,R.id.urlimagelink};

            String []colomns={"id","placename","description","urlimage"};

            SimpleAdapter adapter=new SimpleAdapter(this,list,layout,colomns,view);
            placceListView.setAdapter(adapter);*/




        }catch (Exception ex){
            ex.printStackTrace();
        }
    }





}