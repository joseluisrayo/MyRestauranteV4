package com.example.joserayo.myrestaurantev3.View;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import static android.app.Activity.RESULT_OK;

public class Entrada extends Fragment {

    final int COD_FOTO=20;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    public static final String FB_DATABASE_PAT= "Categorias";
    private static final int REQUEST_CODE = 1234;
    String path;

    private Uri imgUri,foto1;
    private   FloatingActionButton foto;
   private  ImageView imagen;
    private TextView mostrar;
    private EditText entrada,segundo,precio,precio1,cantidad,cantidad1,descripcion,descripcion1;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entrada, container, false);
        entrada = (EditText)v. findViewById(R.id.entrada1);
        segundo = (EditText)v. findViewById(R.id.segundo);
        precio = (EditText) v.findViewById(R.id.precio);

        descripcion = (EditText) v.findViewById(R.id.descripcion);
    imagen=(ImageView)v.findViewById(R.id.fotoentrada);
        ImageView  registrar=(ImageView)v.findViewById(R.id.Registrar);
       foto=(FloatingActionButton)v.findViewById(R.id.fotos);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PAT);
      //  final LinearLayout layout = (LinearLayout)v. findViewById(R.id.ocultar);



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
                Registrar();
            }
        });



        return v;

    }

//sirve para registrar foto
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }







    private void carfoto() {
        Intent intent=new Intent();
        intent.setType("image/");
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


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

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

    }

    private void Registrar() {
        final Bundle bundle = getActivity().getIntent().getExtras();

        final String dato = bundle.getString("nombre");
        final String dato1 = bundle.getString("idres");

        Log.d("legoaa",""+dato1);

        if ( imgUri!=null) {


            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Registrando Bebidas");
            dialog.show();
//agrega con foto
            StorageReference reference = storageReference.child(FB_DATABASE_PAT + System.currentTimeMillis() + "." + getImageExt(imgUri) );
            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String entrada1 = entrada.getText().toString().trim();
                    final String segundo1 = segundo.getText().toString().trim();
                    final Long prec =Long.valueOf(precio.getText().toString());

                    final String descrip = descripcion.getText().toString().trim();

                    if (!TextUtils.isEmpty(entrada1) && !TextUtils.isEmpty(segundo1) && !TextUtils.isEmpty(prec.toString())  && !TextUtils.isEmpty(descrip)) {

                        Comidas comidas = new Comidas(entrada1, taskSnapshot.getDownloadUrl().toString());
                        comidas.setSegundo(segundo1);

                        comidas.setDescripcion(descrip);
                        comidas.setPrecio1(prec);
                        comidas.setNombreRest(dato);
                        comidas.setIdRestaurante(dato1);
                        entrada.setText("");
                        segundo.setText("");
                        precio.setText("");

                        descripcion.setText("");
                        String id = mDatabaseRef.push().getKey();
                        comidas.setIdmenu(id);
                        mDatabaseRef.child("Menu").child(entrada1).setValue(comidas);
                        Toast.makeText(getActivity(), "exito", Toast.LENGTH_LONG).show();


                    }


                }
            })


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //se borra el dialogo

                            dialog.dismiss();

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else  if(valida()) {
//agrega sin foto

           final String entrada2=entrada.getText().toString().trim();
       final String segundo2=segundo.getText().toString().trim();
            final Long prec1 =Long.valueOf(precio.getText().toString());

          final String descrip2=descripcion.getText().toString().trim();
           String imagen ="https://st.depositphotos.com/1014014/2679/i/950/depositphotos_26797131-stock-photo-restaurant-finder-concept-illustration-design.jpg";
           Comidas comida=new Comidas();
           String  id=mDatabaseRef.push().getKey();
            comida.setIdmenu(id);
            comida.setEntrada(entrada2);
            comida.setSegundo(segundo2);
            comida.setPrecio1(prec1);
           comida.setIdRestaurante(dato1);
            comida.setDescripcion(descrip2);
            comida.setNombreRest(dato);
            comida.setUrl(imagen);

            mDatabaseRef.child("Menu").child(entrada2).setValue(comida);
            //    final String entrada2=entrada.getText().toString().trim();
            //   final String segundo2=segundo.getText().toString().trim();
            //   final String precio2=precio.getText().toString().trim();
            //   final String cant2=cantidad.getText().toString().trim();
            // final String descrip2=descripcion.getText().toString().trim();
            // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Categorias");
            // DatabaseReference reference = mDatabase.child(entrada2);
            // reference.child("Entrada").setValue(entrada2);
            //  reference.child("segundo").setValue(segundo2);
            // reference.child("precio").setValue(precio2);
            // reference.child("cantidad").setValue(cant2);
            // String imagen ="https://st.depositphotos.com/1014014/2679/i/950/depositphotos_26797131-stock-photo-restaurant-finder-concept-illustration-design.jpg";
            //  reference.child("url").setValue(imagen);
            //    reference.child("descripcion").setValue(descrip2);



            Toast.makeText(getActivity(),"exitott",Toast.LENGTH_LONG).show();
            entrada.setText("");
            segundo.setText("");
            precio.setText("");

            descripcion.setText("");

        } else {
            Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
        }



    }

    private boolean valida() {

        boolean valida = true;
        if (TextUtils.isEmpty(entrada.getText())) {
            entrada.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(segundo.getText())) {
            segundo.setError("campo obligatorio");
            valida = false;
        } else if (TextUtils.isEmpty(precio.getText())) {
            precio.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(descripcion.getText())){
            descripcion.setError("campo obligatorio");
            valida = false;
        }

        return valida;


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            foto1=data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                imagen.setImageBitmap(bm);
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
            imagen.setImageBitmap(bitmap);
        }





    }






}
