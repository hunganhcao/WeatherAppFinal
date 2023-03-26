package com.example.weatherapp.search;



import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.city.CityActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    ListView lvCity;
    ArrayAdapter<String> adapter;
    SearchView searchView2;

    ArrayList<String> lst_city_temp=new ArrayList<>();
    public static  int RETURN_INF=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        addControls();
        searchView2=findViewById(R.id.search_bar_2);

        //filter tìm kiếm
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(SearchActivity.this, CityActivity.class);
                intent.putExtra("EXTRA_MESSAGE",query);
                Pair pair =new Pair<View,String>(searchView2,"searchtransition");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,pair);
                startActivity(intent,options.toBundle());
                searchView2.setQuery("",false);
                searchView2.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                makelistSuggest(newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String val =(String) parent.getItemAtPosition(position);
                searchView2.setQuery(val,true);
            }
        });
    }



    private void addControls() {
        lvCity=findViewById(R.id.lv_city);
        lst_city_temp.addAll(Arrays.asList(getResources().getStringArray(R.array.arrtinhthanh)));
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lst_city_temp);
        lvCity.setAdapter(adapter);
    }
    public void makelistSuggest(String data){
        lst_city_temp.clear();
        //adapter.clear();
        String url="https://api.weatherapi.com/v1/search.json?key=18a482f9b21f4954952173441232003&q="+data;
        Log.d("test",url);
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("test",response.toString());
                try {
                    for (int i = 0; i < response.length(); i++)
                    {
                        String city = response.getJSONObject(i).getString("name");
                        lst_city_temp.add(city);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.clear();
                adapter.addAll(lst_city_temp);
                adapter.notifyDataSetChanged();
                lvCity.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "sai", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}