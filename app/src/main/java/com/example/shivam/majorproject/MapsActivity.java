package com.example.shivam.majorproject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TinyDB tinyDB;
    double current_lat, current_lon, start_lat, start_lon, end_lat, end_lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        tinyDB = new TinyDB(MapsActivity.this);

        Transaction t = tinyDB.getObject("transaction_key_db", Transaction.class);
        String start[] = t.getStarting_location().split("_");
        String end[] = t.getEnding_location().split("_");
        String current[] = t.getCurrent_employee_location().split("_");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        start_lat = Double.valueOf(start[0]);
        start_lon = Double.valueOf(start[1]);

        end_lat = Double.valueOf(end[0]);
        end_lon = Double.valueOf(end[1]);

        current_lat = Double.valueOf(current[0]);
        current_lon = Double.valueOf(current[1]);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng start = new LatLng(start_lat,start_lon);
        LatLng end = new LatLng(end_lat,end_lon);
        LatLng current = new LatLng(current_lat,current_lon);
        mMap.addMarker(new MarkerOptions().position(start).title("Check In"));
        mMap.addMarker(new MarkerOptions().position(end).title("Current"));
        mMap.addMarker(new MarkerOptions().position(current).title("Check Out"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));

    }
}
