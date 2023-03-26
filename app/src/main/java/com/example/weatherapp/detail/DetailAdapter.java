package com.example.weatherapp.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{

    private Context context;
    private List<Detail> detailList;

    public DetailAdapter(Context context) {
        this.context = context;
    }

    public DetailAdapter(List<Detail> detailLists) {
        detailList = detailLists;
    }


    public void setData(List<Detail> list){
        this.detailList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent,false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Detail detail = detailList.get(position);
        if(detail == null){
            return;
        }
        Picasso.get().load(detail.getImg()).into(holder.iv_weatherdetail);
        holder.tv_detail.setText(detail.getTitle());
        holder.tv_hours.setText(detail.getHours());
    }

    @Override
    public int getItemCount() {
        if(detailList != null){
            return detailList.size();
        }
        return 0;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_weatherdetail;
        private TextView tv_detail,tv_hours;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_weatherdetail = itemView.findViewById(R.id.iv_weatherdetail);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            tv_hours = itemView.findViewById(R.id.tv_hours);
        }
    }
}
