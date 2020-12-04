package com.thecompilers.travelme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChallengeCommentDialog extends AppCompatDialogFragment implements Response.ErrorListener,Response.Listener<String> {
    int id;
    String comments,username;
    ListView listView;
    EditText usercomment;

    ArrayList<String> carrayList;
    ArrayAdapter<String> cadapter;



    public ChallengeCommentDialog(int id, String comments,String username) {
        this.id = id;
        this.comments = comments;
        this.username=username;


    }

    public void converting(){
        String[] listofcomments = comments.split(",");
        for(int i=0;i<listofcomments.length;i++){
            carrayList.add(listofcomments[i]);
            cadapter.notifyDataSetChanged();
        }

    }

    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState)  {


        //alert dailog objective
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.popchallengecoments,null);

        listView=view.findViewById(R.id.challengecommentlist);
        usercomment=view.findViewById(R.id.challengecommnts);

        carrayList=new ArrayList<String>();
        cadapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,carrayList);
        listView.setAdapter(cadapter);
        converting();

        //submit button and cancel button
        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setcomment();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        builder.setView(view).setTitle("Comments").setIcon(R.drawable.ic_baseline_comment_24);

        return builder.create();

    }
    String finalcoment="";
    public void setcomment(){
        finalcoment=username+":-->"+usercomment.getText().toString();
        updateTrip();
    }

    public void updateTrip(){
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="http://beezzserver.com/hasintha/travelingApp/challenge/updatecomment.php";
        final String cid=id+"";
        final String ccomments=finalcoment+","+comments;
        StringRequest request=new StringRequest(Request.Method.POST, url, this,this){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("id", cid);
                params.put("comments", ccomments);

                return params;
            }
        };
        requestQueue.add(request);

        Toast.makeText((getActivity()).getApplicationContext(),"Succesfully Post Comment! Refresh the List.",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //fragment show toast
        Toast.makeText((getActivity()).getApplicationContext(),"Something went a wrong!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        //
    }
}
