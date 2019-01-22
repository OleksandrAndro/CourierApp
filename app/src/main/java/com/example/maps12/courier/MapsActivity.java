package com.example.maps12.courier;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DBFunction db = new DBFunction(this);

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

        Intent intent = getIntent();
        int mID_DB = intent.getIntExtra("ID_db",1);

        db.open();

        //create GeoPoint from data from BD
            Cursor cursor = db.getById(mID_DB);
            cursor.moveToFirst();
            double x = cursor.getDouble(cursor.getColumnIndex(db.GPS_ALTITUDE));
            double y = cursor.getDouble(cursor.getColumnIndex(db.GPS_LONGITUDE));
        cursor.close();




        // Add a marker in Sydney and move the camera
        LatLng client = new LatLng(x, y);
        mMap.addMarker(new MarkerOptions().position(client).title("Our client"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(client));
        //set Zoom for camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(client,15));


        //set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMyLocationEnabled(true);
    }
}
