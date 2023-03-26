package com.example.weatherapp.city;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.ItemTouchHelperListener;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.RecyclerViewItemTouchHelper;
import com.example.weatherapp.search.SearchActivity;
import com.example.weatherapp.Weather;
import com.example.weatherapp.WeatherAdapter;


import org.json.JSONException;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity implements ItemTouchHelperListener {

    SearchView searchView;
    private RecyclerView lvWeather;
    //vi du lieu api thay doi nen dung 2 list
    //list de sd adapter vao list view
    private static final ArrayList<Weather> listWeather=new ArrayList<>();
    //list de luu ten thanh pho va lay du lieu de mo len api se thay doi
    private ArrayList<CityData> listData=new ArrayList<>();
    public static int run=0;//sinh id cho csdl city
    private WeatherAdapter wadap;
    private CityDB db;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        //set supportbar(back btn)
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        //
        lvWeather=findViewById(R.id.lst_city);
        wadap=new WeatherAdapter(this,listWeather);
        lvWeather.setAdapter(wadap);

        // swipetodelete
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        lvWeather.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        lvWeather.addItemDecoration(itemDecoration);

        ItemTouchHelper.SimpleCallback simpleCallback=new RecyclerViewItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(lvWeather);
        //sqldata
        //khoi tao
        db=new CityDB(this,"SQLCityDB",null,1);
        listData=db.getAllCity();
        for (int i = listWeather.size(); i < listData.size(); i++) {
            getCurrentWeather(listData.get(i).getCity());

        }
        if(listData.size()>=1){
        run=listData.get(listData.size()-1).getId();
        }else run=0;
        //get city form search act
        Intent intent=getIntent();
        String city=intent.getStringExtra("EXTRA_MESSAGE");
        //searchview event
        searchView=(SearchView)findViewById(R.id.search_bar_1);
        searchView.setOnSearchClickListener(v -> {
            Intent i= new Intent(CityActivity.this, SearchActivity.class);
            Pair pair =new Pair<View,String>(searchView,"searchtransition");
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(CityActivity.this,pair);
            startActivity(i,options.toBundle());
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getCurrentWeather(city);
                db.addCity(new CityData(run+1,city));

                Log.d("test insert",""+run);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //
        //send search
        if(city!=null) {
            Intent intent_tomain=new Intent(this, MainActivity.class);
            intent_tomain.putExtra("EXTRA_MESSAGE",city);
            startActivity(intent_tomain);
            searchView.setQuery(city,true);

        }
    }
    public void getCurrentWeather(String data){
        String url="https://api.weatherapi.com/v1/forecast.json?key=18a482f9b21f4954952173441232003&q="+data+"&days=1&aqi=no&alerts=no";
        Log.d("test",url);
        RequestQueue requestQueue = Volley.newRequestQueue(CityActivity.this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url ,null,
                response -> {
                    try {
                        Log.d("test",response.toString());
                        String temp = response.getJSONObject("current").getString("temp_c");
                        String detail=response.getJSONObject("current").getJSONObject("condition").getString("text");
//                        String win = response.getJSONObject("current").getString("wind_mph") ;
//                        String uv =  response.getJSONObject("current").getString("uv") ;
                        listWeather.add(new Weather(data,detail,temp+"℃"));
                        wadap.setData(listWeather);
                        wadap.notifyDataSetChanged();
                        lvWeather.setAdapter(wadap);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(CityActivity.this, "sai", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {

        if(viewHolder instanceof WeatherAdapter.WeatherViewHolder){
            int index_delete=viewHolder.getAdapterPosition();
            Log.d("test_delete",""+index_delete);
            //remove
            db.deleteCity(listData.get(index_delete).getId());
            Log.d("test_delete",""+listData.get(index_delete).getId());
            wadap.removeItem(index_delete);


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wadap!=null){
            wadap.release();
        }
    }
}