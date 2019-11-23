package com.tamas.szasz.zapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addUserLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            for(int i = 0 ; i < permissions.length;i++)
                switch (permissions[i]){
                    case Manifest.permission.ACCESS_COARSE_LOCATION:

                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                            return;

                        break;
                    case Manifest.permission.ACCESS_FINE_LOCATION:

                        if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                            return;

                        break;
                }
        } else {
            return;
        }
        addUserLocation();
    }



    private void addUserLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//
//                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
//                CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
//                mMap.clear();
//
//                MarkerOptions mp = new MarkerOptions();
//
//                mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
//
//                mp.title("my position");
//
//                mMap.addMarker(mp);
//                mMap.moveCamera(center);
//                mMap.animateCamera(zoom);
//
//            }
//        });
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

}
