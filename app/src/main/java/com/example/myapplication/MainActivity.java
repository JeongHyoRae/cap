package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    private TextView text;
    private TextView text2;
    String provider;
    double longitude;
    double latitude;
    LocationManager lm;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.txt);
        text2 = findViewById(R.id.txt2);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else{
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null){
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            provider = location.getProvider();
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            text.setText("???????????? : " + provider + "\n" + "?????? : " + longitude + "\n" + "?????? : " + latitude);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            provider = location.getProvider();
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            text.setText("???????????? : " + provider + "\n" + "?????? : " + longitude + "\n" + "?????? : " + latitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(37.145995272094034, 127.06707518461991);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("?????????");
        markerOptions.snippet("?????????");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        LatLng lo = new LatLng(37.14568778936869, 127.06749678277552);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.title("????????????");
        markerOption.snippet("??????");
        markerOption.position(lo);
        googleMap.addMarker(markerOption);

        LatLng loc = new LatLng(latitude, longitude);
        MarkerOptions markerOp = new MarkerOptions();
        markerOp.title("?????????");
        markerOp.snippet("???");
        markerOp.position(loc);
        googleMap.addMarker(markerOp);

        googleMap.setOnMarkerClickListener(this);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        text2.setText(marker.getTitle() + "\n" + marker.getPosition());
        //Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_SHORT).show();
        return true;
    }
}