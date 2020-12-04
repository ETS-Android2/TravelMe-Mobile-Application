package com.thecompilers.travelme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//load the data one district place

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>  {
    RequestOptions options ;
    private Context mContext ;
    private List<Anime> mData ;

    public RvAdapter(Context mContext, List<Anime> mData) {
        this.mContext = mContext;
        this.mData = mData;

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loadingone)
                .error(R.drawable.loadingone);

    }

    @NonNull
    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_listoflocation,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, final int position) {
        holder.placename.setText(mData.get(position).getPlacename());
        holder.rate.setText(mData.get(position).getRating()+" Reviews");
        holder.description.setText(mData.get(position).getDescription());
        //holder.id.setText(mData.get(position).getId());


        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(options).into(holder.AnimeThumbnail);

        //click one item then parse the data to acticity
        holder.view_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,AnimeActivity.class);
                i.putExtra("anime_placename",mData.get(position).getPlacename());
                i.putExtra("anime_placedescription",mData.get(position).getDescription());
                i.putExtra("anime_imageurl",mData.get(position).getImage_url());
                i.putExtra("anime_reviews",mData.get(position).getRating()+" Reviews");
                i.putExtra("anime_id",mData.get(position).getId());
                i.putExtra("anime_address",mData.get(position).getLocation());

                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id,placename,rate,description;
        ImageView AnimeThumbnail;
        LinearLayout view_Layout;


        public MyViewHolder(View itemView) {
            super(itemView);
            placename = itemView.findViewById(R.id.locationnamelist);
            rate = itemView.findViewById(R.id.reviwes);
            description = itemView.findViewById(R.id.locationdeslist);
            AnimeThumbnail = itemView.findViewById(R.id.locationimnagelist);
            id=itemView.findViewById(R.id.listlocationid);
            view_Layout=itemView.findViewById(R.id.view_container);
        }
    }
}
