package com.rsmouki.zed.tp3;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rsmouki.zed.tp3.db.Etablissement;
import com.rsmouki.zed.tp3.db.MyDatabase;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private  static final String TAG="Maps settings";

    private GoogleMap mMap;
    private String[] myDataset;
    MyDatabase itemInstance;
    double lat = 48.864716; double lng = 2.349014; String label = "Paris"; String desc = "this is paris";


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

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        desc = getIntent().getStringExtra("descr");
        label = getIntent().getStringExtra("title");

        itemInstance = Room.databaseBuilder(this,
                MyDatabase.class, "Etablissement")
                .allowMainThreadQueries()
                .build();
        List<Etablissement> etb = itemInstance.etabDao().getEtablissement();
        for(Etablissement item : etb) {
            if(item.getnom().equals(label)&&item.getdesc().equals(desc))
            {
                lat = item.getLat();
                lng = item.getLng();
            }
        }

        setCameraPosition();
        //setCameraPosition();

    }

    void setCameraPosition(){
        LatLng position = new LatLng(lat,lng);
        mMap.addMarker(new
                MarkerOptions().position(position).title(label).snippet(desc));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
    }
}
