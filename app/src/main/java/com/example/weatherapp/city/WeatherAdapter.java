package com.example.weatherapp.city;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>  {
    private ArrayList<Weather> data;
    private Context context;

    public WeatherAdapter(Context context, ArrayList<Weather> data) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather=data.get(position);
        if(weather==null){
            return;
        }
        holder.tvCity.setText(weather.getCity());
        holder.tvTemp.setText(weather.getTemp());
        holder.tvDt.setText(weather.getDetail());
        holder.foreGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToMain(weather);
            }
        });
    }

    private void onClickGoToMain(Weather weather) {
        Intent i=new Intent(context, MainActivity.class);
        i.putExtra("EXTRA_MESSAGE",weather.getCity());
        context.startActivity(i);
    }

    public void release(){
        context=null;
    }
    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        }
        return 0;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        TextView tvCity,tvDt,tvTemp;
        LinearLayout foreGround;


        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
             tvCity=itemView.findViewById(R.id.tv_city);
             tvDt=itemView.findViewById(R.id.tv_detail);
            tvTemp=itemView.findViewById(R.id.tv_temp);
            foreGround=itemView.findViewById(R.id.layout_foreground);

        }
    }
    public void setData(ArrayList<Weather> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public  void removeItem(int index){
        data.remove(index);
        notifyItemRemoved(index);
        //notifyItemRangeChanged(index,data.size());
    }
}
//public class WeatherAdapter extends BaseAdapter implements Filterable {
//
//    //Ngữ cảnh của ứng dụng sử dụng Adapter
//    private Activity activity;
//    //Nguồn dữ liệu cho Adapter
//    private ArrayList<Weather> data;
//    //Sao lưu nguồn dữ liệu cho Adapter
//    private ArrayList<Weather> databackup;
//    //Đối tượng inflater để tách layout
//    private LayoutInflater inflater;
//
//    public void setData(ArrayList<Weather> data) {
//        this.data = data;
//    }
//
//    public WeatherAdapter(Activity activity, ArrayList<Weather> data) {
//        this.activity = activity;
//        this.data = data;
//        inflater = (LayoutInflater)activity.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup parent) {
//
//        View v=convertView;
//        if(v==null)
//            v=inflater.inflate(R.layout.item_city,null);
//        TextView tvCity=v.findViewById(R.id.tv_city);
//        tvCity.setText(data.get(i).getCity());
//        TextView tvDt=v.findViewById(R.id.tv_detail);
//        tvDt.setText(data.get(i).getDetail());
//        TextView tvTemp=v.findViewById(R.id.tv_temp);
//        tvTemp.setText(data.get(i).getTemp()+"℃");
//
//        return v;
//    }
//    @Override
//    public Filter getFilter() {
//        Filter f = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                FilterResults fr = new FilterResults();
//                if(databackup==null)
//                    databackup = new ArrayList<>(data);
//                if(charSequence==null || charSequence.length()==0)
//                {
//                    fr.values=databackup;
//                    fr.count=databackup.size();
//                }
//                else{
//                    ArrayList<Weather> newdata=new ArrayList<>();
//                    for(Weather c:data)
//                        if(c.getCity().toLowerCase().contains(
//                                charSequence.toString().toLowerCase()))
//                            newdata.add(c);
//                    fr.values=newdata;
//                    fr.count=newdata.size();
//                }
//                return fr;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence,
//                                          FilterResults filterResults) {
//                ArrayList<Weather> tmp = (ArrayList<Weather>)filterResults.values;
//                data.clear();
//                for(Weather c:tmp)
//                    data.add(c);
//                notifyDataSetChanged();
//            }
//        };
//        return f;
//    }
//
//}
