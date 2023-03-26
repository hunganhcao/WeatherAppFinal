package com.example.weatherapp.daydetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.List;

public class DaydetailAdapter extends RecyclerView.Adapter<DaydetailAdapter.DaydetailViewHolder>{
    private Context context;
    private List<Daydetail> daydetailList;

    public DaydetailAdapter(Context context) {
        this.context = context;
    }

    public DaydetailAdapter(List<Daydetail> lstdaydetail) {
        daydetailList = lstdaydetail;
    }

    @NonNull
    @Override
    public DaydetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daydetail, parent,false);
        return new DaydetailAdapter.DaydetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaydetailViewHolder holder, int position) {
        Daydetail daydetail = daydetailList.get(position);
        if(daydetail == null){
            return;
        }
        holder.tv_date.setText(daydetail.getDate());
        holder.tv_maxtemp.setText(daydetail.getMaxtemp());
        holder.tv_mintemp.setText(daydetail.getMintemp());
        holder.tv_windspeed.setText(daydetail.getWindspeed());
        holder.tv_uv.setText(daydetail.getUv());
        holder.tv_rainchance.setText(daydetail.getRainchance());
    }

    @Override
    public int getItemCount() {
        if(daydetailList != null){
            return daydetailList.size();
        }
        return 0;
    }

    public class DaydetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date,tv_maxtemp, tv_mintemp, tv_windspeed, tv_uv, tv_rainchance;
        public DaydetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_maxtemp = itemView.findViewById(R.id.tv_maxtemp);
            tv_mintemp = itemView.findViewById(R.id.tv_mintemp);
            tv_windspeed = itemView.findViewById(R.id.tv_windspeed);
            tv_uv = itemView.findViewById(R.id.tv_uv);
            tv_rainchance = itemView.findViewById(R.id.tv_rainchance);


        }
    }
}
