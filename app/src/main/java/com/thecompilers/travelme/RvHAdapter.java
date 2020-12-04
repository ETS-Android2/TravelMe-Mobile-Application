package com.thecompilers.travelme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


//load the to recycler view which in trip history
public class RvHAdapter extends RecyclerView.Adapter<RvHAdapter.MyViewHolder> {
    private Context mContext ;
    private List<historyPlan> mData ;

    public RvHAdapter(Context mContext, List<historyPlan> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.triphistory,parent,false);

        return new RvHAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvHAdapter.MyViewHolder holder, int position) {
        holder.tname.setText(mData.get(position).getName()+" Tour");
        holder.tenddate.setText(mData.get(position).getStartdate()+" to "+mData.get(position).getEnddate());
        holder.tnote.setText(mData.get(position).getNote());
        holder.tlocation.setText(mData.get(position).getLocation());

        final int id=mData.get(position).getId();
        final String name=mData.get(position).getName();
        final String date=mData.get(position).getStartdate()+" to "+mData.get(position).getEnddate();
        final String location=mData.get(position).getLocation();
        final String note=mData.get(position).getNote();
        final String placses=mData.get(position).getPlaces();

        holder.view_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load the dailog fragment click recycler view item
                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                TripHistoryDialog dialog=new TripHistoryDialog(id,name,date,location, note,placses);
                dialog.show(manager, "Edit Profle");

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id,tname,tstartdate,tenddate,tlocation,tnote,tplaces;
        CardView view_Layout;


        public MyViewHolder(View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.tripname);
            tenddate = itemView.findViewById(R.id.datetrip);
            tnote = itemView.findViewById(R.id.tripNote);
            tlocation=itemView.findViewById(R.id.taddress);
            id=itemView.findViewById(R.id.tid);
            view_Layout=itemView.findViewById(R.id.clickcard);
        }
    }
}
