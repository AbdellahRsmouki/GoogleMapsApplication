package com.rsmouki.zed.tp3.GererItem;

import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rsmouki.zed.tp3.MainActivity;
import com.rsmouki.zed.tp3.MapsActivity;
import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.db.Etablissement;
import com.rsmouki.zed.tp3.db.MyDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocalisation extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private  static final String TAG="GetLocation";

    private GoogleMap mMap;
    private String[] myDataset;
    MyDatabase itemInstance;
    private FusedLocationProviderClient mFusedLocationClient;
    double lat = 48.864716; double lng = 2.349014; String label = "Paris"; String desc = "this is paris";
    static Double[] position= new Double[2];

    MyDatabase EInstantce;
    Etablissement Etabl= new Etablissement();
    FloatingActionButton fab;
    String myNewLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_localisation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.etablocalisation);
        mapFragment.getMapAsync(this);


        Etabl.nom = getIntent().getStringExtra("name");
        Etabl.desc = getIntent().getStringExtra("desc");
        Etabl.image = AjouterItem.etablissement.image;

        fab = findViewById(R.id.fab);

    }

    public void ajouter() {
        Log.d(TAG, "ajouter position");


        fab.setEnabled(false);

        if(!checkDatabase())
        {
            onAddingEtabFailed();
            return;
        }

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onAddingEtabFailed() {
        Toast.makeText(getBaseContext(), "adding etablissement failed", Toast.LENGTH_LONG).show();

        fab.setEnabled(true);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        setCameraPosition();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {


            @Override
            public void onMapLongClick(LatLng arg0) {

                mMap.addMarker(new MarkerOptions()
                        .position(arg0)
                        .title("new location")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                Double lati = (arg0.latitude);
                Double loni = (arg0.longitude);
                String aLatPlace = lati.toString();
                String aLongPlace = loni.toString();

                Geocoder myGeo = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> myAddresses = myGeo.getFromLocation(lati, loni, 1);

                    if (myAddresses != null && myAddresses.size() > 0) {

                        myNewLocal = myAddresses.get(0).getAddressLine(0) + ", "
                                + myAddresses.get(0).getAddressLine(1);

                    } else {

                        myNewLocal = "";

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Etabl.lat = lati;
                Etabl.lng = loni;
                ajouter();
            }
        });

    }

    void setCameraPosition(){
        LatLng position = new LatLng(lat,lng);
        mMap.addMarker(new
                MarkerOptions().position(position).title(label).snippet(desc));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
    }

    boolean checkDatabase()
    {
        EInstantce = Room.databaseBuilder(this,
                MyDatabase.class, "Etablissement")
                .allowMainThreadQueries()
                .build();
        Log.d(TAG, "checkdatabase");
        List<Etablissement> etab =
                EInstantce.etabDao().getEtablissement(Etabl.nom);
        Log.d(TAG, "etablissementlist");
        for(Etablissement et : etab) {
            Log.d(TAG, et.nom+ et.desc);
            if(Etabl.getnom().equals(et.nom))
            {
                Log.d(TAG, "etablissementdeja existe");
                Toast.makeText(this,"this etablissement is already exist",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        EInstantce.etabDao().addEtablissement(Etabl);
        return true;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this, latLng.latitude+" "+latLng.longitude, Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(
                        Double.parseDouble(""+latLng.latitude),
                        Double.parseDouble(""+latLng.longitude)))
                .title("Your position"));

    }
}
