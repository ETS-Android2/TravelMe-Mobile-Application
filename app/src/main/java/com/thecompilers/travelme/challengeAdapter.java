package com.thecompilers.travelme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class challengeAdapter extends RecyclerView.Adapter<challengeAdapter.MyViewHolder> {
    private Context mContext ;
    private List<getsetchallenge> mData ;
    RequestOptions options;

    SharedPreferences prefs;
    boolean key;
    int panelid;
    public challengeAdapter(Context mContext, List<getsetchallenge> mData) {
        this.mContext = mContext;
        this.mData = mData;
        options=new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.commentphoto)
                .error(R.drawable.commentphoto);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.challengepanel,parent,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        final int id=mData.get(position).getId();
        panelid=id;
        final String comments=mData.get(position).getComments();
        final String loguser=mData.get(position).getLoguser();

        //no comments;
        String[] noofcomments = comments.split(",");



        holder.challenger.setText(mData.get(position).getUsername());
        holder.cdate.setText(mData.get(position).getDate());
        holder.nooflike.setText(" "+mData.get(position).getLikes()+"");
        holder.nocomment.setText(" "+noofcomments.length);
        holder.cpost.setText(mData.get(position).getChallenge());
        Glide.with(mContext).load(mData.get(position).getPhotoUrl()).apply(options).circleCrop().into(holder.profile);

        final int nolikes=mData.get(position).getLikes();
        final boolean key=true;

            holder.nolike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            holder.nocomment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    ChallengeCommentDialog dialog=new ChallengeCommentDialog(id,comments,loguser);
                    dialog.show(manager, "comments Profle");
                }
            });



            holder.nolike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        // The toggle is enabled
                        holder.nooflike.setText((nolikes+1)+"");
                        updatelike(id,(nolikes+1));
                      //  holder.nolike.setText(nolikes);
                    } else {

                        holder.nooflike.setText((nolikes)+"");
                        updatelike(id,(nolikes));
                        // The toggle is disabled
                    }
                }
            });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        int id;
        TextView challenger,cdate,nocomment,cpost,nooflike;
        ToggleButton nolike;
        ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            challenger=itemView.findViewById(R.id.challengeusername);
            cdate=itemView.findViewById(R.id.challengedate);
            nolike=itemView.findViewById(R.id.challengelike);
            nocomment=itemView.findViewById(R.id.challengecomment);
            cpost=itemView.findViewById(R.id .challengeusercomment);
            nooflike=itemView.findViewById(R.id.nooflike);
            profile=itemView.findViewById(R.id.profileImage);
        }
    }


    public void updatelike(int id,int nolike){
        RequestQueue requestQueue= Volley.newRequestQueue(this.mContext);
        String url="https://dev.chethiya-kusal.me/hasintha/travelingApp/challenge/updatelike.php";
        final String cid=id+"";
        final String nolikes=nolike+"";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params=new HashMap<>();
                params.put("id", cid);
                params.put("nolikes", nolikes);

                return params;
            }
        };
        requestQueue.add(request);


    }
}
