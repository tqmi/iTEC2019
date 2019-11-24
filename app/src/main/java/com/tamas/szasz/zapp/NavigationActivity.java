package com.tamas.szasz.zapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.StationsUpdater;
import com.tamas.szasz.zapp.Stations.res.PointF;
import com.tamas.szasz.zapp.Stations.retrofit_threads.stations.StationsAddThread;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.StationsUpdater;
import com.tamas.szasz.zapp.Stations.retrofit_threads.stations.StationsListThread;
import com.tamas.szasz.zapp.main.fragments.HomeFragment;
import com.tamas.szasz.zapp.main.fragments.model.CustomPopupWindow;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener{
    private GoogleMap mMap;
    private boolean permissions = false;
    private CustomPopupWindow mPopWindow;
    private TextInputEditText mTIETTotalSocket, mTIETName;
    private TextInputLayout mTILTotalSocket, mTILName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setSettingsOnClick();
        checkPermissions();
        setUpMap();
        StationsUpdater.getInstance().setContext(this);
        StationHandler.getInstance().setContext(this);
        StationsUpdater.getInstance().start();

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
        Marker marker;
        marker = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Selected Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        showPopupWindows(this.findViewById(R.id.act_navigation_LL), point,marker);

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




    private void showPopupWindows(View view, LatLng point, final Marker marker) {
        final View _inflatedView = LayoutInflater.from(this).inflate(R.layout.popup_add_station, null, false);
        // get device size
        Display _display = this.getWindowManager().getDefaultDisplay();
        final Point _size = new Point();
        _display.getSize(_size);

        mPopWindow = new CustomPopupWindow(_inflatedView, _size.x - 50, _size.y /4 + 24, true, this, view);
        mPopWindow.setLocation(view, Gravity.TOP, 0, 0);
        mPopWindow.setAnimationStyle(R.style.PopupAnimationTop);
        setUpEditTexts(_inflatedView);
        setUpLLayouts(_inflatedView);
        setUpPopupButtons(_inflatedView, point);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){

            @Override
            public void onDismiss() {
                marker.remove();
            }
        });
    }



    private void setUpPopupButtons(View inflatedView, final LatLng point) {
        Button buttonAddStation = inflatedView.findViewById(R.id.popup_add_station_BTN_add_station);
        buttonAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notEmptyFields()) {
                    StationsAddThread stationsAddThread = new StationsAddThread(mTIETName.getText().toString(),
                            Integer.parseInt(mTIETTotalSocket.getText().toString()),
                            Integer.parseInt(mTIETTotalSocket.getText().toString()),
                            new PointF(false, point.latitude, point.longitude));
                    stationsAddThread.run();
                    try {
                        stationsAddThread.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mPopWindow.dismiss();
                }
            }
        });
    }

    private boolean notEmptyFields() {
        if(mTIETName.getText().toString().equals("")) {
            mTILName.setError("Field required");
            return false;
        }
        mTILName.setErrorEnabled(false);
        if(mTIETTotalSocket.getText().toString().equals("")) {
            mTILTotalSocket.setError("Field required");
            return false;
        }
        mTILTotalSocket.setErrorEnabled(false);
        return true;
    }

    private void setUpLLayouts(View inflatedView) {
        mTILName = inflatedView.findViewById(R.id.popup_add_station_TIL_station_name);
        mTILTotalSocket = inflatedView.findViewById(R.id.popup_add_station_TIL_total_sockets);
    }

    private void setUpEditTexts(View inflatedView) {
        mTIETName = inflatedView.findViewById(R.id.popup_add_station_TIET_station_name);
        mTIETTotalSocket = inflatedView.findViewById(R.id.popup_add_station_TIET_total_sockets);
    }
    public void refreshStations(View view){

        StationsListThread stationsListThread = new StationsListThread();
        stationsListThread.run();

        try {
            stationsListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Marker addMarker(LatLng latLng){
        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.ic_marker_station);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        return  mMap.addMarker(new MarkerOptions().position(latLng).visible(true).icon(bitmapDescriptor));
    }

}
