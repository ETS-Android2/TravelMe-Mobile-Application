package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Insets;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//this is the collapse acticty one place review and description
public class AnimeActivity extends AppCompatActivity  {
    ImageView animeImage,newComment;
    TextView ratinanime,pdescrption,address;
    EditText reviewEdit;
    int id;

    String username;
    ListView reviewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        //geting data from places recycle view after click
        final String name=getIntent().getExtras().getString("anime_placename");
        String loc=getIntent().getExtras().getString("anime_address");
        String description=getIntent().getExtras().getString("anime_placedescription");
        String imgurl=getIntent().getExtras().getString("anime_imageurl");
        String reviews=getIntent().getExtras().getString("anime_reviews");
        id=getIntent().getExtras().getInt("anime_id");

        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsetoolbarView);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(name);


        animeImage=findViewById(R.id.locationimnageanime);
        pdescrption=findViewById(R.id.locationdesanime);
        ratinanime=findViewById(R.id.reviwesanime);
        address=findViewById(R.id.laddress);
        reviewList=findViewById(R.id.listViewRe);

        pdescrption.setText(description);
        ratinanime.setText(reviews);
        address.setText(loc);

        //error handling photo
        RequestOptions requestOptions=new RequestOptions().centerCrop().placeholder(R.drawable.loadingone).error(R.drawable.loadingone);

        Glide.with(this).load(imgurl).apply(requestOptions).into(animeImage);

        //retriew user data from saved
        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        username=profile.getString("username","Guest");
        System.out.println(username);


        reviewEdit=findViewById(R.id.reviwesnote);

        //click add comment botton
        newComment=findViewById(R.id.newComment);
        newComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_show_dialog(v);
            }
        });


        ViewCompat.setNestedScrollingEnabled(reviewList, true);

        //click address the app
       address.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(AnimeActivity.this,GoogleMapActivity.class);
               i.putExtra("placename",name);

               startActivity(i);

           }
       });

    }


    public void btn_show_dialog(View v){
        //call the dialog fragment via activity
        ExampleDialog exampleDialog=new ExampleDialog(id,username);
        exampleDialog.show(getSupportFragmentManager(),"example dialog");

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadreview();
    }


    //load data to list view to comment about places
    public void loadreview(){

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String url="http://beezzserver.com/hasintha/travelingApp/reviews/index.php?pid="+id+"";
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
                Toast.makeText(AnimeActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }

    public void setData(JSONArray response){
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        try {
            for (int i=0;i<response.length();i++){
                JSONObject obj=response.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("cid",obj.getString("id"));
                map.put("username",obj.getString("username"));
                map.put("review",obj.getString("review"));
                map.put("date",obj.getString("date"));
                list.add(map);
            }

            //1. layout file
            int layout=R.layout.customeuserreview;

            //2.views
            int []views={R.id.cid,R.id.ruser,R.id.datet,R.id.message};

            //3.columns name
            String[] colomns={"cid","username","date","review"};
            SimpleAdapter adapter=new SimpleAdapter(this,list,layout,colomns,views);
            reviewList.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}