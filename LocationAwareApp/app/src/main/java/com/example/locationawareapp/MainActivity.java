package com.example.locationawareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    TextView longitudeTV, latitudeTV, altitudeTV, accuracyTV, addressTV;

    //Second step: Declare the location manager and location listener
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longitudeTV = findViewById(R.id.longitudeTV);
        latitudeTV = findViewById(R.id.latitudeTV);
        altitudeTV = findViewById(R.id.altitudeTV);
        accuracyTV = findViewById(R.id.accuracyTV);
        addressTV = findViewById(R.id.addressTV);

        //Third Step - instantiation of location manage and listener
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "LocationChanged: " + location);
                longitudeTV.setText(location.getLongitude()+"");
                latitudeTV.setText(location.getLatitude()+"");
                altitudeTV.setText(location.getAltitude()+"");
                accuracyTV.setText(location.getAccuracy()+"");
                //addressTV.setText()
            }
        };

        //Continuation of the third step - request the permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE);
    }

    ;

//Fourth Step - after granting or denying the permission, this method is called

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(
                    locationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener
            );
        }
    }
}