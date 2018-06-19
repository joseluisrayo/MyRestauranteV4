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


 private EditText nombre,marca,precio,canidad,cantidad1,descripcion,descripcion1;
 private Spinner categoria;
 private FloatingActionButton registrar;
 private ImageView foto;
    private Uri imgUri;
    private static final int SECACT_REQUEST_CODE = 0;
    private static final int REQUEST_CODE = 1234;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PAT= "Categorias";
    private boolean firstame = true;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    private String lista[]={"Escoge Categoria","Gaseosa","Refresco","cerveza","Agua","Jugos","Cafe","Vino"};
    private ArrayAdapter<String> adapter;
    String path;
    private String  cate;
    final int COD_FOTO=20;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;


    private ArrayAdapter<String> adapter1;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.bebidas, container, false);
       nombre=(EditText)view.findViewById(R.id.nombre);
       marca=(EditText)view.findViewById(R.id.marca);
       precio=(EditText)view.findViewById(R.id.bebidaprecio);

       descripcion=(EditText)view.findViewById(R.id.comentariobebida);
       registrar=(FloatingActionButton)view.findViewById(R.id.registrar1);
       categoria=(Spinner)view.findViewById(R.id.categoria);
       foto=(ImageView)view.findViewById(R.id.fotobebida);
        storageReference = FirebaseStorage.getInstance().getReference();
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
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lista);
        categoria.setAdapter(adapter);
registrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        crear();
    }
});

      categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              switch (position){
                  case 0:
                      cate="Gaseosa";
                      break;
                  case 1:
                      cate="Cerveza";
                      break;
                  case 2:
                      cate="Refresco";
                      break;
                  case 3:
                      cate="cerveza";
                      break;
                  case 4:
                      cate="Agua";
                      break;
                  case 5:
                      cate="Jugos";
                      break;
                  case 6:
                      cate="Cafe";
                      break;
                  case 7:
                      cate="Vino";
                      break;

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


        return view;


    }
//agregar con foto
    private void crear() {

        final Bundle bundle=getActivity().getIntent().getExtras();

        final  String dato=bundle.getString("nombre");
        final  String dato1=bundle.getString("idres");


        if(imgUri!=null){



            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setTitle("Registrando Bebidas");
            dialog.show();
            StorageReference refe = storageReference.child(FB_DATABASE_PAT + System.currentTimeMillis() + "." + getImageExt(imgUri));

            refe.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  final String nombre1=nombre.getText().toString().trim();
                  final String marca1=marca.getText().toString().trim();
                  final String precio1=precio.getText().toString().trim();

                  final String descrip=descripcion.getText().toString().trim();


                    if(!TextUtils.isEmpty(nombre1)&& !TextUtils.isEmpty(marca1)&&!TextUtils.isEmpty(precio1)&&!TextUtils.isEmpty(descrip))
                    {

                        Categorias categorias=new Categorias(nombre1,taskSnapshot.getDownloadUrl().toString());
                        categorias.setMarca(marca1);
                        categorias.setCategoria(cate);
                        categorias.setPrecio(precio1);
                        categorias.setIdRestaurante(dato1);
                        categorias.setDescribebidas(descrip);
                        categorias.setNombreRest(dato);
                         nombre.setText("");
                         marca.setText("");
                         precio.setText("");

                         descripcion.setText("");
                        mDatabaseRef.child("bebidas").child(nombre1).setValue(categorias);
                        Toast.makeText(getActivity(),"exito",Toast.LENGTH_LONG).show();




                    } else {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }

                    });



        }
        //agregar sin foto
        else if (valida()){
            final String nombre2=nombre.getText().toString().trim();
            final String marca2=marca.getText().toString().trim();
            final String precio2=precio.getText().toString().trim();
            final String canti2=cantidad1.getText().toString().trim();
            final String descrip2=descripcion.getText().toString().trim();
            String imagen ="https://st.depositphotos.com/1014014/2679/i/950/depositphotos_26797131-stock-photo-restaurant-finder-concept-illustration-design.jpg";
           Categorias categorias=new Categorias();
           categorias.setNombreRest(dato);
           categorias.setNombre(nombre2);
           categorias.setMarca(marca2);
           categorias.setPrecio(precio2);
           categorias.setIdRestaurante(dato1);
           categorias.setCantidad(canti2);
           categorias.setDescribebidas(descrip2);
           categorias.setUrl1(imagen);


            Toast.makeText(getActivity(),"Registrado Correctamente",Toast.LENGTH_LONG).show();

        }


    }

    private boolean valida() {
        return false;
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

        ////
    }

    private void carfoto() {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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
    private boolean valida1() {

        boolean valida = true;
        if (TextUtils.isEmpty(nombre.getText())) {
            nombre.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(marca.getText())) {
            marca.setError("campo obligatorio");
            valida = false;
        } else if (TextUtils.isEmpty(precio.getText())) {
            precio.setError("campo obligatorio");
            valida = false;
        } else if (TextUtils.isEmpty(cantidad1.getText())) {
            cantidad1.setError("campo obligatorio");
            valida = false;
        }


        return valida;
    }


    }




