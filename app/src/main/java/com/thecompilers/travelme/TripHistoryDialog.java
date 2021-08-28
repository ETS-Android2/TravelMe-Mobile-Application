package com.thecompilers.travelme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TripHistoryDialog extends AppCompatDialogFragment implements Response.Listener<String>,Response.ErrorListener {
    //dailog of hisoty of trip click
    int id;
    String name,date,location,note,places;
    AutoCompleteTextView addplaces;
    EditText tripnote;
    Button clickPlaced;
    ListView addplacesList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    String addVPlaces="";

    public TripHistoryDialog(int id, String name, String date, String location, String note, String places) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.note = note;
        this.places=places;
    }

    //get the varchar value and convert to string array and put to list;
    public void converting(){
        String[] listofplaces = places.split(", ");
        for(int i=0;i<listofplaces.length;i++){
            arrayList.add(listofplaces[i]);
            adapter.notifyDataSetChanged();
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.triphistorydialog,null);
        tripnote=view.findViewById(R.id.dtripnote);
        addplacesList=view.findViewById(R.id.visistedplaceslist);
        clickPlaced=view.findViewById(R.id.visistedplacesbutton);
        addplaces=view.findViewById(R.id.visistedplaces);

        tripnote.setText(note );

        arrayList=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,arrayList);
        addplacesList.setAdapter(adapter);
        converting();
        addVPlaces=places+addVPlaces;
        clickPlaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=addplaces.getText().toString();
                addVPlaces=result+", "+addVPlaces;
                arrayList.add(result);
                adapter.notifyDataSetChanged();
                addplaces.setText("");
                Toast.makeText(getActivity().getApplicationContext(),"Added!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view).setTitle(name+ " Tour").setIcon(R.drawable.mytravel);
        //submit button and cancel button
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    updateTrip();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        return builder.create();
    }


    public void updateTrip(){
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/plantrip/update.php";
        final String tid=id+"";
        final String tnote=tripnote.getText().toString();
        final String tplace=addVPlaces;
        System.out.println(id+"s"+note);
        StringRequest request=new StringRequest(Request.Method.POST, url, this,this){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("id", tid);
                params.put("note", tnote);
                params.put("places", tplace);

                return params;
            }
        };
        requestQueue.add(request);

        Toast.makeText((getActivity()).getApplicationContext(),"Succesfully Update! Refresh the List.",Toast.LENGTH_SHORT).show();

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
