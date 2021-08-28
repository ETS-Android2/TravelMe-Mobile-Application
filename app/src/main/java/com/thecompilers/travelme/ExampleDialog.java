package com.thecompilers.travelme;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

//this dailog show clss using pp compatdialogfragment
public class ExampleDialog extends AppCompatDialogFragment {
    EditText reviewEdit;
    int id;
    String username,review;

    public ExampleDialog(int id,String username){
        this.id=id;
        this.username=username;
        System.out.println(id+username);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        //alert dailog objective
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.custuompopupwriterevies,null);

        //submit button and cancel button
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            review=reviewEdit.getText().toString()+"";
                            insertDataReview();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        builder.setView(view).setTitle("Comments").setIcon(R.drawable.ic_baseline_comment_24);
        reviewEdit=view.findViewById(R.id.reviwesnote);

        return builder.create();

    }
    //insert new comment for perticulr place
    public void insertDataReview() {
        final String u=username;
        final String r=review;
        final String i=id+"";
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/reviews/insert.php";

        StringRequest  request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){

            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("username",u);
                params.put("review",r);
                params.put("id",i);
                return params;
            }
        };
        requestQueue.add(request);

        Toast.makeText(getActivity().getApplicationContext(), "Post comment  is success!", Toast.LENGTH_SHORT).show();
        ((AnimeActivity)getActivity()).loadreview();
    }

}
