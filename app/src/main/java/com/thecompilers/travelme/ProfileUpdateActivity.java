package com.thecompilers.travelme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thecompilers.travelme.R.drawable.roundborder2;

public class ProfileUpdateActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {

    EditText username,address,emailField,passwordField;
    String email="";
    Button update;
    ImageView profile;
    Bitmap bitmap;

    RequestOptions options ;

    final int CODE_GALLERY_REQUEST=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);



        username=findViewById(R.id.username1);
        address=findViewById(R.id.address1);
        emailField=findViewById(R.id.emailField1);
        passwordField=findViewById(R.id.passwordField1);
        update=findViewById(R.id.buttonUpdate);

        profile=findViewById(R.id.imageuserprofile);

        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        email=bundle.getString("logemail","");
        System.out.println(email);
        setProfile(email);
        load();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(v);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                       ProfileUpdateActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST

                );
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CODE_GALLERY_REQUEST){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),CODE_GALLERY_REQUEST);
            }
            else {
                Toast.makeText(getApplicationContext(),"You dont have the permission to gallery",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data!=null){
            Uri filepath=data.getData();
            try {

                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint);
                profile.setImageBitmap(circleBitmap);
                uploadImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    //upload image
    public void uploadImage(){



        System.out.println("uploadImage");
        final String _id=id;
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/updatep.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response+"h");
                Toast.makeText(ProfileUpdateActivity.this,""+response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error+"h");
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                String imageData=imageToString(bitmap);
                params.put("id",_id);
                params.put("image",imageData);
                return params;
            }
        };
        queue.add(request);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte []imageBytes=outputStream.toByteArray();
        String encodedImage=Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;

    }





    //laoding notification
    public void load(){
        Toast.makeText(this,"Loading data...",Toast.LENGTH_SHORT).show();
    }


    //load the data to text field using email
    public void setProfile(String email){
        System.out.println(email+"this");
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/index2.php?email="+email+" ";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            //    System.out.println(response);
                setRegister(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileUpdateActivity.this,error+"",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);


    }


    //load the data
    String id="";
    String username2="";
    String email2="";
    String address2="";
    String image="";
    public void setRegister(JSONArray response){
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.commentphoto)
                .error(R.drawable.commentphoto);

        List<HashMap<String,String>> list=new ArrayList<>();
        try {
            for(int i=0;i<response.length();i++){
                JSONObject obj=response.getJSONObject(i);
                username2=obj.getString("username");
                email2=obj.getString("email");
                address2=obj.getString("address");
                image="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/Images/"+obj.getString("image");
                id=obj.getString("id");
            }

            username.setText(username2);
            emailField.setText(email2);
            address.setText(address2);

            Glide.with(this).load(image).apply(options).circleCrop().into(profile);

        }catch (Exception e){
            e.printStackTrace();
        }
    }





    //update the profile
    public void updateUser(View view){
        final String _id=id;
        final String Username=username.getText().toString();
        final String Address=address.getText().toString();
        final String email=emailField.getText().toString();
        final String password=passwordField.getText().toString();

        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/registeruser/update.php";

        StringRequest request=new StringRequest(Request.Method.POST,url,this,this){
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("id",_id);
                params.put("username",Username);
                params.put("email",email);
                params.put("address",Address);
                params.put("password",password);


                return params;
            }
        };
        queue.add(request);
        SharedPreferences prefs=getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor=prefs.edit();
        editor.putString("username",Username);
        editor.putString("email",email);
        editor.apply();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this,""+response,Toast.LENGTH_SHORT).show();
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