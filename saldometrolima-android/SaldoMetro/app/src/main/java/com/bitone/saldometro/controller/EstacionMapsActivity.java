package com.bitone.saldometro.controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.StringTokenizer;

public class EstacionMapsActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_maps);
        setUpMapIfNeeded();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        cargarDatos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                cerrar();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }


        final int cont =0;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.e("" + cont + " : ", latLng.latitude + " , " + latLng.longitude);
            }
        });
    }


    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void cargarDatos(){
        int idEstacion= this.getIntent().getExtras().getInt("idEstacion");
        String nombrEstacion = this.getIntent().getExtras().getString("nombreEstacion");
        this.setTitle(nombrEstacion + " - Mapa");
        String coordenada= this.getIntent().getExtras().getString("coordenada");

        cargarCoordenadaEstacion(coordenada);
    }

    public void cargarCoordenadaEstacion(String coordenada){
        StringTokenizer stLatlng = new StringTokenizer(coordenada,",");
        double lat = Double.parseDouble(stLatlng.nextToken());
        double lng = Double.parseDouble(stLatlng.nextToken());
        LatLng estacion = new LatLng(lat, lng);

        CameraPosition camPos = new CameraPosition.Builder()
                .target(estacion)   //Centra el mapa en la Estacion
                .zoom(16)         //Establece el zoom en 16
                .bearing(45)      //Establece la orientación con el noreste arriba
                .tilt(70)         //Baja el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate camUp =
                CameraUpdateFactory.newCameraPosition(camPos);

        mMap.animateCamera(camUp);

        //mMap.moveCamera(camUpd1);
    }

    private void cerrar(){
        finish();
    }

}
