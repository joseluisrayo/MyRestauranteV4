package com.example.joserayo.myrestaurantev3.View;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.text.TextUtils;
import android.widget.Toast;


import com.example.joserayo.myrestaurantev3.Model.Categorias;
import com.example.joserayo.myrestaurantev3.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import static android.app.Activity.RESULT_OK;

public class Bebidas extends Fragment  {
    private EditText nombre,precio,descripcion;

    private Button registrar;
    private ImageView foto;
    private Uri imgUri;
    private static final int REQUEST_CODE = 1234;
    public static final String FB_DATABASE_PAT= "Categorias";
    private boolean firstame = true;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    private ArrayAdapter<String> adapter;
    private Spinner diaReg3;
    private ImageView infoEntrada;
    String path;
    final int COD_FOTO=20;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;
    String DiaRegi3="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.bebidas, container, false);

       nombre=(EditText)view.findViewById(R.id.nombre);
       precio=(EditText)view.findViewById(R.id.bebidaprecio);
       descripcion=(EditText)view.findViewById(R.id.comentariobebida);

       registrar=(Button)view.findViewById(R.id.registrar1);
       diaReg3=(Spinner)view.findViewById(R.id.categoria);
       infoEntrada = (ImageView)view.findViewById(R.id.infoEntrada3);
       foto=(ImageView)view.findViewById(R.id.fotobebida);

       storageReference = FirebaseStorage.getInstance().getReference(FB_DATABASE_PAT);
       mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PAT);
       foto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
               final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getActivity());
               alertOpciones.setTitle("Seleccione una Opción");
               alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       if (opciones[i].equals("Tomar Foto")){
                           tomarfoto();
                       }else{
                           if (opciones[i].equals("Cargar Imagen")){
                               carfoto();

                           }else{
                               dialogInterface.dismiss();
                           }
                       }
                   }
               });
               alertOpciones.show();
           }
       });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear();
            }
        });
        infoEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSpinner();
            }
        });
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.shopping_item2));
        diaReg3.setAdapter(adapter);
        diaReg3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                if (!diaReg3.getSelectedItem().toString().equalsIgnoreCase("Seleccione Dia")){
                    DiaRegi3 = diaReg3.getSelectedItem().toString();
                    Toast.makeText(getActivity(), DiaRegi3,Toast.LENGTH_SHORT).show();
                }else{
                    DiaRegi3 ="";
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }
    public void DialogSpinner(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione dia de Registro");
        builder.setMessage(R.string.MenaRegistrODay);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {///evento onclik cuando presiono "Ok"
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void carfoto() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }

    private void tomarfoto() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }

        path=Environment.getExternalStorageDirectory()+ File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);
        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getActivity().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getActivity(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    private void crear() {
        if (valida()){
            registrarfinal3();
        }
    }

    private void registrarfinal3(){

        final Bundle bundle=getActivity().getIntent().getExtras();
        final  String dato1 = bundle.getString("idres");

        if(imgUri!=null){
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setTitle("Registrando Bebidas");
            dialog.show();

            StorageReference refe = storageReference.child(System.currentTimeMillis()+"."+getImageExt(imgUri));
            refe.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String nombre1=nombre.getText().toString().trim();
                    final String precio1=precio.getText().toString().trim();
                    final String descrip=descripcion.getText().toString().trim();

                    if(!TextUtils.isEmpty(nombre1)&&!TextUtils.isEmpty(precio1)&&!TextUtils.isEmpty(descrip))
                    {
                        Categorias categorias=new Categorias(nombre1,taskSnapshot.getDownloadUrl().toString());
                        categorias.setPrecio(precio1);
                        categorias.setIdRestaurante(dato1);
                        categorias.setDescribebidas(descrip);

                        nombre.setText("");//limpia los imput nombre
                        precio.setText("");//limpia los imput precio
                        descripcion.setText("");//limpia los imput descripcion

                        mDatabaseRef.child(DiaRegi3).child("bebidas").child(nombre1).setValue(categorias);
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Registrado!!", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    }
                }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //    dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }else {
            Toast.makeText(getActivity(),"Por favor ingrese una imagen del plato que va a registrar!!",Toast.LENGTH_LONG).show();

        }
    }

    private boolean valida() {
        boolean valida = true;

        if (TextUtils.isEmpty(nombre.getText())) {
            nombre.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(precio.getText())) {
            precio.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(descripcion.getText())){
            descripcion.setError("campo obligatorio");
            valida = false;
        } else if (DiaRegi3.equals("")){
            Toast.makeText(getActivity(), "Seleccione un día para el registro, para mas detalles en el icono de información",Toast.LENGTH_SHORT).show();
            valida = false;
        }
        return valida;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                foto.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

   if(requestCode==COD_FOTO){
       MediaScannerConnection.scanFile(getActivity(), new String[]{path}, null,
               new MediaScannerConnection.OnScanCompletedListener() {
                   @Override
                   public void onScanCompleted(String path, Uri uri) {
                       Log.i("Ruta de almacenamiento","Path: "+path);
                   }
               });

       Bitmap bitmap= BitmapFactory.decodeFile(path);
       foto.setImageBitmap(bitmap);
            }

    }

    }




