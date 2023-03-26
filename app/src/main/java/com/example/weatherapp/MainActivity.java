package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.city.CityActivity;
import com.example.weatherapp.city.CityDB;
import com.example.weatherapp.city.CityData;
import com.example.weatherapp.daydetail.DaydetailActivity;
import com.example.weatherapp.detail.Detail;
import com.example.weatherapp.detail.DetailAdapter;

import com.example.weatherapp.mainweather.MainWeather;
import com.example.weatherapp.mainweather.MainWeatherAdapter;
import com.example.weatherapp.setting.SettingActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final String API_KEY = "31650388f3b94ced879165751231303";
    ImageView Ivadd, iv_weatherimg,iv_setting;
    Button btndubao;
    TextView tv_tentp, tv_day,tv_temp,tv_condition;
    private RecyclerView rcvmainweather,rcv_mainweatherdetail;
    private MainWeatherAdapter mainWeatherAdapter;
    private DetailAdapter mainWeatherAdapterDetail;
    private CityDB cityDB;
    private ArrayList<CityData> listData=new ArrayList<>();
    public static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ivadd = findViewById(R.id.Ivadd);
        btndubao = findViewById(R.id.btndubao);
        rcvmainweather = findViewById(R.id.rcvmainweather);
        rcv_mainweatherdetail = findViewById(R.id.rcv_mainweatherdetail);
        iv_weatherimg = findViewById(R.id.iv_weatherimg);
        iv_setting = findViewById(R.id.Iv_setting);
        tv_tentp = findViewById(R.id.txtTenTP);
        tv_day = findViewById(R.id.tv_day);
        tv_temp = findViewById(R.id.txttemp);
        tv_condition = findViewById(R.id.txtcondition);
        mainWeatherAdapter = new MainWeatherAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvmainweather.setLayoutManager(linearLayoutManager);
        rcvmainweather.setAdapter(mainWeatherAdapter);
        //
        mainWeatherAdapterDetail = new DetailAdapter(this);
        LinearLayoutManager linearLayoutManagerdetail = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcv_mainweatherdetail.setLayoutManager(linearLayoutManagerdetail);
        rcv_mainweatherdetail.setAdapter(mainWeatherAdapterDetail);
        //
        cityDB=new CityDB(this,"SQLCityDB",null,1);
        listData=cityDB.getAllCity();
        //
        Intent i =getIntent();
        String getcity=city;
        if(i.getStringExtra("EXTRA_MESSAGE")!=null){
            getcity=i.getStringExtra("EXTRA_MESSAGE");
        }
        if(getcity!=null) {
            city=getcity;
            Toast.makeText(this, getcity, Toast.LENGTH_SHORT).show();
            setMainData(city);
        }else{
            int s=listData.size();
            if(s>=1){
                city=listData.get(s-1).getCity();
                setMainData(city);
            }
            else{
                //get location city
                //or
                city="Ha Noi";
                setMainData(city);
            }
        }
        //
        Ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        });
//        iv_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
//                startActivity(intent);
//            }
//        });

        btndubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DaydetailActivity.class);
                intent.putExtra("City_Mess",city);
                startActivity(intent);
            }
        });
    }

    public void getJsonWeatherday(String city){
        String url ="http://api.weatherapi.com/v1/forecast.json?key="+API_KEY+"&q="+city+"&days=1&aqi=yes&alerts=yes";
        RequestQueue requestQueuedetail = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequestdetail = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            List<Detail> detailLists = new ArrayList<>();
                            JSONObject forecast = response.getJSONObject("forecast");
                            JSONArray forecastday = forecast.getJSONArray("forecastday");
                            for (int i =0; i<forecastday.length();i++){
                                JSONObject item = forecastday.getJSONObject(i);
                                JSONArray hour = item.getJSONArray("hour");
                                for (int j = 0;j<hour.length();j++){
                                    JSONObject itemdetail = hour.getJSONObject(j);
                                    String time = itemdetail.getString("time");
                                    String hours = time.substring(11,16);
                                    String temp_c = itemdetail.getString("temp_c");
                                    JSONObject condition = itemdetail.getJSONObject("condition");
                                    String icon = condition.getString("icon");
                                    String urlIcon = "http:"+icon;
                                    detailLists.add(new Detail(hours,urlIcon,temp_c+"°C"));
                                }

                            }
                            mainWeatherAdapterDetail = new DetailAdapter(detailLists);
                            rcv_mainweatherdetail.setAdapter(mainWeatherAdapterDetail);
                            mainWeatherAdapterDetail.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueuedetail.add(jsonObjectRequestdetail);
    }
    public void getJsonWeather(String city){
        String url ="http://api.weatherapi.com/v1/forecast.json?key="+API_KEY+"&q="+city+"&days=7&aqi=yes&alerts=yes";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //lấy tên thành phố
                            JSONObject location = response.getJSONObject("location");
                            String name = location.getString("name");
                            tv_tentp.setText(name);
                            String lat = location.getString("lat");
                            String lon = location.getString("lon");
                            iv_setting.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intentmap = new Intent(MainActivity.this, SettingActivity.class);
                                    intentmap.putExtra("city_Lat",lat);
                                    intentmap.putExtra("city_Lon",lon);
                                    startActivity(intentmap);
                                }
                            });

                            String localtime = location.getString("localtime");
                            tv_day.setText(localtime);
                            //lấy thời tiết hiện tại
                            JSONObject current = response.getJSONObject("current");
                            String temp_c =current.getString("temp_c");
                            Double tempc = Double.parseDouble(temp_c);
                            tv_temp.setText(""+tempc+"°C");
                            //
                            JSONObject condition = current.getJSONObject("condition");
                            String text = condition.getString("text");
                            tv_condition.setText(text);
                            String Icon = condition.getString("icon");
                            String urlIcon = "http:"+Icon;
                            Picasso.get().load(urlIcon).into(iv_weatherimg);
                            // hiện thi dữ liệu lên RecycleView
                            List<MainWeather> mainWeatherList = new ArrayList<>();
                            JSONObject forecast = response.getJSONObject("forecast");
                            JSONArray forecastday = forecast.getJSONArray("forecastday");
                            for(int i = 0; i<forecastday.length();i++){
                                JSONObject item = forecastday.getJSONObject(i);
                                String date = item.getString("date");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date1 = null;
                                try {
                                    date1 = dateFormat.parse(date);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                                String date_custom = dayFormat.format(date1);
                                //String weekdayName = dayFormat.format(date);
                                JSONObject day = item.getJSONObject("day");
                                String mintemp_c = day.getString("mintemp_c");
                                String maxtemp_c = day.getString("maxtemp_c");
                                //String avghumidity = day.getString("avghumidity");
                                String daily_chance_of_rain = day.getString("daily_chance_of_rain");
                                JSONObject condition1 = day.getJSONObject("condition");
                                String Icon1 = condition1.getString("icon");
                                String urlIcon1  = "http:"+Icon1;
                               mainWeatherList.add(new MainWeather(urlIcon1,date_custom,mintemp_c+"°C-"+maxtemp_c+"°C"));

                            }
                            mainWeatherAdapter = new MainWeatherAdapter(mainWeatherList);
                            rcvmainweather.setAdapter(mainWeatherAdapter);
                            mainWeatherAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
         requestQueue.add(jsonObjectRequest);
    }

    public void setMainData(String city){
        getJsonWeather(city);
        getJsonWeatherday(city);
    }
}