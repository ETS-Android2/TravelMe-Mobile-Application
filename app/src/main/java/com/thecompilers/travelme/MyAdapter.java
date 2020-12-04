package com.thecompilers.travelme;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//load image from images in databse

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ImageViewHolder>{
    RequestOptions options ;
    private Context mContext ;
    private List<ModelImage> mData ;

    public MyAdapter(Context mContext, List<ModelImage> mData) {
        this.mContext = mContext;
        this.mData = mData;
        options=new RequestOptions().centerCrop();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custome_imagelayout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imagename.setText(mData.get(position).getLocation());
        Glide.with(mContext).load(mData.get(position).getImageurl()).apply(options).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView imagename;
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imagename=itemView.findViewById(R.id.imagename);
            imageView=itemView.findViewById(R.id.imagesview);

        }
    }
}

