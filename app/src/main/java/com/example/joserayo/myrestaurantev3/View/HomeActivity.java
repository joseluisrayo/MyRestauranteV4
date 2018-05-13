package com.example.joserayo.myrestaurantev3.View;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    //private FirebaseAuth firebaseAuth;


    private EditText nombre, direccion, numero;
    private Spinner categoria;
    private Button registrar;
    private Switch lugar1;
    private Switch selectrestaurante;
    Button bfecha, bhora;
    EditText efecha, ehora;
    private int dia, mes, ano, hora, minutos;
    private ProgressDialog mProgress;
    private boolean firstame = true;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private static int posicion;
    private String cate;
    private String dato;
    private String lista[] = {"Escoge Categoria", "Restaurante", "Pizeria", "Chifa", "Cevicheria", "Polleria", "Cafeteria"};
    private ArrayAdapter<String> adapter;
    String valorLong;
    String valorLat;
    public final int MY_PERMISSIONS_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bfecha = (Button) findViewById(R.id.bfecha);
        nombre = (EditText) findViewById(R.id.nombre);
        numero = (EditText) findViewById(R.id.telefono);
        direccion = (EditText) findViewById(R.id.direccion);
        bhora = (Button) findViewById(R.id.bhora);
        lugar1 = (Switch) findViewById(R.id.lugar);
        registrar = (Button) findViewById(R.id.createRes);
        efecha = (EditText) findViewById(R.id.efecha);
        ehora = (EditText) findViewById(R.id.ehora);
        selectrestaurante = (Switch) findViewById(R.id.selectlugar);
        categoria = (Spinner) findViewById(R.id.categoria);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int heint = metrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (heint * .7));

        /*Obtener categoria*/

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        categoria.setAdapter(adapter);


        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 1:
                        cate = "Restaurantes";
                        break;
                    case 2:
                        cate = "Pizeria";
                        break;
                    case 3:
                        cate = "Chifa";
                        break;
                    case 4:
                        cate = "Cevicheria";
                        break;

                    case 5:
                        cate = "Polleria";
                        break;
                    case 6:
                        cate = "Cafeteria";

                }
                if (firstame) {
                    firstame = false;
                }

                Log.d("categori", "que es" + cate);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lugar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.lugar) {
                    if (lugar1.isChecked()) {

                        startActivity(new Intent(getApplicationContext(), SelecUbicacion.class));
                    } else {

                    }
                }
            }
        });


        //Valor de ubicación desde la opción seleccionar ubicación
        Bundle datos = this.getIntent().getExtras();
        valorLat = datos.getString("latitud");
        valorLong = datos.getString("longitud");

        Log.d("Lat", "your lat" + valorLat);

          registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearRestaurant();
            }
        });




        selectrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.selectlugar) {

                    LocationManager locationManager = (LocationManager) HomeActivity.this.getSystemService(HomeActivity.this.LOCATION_SERVICE);
                    if ((ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("MENSAJE:", "Faltan permisos");

                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST);
                        return;

                    } else {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {

                                valorLat = (location.getLatitude()) + "";
                                valorLong = (location.getLongitude()) + "";
                                Log.i("Latitud1::", valorLat);
                                Log.i("Longitud1::", valorLong);
                                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                    try {
                                        Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
                                        List<Address> list = geocoder.getFromLocation(
                                                location.getLatitude(), location.getLongitude(), 1);
                                        if (!list.isEmpty()) {
                                            Address DirCalle = list.get(0);
                                            dato = DirCalle.getAddressLine(0);
                                            Log.i("SDireccion::", dato);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        });
                    }

                }
            }
        });


    }


    public void regisfecha(View view) {

        if (view == bfecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }
                    , dia, mes, ano);
            datePickerDialog.show();
        }


    }

    public void registime(View view) {
        if (view == bhora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(hourOfDay + ":" + minute);
                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }


    }


     private void crearRestaurant() {

        final String description2 = direccion.getText().toString().trim();
        final String nombre1 = nombre.getText().toString().trim();
        final String telefono = numero.getText().toString().trim();

        final String date2 = efecha.getText().toString().trim();
        final String time2 = ehora.getText().toString().trim();

        if (!TextUtils.isEmpty(description2) && !TextUtils.isEmpty(date2) && !TextUtils.isEmpty(time2)
       && !TextUtils.isEmpty(nombre1) && !TextUtils.isEmpty(telefono)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Restaurante");

            DatabaseReference reference = mDatabase.child(UUID.randomUUID().toString());

            reference.child("nombre").setValue(nombre1);
            reference.child("direccion").setValue(description2);
            reference.child("telefono").setValue(telefono);
            reference.child("categoria").setValue(cate);
            reference.child("date").setValue(date2);
            reference.child("time").setValue(time2);
            reference.child("latitudRes").setValue(valorLat);
            reference.child("longitudRes").setValue(valorLong);
            Toast.makeText(HomeActivity.this, "Exito al registrar el evento", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(HomeActivity.this, "Error al crear el evento :( ...", Toast.LENGTH_SHORT).show();


        }


    }




}












