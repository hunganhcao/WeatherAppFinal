package com.example.weatherapp.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.weatherapp.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        Context context = this.getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(15.0);
        //GeoPoint startpoint = new GeoPoint(21.03,105.85);
        //mapView.getController().setCenter(startpoint);
        requestPermissionsIfnecessary(new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET
        });
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(this, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        Intent intent = getIntent();
        String alat = intent.getStringExtra("alat");
        String alon = intent.getStringExtra("alon");

        GeoPoint startpoint = new GeoPoint(Double.valueOf(alat),Double.valueOf(alon));
        Marker marker = new Marker(mapView);
        marker.setPosition(startpoint);
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_CENTER);
        mapView.getOverlays().add(marker);
        mapView.getController().setCenter(startpoint);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissiontorequest = new ArrayList<>();
        for (int i = 0 ;i<grantResults.length;i++){
            permissiontorequest.add(permissions[i]);
        }
        if(permissiontorequest.size()>0){
            ActivityCompat.requestPermissions(this,permissiontorequest.toArray(new String[0]),REQUEST_CODE);
        }

    }

    private void requestPermissionsIfnecessary(String[] permissions) {
        ArrayList<String> permissiontorequest = new ArrayList<>();
        for (String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissiontorequest.add(permission);
            }
        }
        if(permissiontorequest.size()>0){
            ActivityCompat.requestPermissions(this,permissiontorequest.toArray(new String[0]),REQUEST_CODE);
        }
    }
}