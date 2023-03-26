package com.example.weatherapp.daydetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaydetailActivity extends AppCompatActivity {
    static final String API_KEY = "31650388f3b94ced879165751231303";

    private RecyclerView rcv_daydetail;
    private DaydetailAdapter daydetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daydetail);
        rcv_daydetail = findViewById(R.id.rcv_daydetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        daydetailAdapter = new DaydetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_daydetail.setLayoutManager(linearLayoutManager);
        rcv_daydetail.setAdapter(daydetailAdapter);
        Intent i =getIntent();
        String getcity=i.getStringExtra("City_Mess");
        if(getcity!=null) {
            Toast.makeText(this, getcity, Toast.LENGTH_SHORT).show();
            getJsonDaydetail(getcity);
        }



    }
        public void getJsonDaydetail(String city){
            String url ="http://api.weatherapi.com/v1/forecast.json?key="+API_KEY+"&q="+city+"&days=7&aqi=yes&alerts=yes";
            RequestQueue requestQueuedaydetail = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequestdaydetail = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        List<Daydetail> lstdaydetail = new ArrayList<>();
                        JSONObject forecast = response.getJSONObject("forecast");
                        JSONArray forecastday = forecast.getJSONArray("forecastday");
                        for (int i =0;i <forecastday.length();i++){
                            JSONObject item = forecastday.getJSONObject(i);
                            String date = item.getString("date");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = null;
                            try {
                                date1 = dateFormat.parse(date);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault());
                            String datedetail = dayFormat.format(date1);
                            JSONObject day = item.getJSONObject("day");
                            String maxtemp_c = day.getString("maxtemp_c");
                            String mintemp_c = day.getString("mintemp_c");
                            String maxwind_mph = day.getString("maxwind_mph");
                            String daily_chance_of_rain = day.getString("daily_chance_of_rain");
                            String uv = day.getString("uv");
                            lstdaydetail.add(new Daydetail(datedetail,maxtemp_c+"°C",mintemp_c+"°C",maxwind_mph+" mph",uv,
                                    daily_chance_of_rain+"%"));

                        }
                        daydetailAdapter = new DaydetailAdapter(lstdaydetail);
                        rcv_daydetail.setAdapter(daydetailAdapter);
                        daydetailAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DaydetailActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueuedaydetail.add(jsonObjectRequestdaydetail);
        }

}