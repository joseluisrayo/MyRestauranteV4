package com.example.joserayo.myrestaurantev3.View;

import android.Manifest;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.joserayo.myrestaurantev3.R;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity  {
    //private FirebaseAuth firebaseAuth;

  private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText direccion,numero,textview,etime1,etime2,mDisplayDate,nombre,web;
    private Spinner categoria;
    private Button registrar,CargarFoto;
    private FloatingActionButton lugar1;
    private String nombre2;
    private Switch selectrestaurante;
    private int  hora, minutos,hora1,minute2;
    private boolean firstame = true;
    private FirebaseAuth mAuth;
    private EditText date;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private String cate;
    private String dato;
    private String lista[] = {"Escoge Categoria", "Restaurante", "Pizeria", "Chifa", "Cevicheria", "Polleria", "Cafeteria"};
    private ArrayAdapter<String> adapter;
    double valorLong;
    double valorLat;
    public final int MY_PERMISSIONS_REQUEST = 1;
    static String abierto;
     static  String cerrado;
    static String time4;
    static String time3;
     static String horario2;
     private static TextView llevar;
     private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "HomeActivity";
    private static final int SECACT_REQUEST_CODE=0;
    private static final int SECAC_REQUEST_CODE=0;
    private ImageView imagen;

   String direc;
     AlertDialog.Builder alertdialogbuilder;
     String[] AlertDialogItems = new String[]{
            "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"
    };
     List<String> ItemsIntoList;

    boolean[] Selectedtruefalse = new boolean[]{
            false, false, false, false, false, false, false
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==SECACT_REQUEST_CODE){
            if (resultCode==RESULT_OK){
                valorLat = data.getDoubleExtra("latitud",0);
                valorLong = data.getDoubleExtra("longitud",0);
                direc=data.getStringExtra("direccion");

                direccion.setText(direc);
                abierto=data.getStringExtra("abierto");

                cerrado=data.getStringExtra("cerrado");

                //foto
                Uri path=data.getData();
                imagen.setImageURI(path);

            }

        }







    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nombre = (EditText) findViewById(R.id.nombre);
        numero = (EditText) findViewById(R.id.telefono);
        direccion = (EditText) findViewById(R.id.direccion);

        registrar = (Button) findViewById(R.id.createRes);
        llevar=(TextView)findViewById(R.id.lugares);
        web=(EditText)findViewById(R.id.web);
        etime1 = (EditText) findViewById(R.id.etime1);
        etime2 = (EditText) findViewById(R.id.etime2);
        selectrestaurante = (Switch) findViewById(R.id.selectlugar);
        categoria = (Spinner) findViewById(R.id.categoria);
         textview = (EditText) findViewById(R.id.horarios);
        mDisplayDate = (EditText) findViewById(R.id.tvDate);
        imagen=(ImageView)findViewById(R.id.idimagen);
        CargarFoto=(Button)findViewById(R.id.tomarfoto);

          //este codigo es para desaparecer el teclado virtual de los editext
        direccion .setInputType(InputType.TYPE_NULL);
        textview.setInputType(InputType.TYPE_NULL);
        mDisplayDate.setInputType(InputType.TYPE_NULL);

        llevar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SeleccionarHorario.class);
                startActivityForResult(intent,SECACT_REQUEST_CODE);

            }
        });
//prendo o apago el boton segun los permisos que se hace
        if(ValidarPermisos()){
           CargarFoto.setEnabled(true);


        }else{

            CargarFoto.setEnabled(false);
        }


        //Escoger Fecha  Restaurant
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        //HORARIO Abrir Y Cerrar Restaurante
        etime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time4 = hourOfDay + ":" + minute;
                        etime1.setText(time4);



                    }
                }, hora, minutos, false);
                timePickerDialog.setTitle("Abre a la(s)");
                timePickerDialog.show();



            }
        });


        etime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar  calendar= Calendar.getInstance();
                hora1=calendar.get(Calendar.HOUR_OF_DAY);
                minute2=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time3 = hourOfDay + ":" + minute;
                        Log.d("abrirr",time3);
                        etime2.setText(time3);

                    }

                },hora1,minute2,false);
                timePickerDialog.setTitle("Cierra a las(S)");
                timePickerDialog.show();

            }
        });

 //dialogo flotante horario
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textview.setText("");

                alertdialogbuilder = new AlertDialog.Builder(HomeActivity.this);

                ItemsIntoList = Arrays.asList(AlertDialogItems);

                alertdialogbuilder.setMultiChoiceItems(AlertDialogItems, Selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            switch (which){
                                case 1:
                                    horario2="Domingo";
                                    break;
                                case 2:
                                    horario2="Lunes";
                                    break;
                                case 3:
                                    horario2="Martes";
                                    break;
                                case 4:
                                    horario2="Miercoles";
                                    break;
                                case 5:
                                    horario2="Jueves";
                                    break;
                                case 6:
                                    horario2="Viernes";
                                    break;
                                case 7:
                                    horario2="Sabado";
                                    break;

                            }
                            Log.d("escogido",horario2);
                        }

                    }
                });

                alertdialogbuilder.setCancelable(false);

                alertdialogbuilder.setTitle("Seleccione Horario");

                alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int a = 0;
                        while(a < Selectedtruefalse.length)
                        {
                            boolean value = Selectedtruefalse[a];

                            if(value){
                                textview.setText(textview.getText() + ItemsIntoList.get(a) + "—");
                            }

                            a++;
                        }

                    }
                });

                alertdialogbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = alertdialogbuilder.create();

                dialog.show();
            }



        });

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

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelecUbicacion.class);
                startActivityForResult(intent,SECACT_REQUEST_CODE);
            }
        });


        //Valor de ubicación desde la opción seleccionar ubicación y traigo la direccion al editeX
   //     Bundle datos = this.getIntent().getExtras();
    //    valorLat = datos.getString("latitud",valorLat);
    //    valorLong = datos.getString("longitud",valorLong);
    //    direc=datos.getString("Direccion",direc);
    //    direccion.setText(direc);

    //    Log.d("Lat", "your lat" + valorLat);


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
                    if ((ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("MENSAJE:", "Faltan permisos");

                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST);
                        return;

                    } else {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {

                                valorLat = (location.getLatitude()) ;
                                valorLong = (location.getLongitude());
                                Log.i("Latitud1::",""+ valorLat);
                                Log.i("Longitud1::",""+ valorLong);
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

    private boolean ValidarPermisos() {


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(HomeActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
              String direcc=direccion.getText().toString();
             String nom=nombre.getText().toString();
             outState.putString("nom1",nom);
             outState.getString("direc2",direcc);
             Toast.makeText(this,"llego"+nom,Toast.LENGTH_LONG).show();




        
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String nom2=savedInstanceState.getString("nom1");
        nombre.setText(nom2);
        String direcc3=savedInstanceState.getString("direc2");
        direccion.setText(direcc3);
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this,"llego12"+direcc3,Toast.LENGTH_LONG).show();


    }


     private void crearRestaurant() {

        final String description2 = direccion.getText().toString().trim();
        final String nombre1 = nombre.getText().toString().trim();
        final String web1=web.getText().toString().trim();
        final String telefono = numero.getText().toString().trim();
        final String time1 = etime1.getText().toString().trim();
       final String time2 = etime2.getText().toString().trim();
        final String fecha=mDisplayDate.getText().toString();
        if (!TextUtils.isEmpty(description2)  && !TextUtils.isEmpty(fecha) && !TextUtils.isEmpty(time1) && !TextUtils.isEmpty(fecha) && !TextUtils.isEmpty(time2)
       && !TextUtils.isEmpty(nombre1) && !TextUtils.isEmpty(telefono)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("location2");

            DatabaseReference reference = mDatabase.child(nombre1);

            reference.child("nombreRest").setValue(nombre1);
            reference.child("direccion").setValue(description2);
            reference.child("telefono").setValue(telefono);
            reference.child("categoria").setValue(cate);
            reference.child("horarios").setValue(horario2);
            reference.child("Cerrar").setValue(time1);
            reference.child("web").setValue(web1);
            reference.child("fecha").setValue(fecha);
            reference.child("Abrir").setValue(time2);
            reference.child("latitude").setValue(valorLat);
            reference.child("longitude").setValue(valorLong);
            Toast.makeText(HomeActivity.this, "Exito al registrar el Restaurante", Toast.LENGTH_SHORT).show();

            nombre.setText("");
            direccion.setText("");
            numero.setText("");
            web.setText("");
            etime1.setText("");
            etime2.setText("");
            mDisplayDate.setText("");
            textview.setText("");



        } else {
            Toast.makeText(HomeActivity.this, "Error al crear el Restaurante :( ...", Toast.LENGTH_SHORT).show();


        }



    }


    public void cargarimagen(View view) {
                CargarImagen();
    }

    private void CargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");

        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }
}












