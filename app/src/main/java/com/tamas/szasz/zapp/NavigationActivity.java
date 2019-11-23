package com.tamas.szasz.zapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.StationsUpdater;
import com.tamas.szasz.zapp.main.fragments.HomeFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{
    private GoogleMap mMap;
    private boolean permissions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setSettingsOnClick();
        checkPermissions();
        setUpMap();
        StationsUpdater.getInstance().setContext(this);

    }

    private void setSettingsOnClick() {
        ImageView imageView = findViewById(R.id.act_navigation_IV_Settings);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSettingsActivity = new Intent(NavigationActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
            }
        });
    }


    private void setUpMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(this.permissions) {
            addUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            for(int i = 0 ; i < permissions.length;i++)
                switch (permissions[i]){
                    case Manifest.permission.ACCESS_COARSE_LOCATION:

                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                            return;
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                            return;
                        else {
                            addUserLocation();
                        }
                        break;
                }
        } else {
            return;
        }
    }

    public void refreshMap(){
        ArrayList<LatLng> stationPoints = StationHandler.getInstance().getPositions();

        if(mMap != null){

            for(LatLng pos : stationPoints){

                mMap.addMarker(new MarkerOptions().position(pos));

            }


        }

    }

    private void addUserLocation(){
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng coordinate = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 16);
            mMap.animateCamera(yourLocation);
        }
    }
    @Override
    public void onMapLongClick(LatLng point) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Selected Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        Spinner selectMenu = new Spinner(this);
        String[] options = new String[]{"option1","option2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,options);
        selectMenu.setAdapter(adapter);
        selectMenu.setEnabled(true);
    }
    private void checkPermissions() {
        if (!isFineLocationPermissionGranted() || !isCoarseLocationPermissionGranted()){
            this.permissions = false;
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        } else {
            this.permissions = true;
        }
    }

    public boolean isFineLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isCoarseLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
