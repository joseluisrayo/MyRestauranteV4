package com.example.joserayo.myrestaurantev3.View;

import android.Manifest;

import android.support.v7.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;
import com.example.joserayo.myrestaurantev3.R;
import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    //private FirebaseAuth firebaseAuth;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText direccion, telefono, horario, nombreRest;
    private Spinner categoria;
    private Button registrar, cargarFoto;
    private CheckBox selectrestaurante;
    private boolean firstame = true;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private String cate,cate1;
    private String dato;
    private String lista[] = {"Escoge Categoria", "Restaurante", "Pizeria", "Chifa", "Cevicheria", "Polleria", "Cafeteria"};
    private ArrayAdapter<String> adapter;
    double valorLong;
    double valorLat;
    public final int MY_PERMISSIONS_REQUEST = 1;
    private static final int SECACT_REQUEST_CODE = 0;
    private static final int REQUEST_CODE = 1234;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "location2";
    private ImageView imagen;
    private Uri imgUri;
    private int idrestaurante =0;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    String direc;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECACT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                valorLat = data.getDoubleExtra("latitud", 0);
                valorLong = data.getDoubleExtra("longitud", 0);
                direc = data.getStringExtra("direccion");

                direccion.setText(direc);


            }

        }
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imagen.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("REGISTRO DE RESTURANTES");
        nombreRest = (EditText) findViewById(R.id.nombre);
        telefono = (EditText) findViewById(R.id.telefono);
        direccion = (EditText) findViewById(R.id.direccion);
        registrar = (Button) findViewById(R.id.createRes);
        selectrestaurante = (CheckBox) findViewById(R.id.selectlugar);
        categoria = (Spinner) findViewById(R.id.categoria);
        horario = (EditText) findViewById(R.id.horarios);
        imagen = (ImageView) findViewById(R.id.idimagen);

        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        //este codigo es para desaparecer el teclado virtual de los editext
        direccion.setInputType(InputType.TYPE_NULL);
        horario.setInputType(InputType.TYPE_NULL);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cargarfoto();
            }
        });


        //dialogo flotante horario
        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + "—";
                            }
                        }
                        horario.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            horario.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }


        });



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
                Intent intent = new Intent(getApplicationContext(), SelecUbicacion.class);
                startActivityForResult(intent, SECACT_REQUEST_CODE);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
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

                                valorLat = (location.getLatitude());
                                valorLong = (location.getLongitude());
                                Log.i("Latitud1::", "" + valorLat);
                                Log.i("Longitud1::", "" + valorLong);
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



    private void Registrar() {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("registrando imagen");
            dialog.show();
            //pongo el nombre que ba tener en el torag
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //registro al firebase

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (validar()) {
                        final Long telefono2 = Long.valueOf(telefono.getText().toString().trim());
                        final String horario1 = (horario.getText().toString());
                        final String nombre = (nombreRest.getText().toString().trim());
                        LocationModel imageUpload = new LocationModel(nombre, taskSnapshot.getDownloadUrl().toString());
                        imageUpload.setTelefono(telefono2);
                        imageUpload.setDireccion(direccion.getText().toString());
                        imageUpload.setCategoria(cate);
                        imageUpload.setHorarios(horario1);
                        imageUpload.setLongitude(valorLong);

                        imageUpload.setLatitude(valorLat);

                        //Save image info in to firebase database
                        String ultimoCliente = mDatabaseRef.getKey();
                        String uid = mDatabaseRef.child("location2").push().getKey();
                        imageUpload.setIdRestaurante(uid);
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(imageUpload);


                        //ocultar dialogo cuando se registra
                        dialog.dismiss();
                        //Display success toast msg
                        if (cate.equals("Restaurantes")) {
                            switch (cate) {
                                case "Restaurantes":
                                    Intent intent = new Intent(HomeActivity.this, RegistroMenu1.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("nombre", nombre);
                                    bundle.putString("idres",uploadId);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                    break;


                            }

                        } else {
                            Toast.makeText(HomeActivity.this,"esto es otra categoria",Toast.LENGTH_LONG).show();
                        }



                        Toast.makeText(getApplicationContext(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                        nombreRest.setText("");
                        direccion.setText("");
                        horario.setText("");
                        telefono.setText("");
                        imagen.setTag("");









                     } else {
                         Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                     }




                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //se borra el dialogo

                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }


    }

    private void Cargarfoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String direcc = direccion.getText().toString();
        String nom = nombreRest.getText().toString();
        outState.putString("nom1", nom);
        outState.getString("direc2", direcc);
        Toast.makeText(this, "llego" + nom, Toast.LENGTH_LONG).show();


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String nom2 = savedInstanceState.getString("nom1");
        nombreRest.setText(nom2);
        String direcc3 = savedInstanceState.getString("direc2");
        direccion.setText(direcc3);
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "llego12" + direcc3, Toast.LENGTH_LONG).show();


    }





    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public boolean validar() {
        boolean valida = true;
        if (TextUtils.isEmpty(nombreRest.getText())) {
            nombreRest.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(telefono.getText())) {
            telefono.setError("campo obligatorio");
            valida = false;
        } else if (telefono.getText().toString().trim().length() < 9) {
            telefono.setError("Minimo 9 numeros");
            valida = false;
        } else if (TextUtils.isEmpty(cate)) {
            Toast.makeText(HomeActivity.this, "Campo Obligatorio", Toast.LENGTH_LONG).show();
            valida = false;
        } else if (TextUtils.isEmpty(horario.getText())){
            Toast.makeText(HomeActivity.this, "Campo Obligatorio", Toast.LENGTH_LONG).show();
            valida = false;
        }

            return valida;

    }


}












