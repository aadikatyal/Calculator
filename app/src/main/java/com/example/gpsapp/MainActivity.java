package com.example.gpsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity
{
    private LocationManager locationManager;
    private TextView tvCoordinates, tvAddress, tvDistance, tvTime;
    private List<Address> addressList;
    private Location lastLocation;
    private double distanceMeters;
    private long startTime;
    private static final int REQUEST_LOC = 0;
    private LocationListener locationListener;
    private DecimalFormat df = new DecimalFormat("###.###");

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCoordinates = findViewById(R.id.tvCoordinates);
        tvAddress = findViewById(R.id.tvAddress);
        tvDistance = findViewById(R.id.tvDistance);
        tvTime = findViewById(R.id.tvTime);
        startTime = SystemClock.elapsedRealtime();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
        {

        }
        else
        {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOC);
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOC);
        }

        locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(@NonNull Location location)
            {
                tvCoordinates.setText("Latitude: " + df.format(location.getLatitude()) + "\nLongitude: " + df.format(location.getLongitude()));
                getAddress(location.getLatitude(), location.getLongitude());

                if (lastLocation == null)
                {
                    lastLocation = location;
                }

                distanceMeters += location.distanceTo(lastLocation);

                if(lastLocation == location)
                {
                    tvDistance.setText("Total Distance: " + 0.0 + " miles");
                    tvTime.setText("Elapsed Time: " + 0.0 + " seconds");
                }
                else
                {
                    double dist = distanceMeters/1609.334;
                    tvDistance.setText("Total Distance: " + df.format(dist) + " miles");

                    double elapsedTime = (double) (SystemClock.elapsedRealtime() - startTime)/1000.0;
                    tvTime.setText("Elapsed Time: "+ df.format(elapsedTime) + " seconds");
                }
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider)
            {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider)
            {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
    }

    public void getAddress(double lat, double lon)
    {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try
        {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList != null)
            {
                Address returnedAddress = addressList.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for(int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++)
                {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }

                strAdd = strReturnedAddress.toString();
            }
            tvAddress.setText(strAdd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d("TAG", "BEFORE SWITCH");
        switch (requestCode)
        {
            case REQUEST_LOC:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, locationListener);
                }
            }
        }
    }
}