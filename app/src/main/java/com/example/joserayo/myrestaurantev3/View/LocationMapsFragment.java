package com.example.joserayo.myrestaurantev3.View;
import com.example.joserayo.myrestaurantev3.Model.LocationData;
import com.example.joserayo.myrestaurantev3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Map;

public class LocationMapsFragment extends Fragment implements OnMapReadyCallback, LocationListener{
    private GoogleMap mMap;
    private Marker marcador;
    private LatLng locationLatLng;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.location_maps_fragment,container,false);

        SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        supportMapFragment.getMapAsync(this);
        //se inicializa el comienzo de la ubicaion
        startGettingLocations();
        //inicializamos la firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //inicializamos los multiples markets
        getMarkers();
        return vista;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //LatLng recife = new LatLng( -11.984694472037257, -76.83246731758118);
        //mMap.addMarker(new MarkerOptions().position(recife).title("Marcador em Recife"));
        //CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(recife).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //LocationData locationData = new LocationData(recife.latitude,recife.longitude);
        //mDatabase.child("location2").child(String.valueOf("Las Delicias De Casa")).setValue(locationData);
    }

    @Override
    public void onLocationChanged(Location location) {
        //se verifica si el marcador es diferente a null
        if (marcador!=null){
            marcador.remove();
        }
        //Se agrega el market
        locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(locationLatLng);
        markerOptions.title("Location actual");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.locatioimagen));
        marcador = mMap.addMarker(markerOptions);

        //Se mueve a la nueva localizacion
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(locationLatLng).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Toast.makeText(getActivity(), "Localización Actualizada", Toast.LENGTH_SHORT).show();
        getMarkers();
    }
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (ContextCompat.checkSelfPermission(getActivity(),permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    private void startGettingLocations() {

        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// Distance in meters
        long MIN_TIME_BW_UPDATES = 1000 * 10;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(getActivity(), "No se pudo obtener la localización", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMarkers(){

        mDatabase.child("location2").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if (dataSnapshot.getValue() != null)
                            getAllLocations((Map<String,Object>) dataSnapshot.getValue());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void getAllLocations(Map<String,Object> locations) {

        for (Map.Entry<String, Object> entry : locations.entrySet()){
            String newDate = new String(String.valueOf(entry.getKey()));
            Map singleLocation = (Map) entry.getValue();
            LatLng latLng = new LatLng((Double) singleLocation.get("latitude"), (Double)singleLocation.get("longitude"));
            addGreenMarker(newDate,latLng);
        }
    }

    private void addGreenMarker(String newDate,LatLng latLng) {
        //SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(newDate);
        //markerOptions.title("Restaurantes");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions);
    }


    ///Estos metodos no son utilizados
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
