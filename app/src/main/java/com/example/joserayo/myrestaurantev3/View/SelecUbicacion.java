package com.example.joserayo.myrestaurantev3.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.joserayo.myrestaurantev3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SelecUbicacion extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    private FloatingActionButton btnatras;
    private double lat;
    private double longLt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_ubicacion);


        btnatras = (FloatingActionButton) findViewById(R.id.atras);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelecUbicacion.this,HomeActivity.class);

                intent.putExtra("latitud",lat);
                intent.putExtra("longitud",longLt);
                startActivity(intent);
                finish();
            }
        });


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int heint = metrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (heint * .6));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
          mMap=googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng lima = new LatLng(-11.9927587,-76.8432115);

        googleMap.addMarker(new MarkerOptions().position(lima)
                .title("Selecionar Ubicacion")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 18));






        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder geocoder=new Geocoder(SelecUbicacion.this);

                List<Address>list =null;
                try {
                    LatLng latLng=marker.getPosition();
                    longLt=marker.getPosition().longitude;
                    lat=marker.getPosition().latitude;

                    list=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1) ;

                    Address address=list.get(0);
                    marker.setTitle(address.getAddressLine(0));
                    marker.showInfoWindow();
                    Log.d("Lat","your lat"+lat);
                    Log.d("Long","your long"+longLt);

                }catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }



}
