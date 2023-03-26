package com.example.weatherapp.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.example.weatherapp.map.MapActivity;
import com.example.weatherapp.R;

public class SettingActivity extends AppCompatActivity {
    private Button btn_open, btn_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_open = findViewById(R.id.btn_setting);
        btn_map = findViewById(R.id.btn_map);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSetting();
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMap();
            }
        });
    }

    private void OpenMap() {
        Intent intent = getIntent();
        String alat = intent.getStringExtra("city_Lat");
        String alon = intent.getStringExtra("city_Lon");
        Intent intentmap = new Intent(SettingActivity.this, MapActivity.class);
        intentmap.putExtra("alat",alat);
        intentmap.putExtra("alon",alon);
        startActivity(intentmap);


    }

    private void OpenSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }

}