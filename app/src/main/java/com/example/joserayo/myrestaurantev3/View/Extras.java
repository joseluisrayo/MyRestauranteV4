package com.example.joserayo.myrestaurantev3.View;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.Model.ExtrasModel;
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

import static android.app.Activity.RESULT_OK;

public class Extras extends  Fragment{
    final int COD_FOTO=20;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    public static final String FB_DATABASE_PAT= "Categorias";
    private static final int REQUEST_CODE = 1234;
    String path;

    private Uri imgUri,foto1;
    private Button registrar;
    private  ImageView imagen;

    private EditText extras,segundo,precio,descripcion;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.extras, container, false);
        extras = (EditText)v. findViewById(R.id.extras);
        precio = (EditText)v. findViewById(R.id.precioextras);
        descripcion = (EditText) v.findViewById(R.id.comentario);

        imagen=(ImageView)v.findViewById(R.id.fotoextra);

        registrar=(Button)v.findViewById(R.id.regisextras);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PAT);
        //  final LinearLayout layout = (LinearLayout)v. findViewById(R.id.ocultar);



        imagen.setOnClickListener(new View.OnClickListener() {
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

        if ( imgUri!=null) {


            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Registrando Bebidas");
            dialog.show();
//agrega con foto
            StorageReference reference = storageReference.child(FB_DATABASE_PAT + System.currentTimeMillis() + "." + getImageExt(imgUri) );
            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String extra1 = extras.getText().toString().trim();
                    final Long pre =Long.valueOf(precio.getText().toString());
                    final String descrip = descripcion.getText().toString().trim();



                    if (!TextUtils.isEmpty(extra1) && !TextUtils.isEmpty(pre.toString())  && !TextUtils.isEmpty(descrip)) {

                      ExtrasModel extra=new ExtrasModel(extra1,taskSnapshot.getDownloadUrl().toString());
                        extra.setDescripcionextra(descrip);
                        extra.setNombreRest(dato);
                        extra.setPrecioExtra(pre);
                        extra.setIdRestaurante(dato1);
                        mDatabaseRef.child("Extras").child(extra1).setValue(extra);
                        extras.setText("");
                        precio.setText("");
                        descripcion.setText("");

                        Toast.makeText(getActivity(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), "error al registrar", Toast.LENGTH_SHORT).show();
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

                          //  double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //    dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else  if(valida()) {
//agrega sin foto

            final String extras2=extras.getText().toString().trim();
            final Long pre1 =Long.valueOf(precio.getText().toString());
            final String descrip2=descripcion.getText().toString().trim();
            String imagen ="https://st.depositphotos.com/1014014/2679/i/950/depositphotos_26797131-stock-photo-restaurant-finder-concept-illustration-design.jpg";
            ExtrasModel comida=new ExtrasModel();
          //  String  id=mDatabaseRef.push().getKey();
         //   comida.ser(id);

            comida.setPrecioExtra(pre1);
            comida.setDescripcionextra(descrip2);
            comida.setNombreRest(dato);
            comida.setIdRestaurante(dato1);
            comida.setUrl2(imagen);
            comida.setExtra(extras2);
            mDatabaseRef.child("Extras").child(extras2).setValue(comida);




            Toast.makeText(getActivity(),"exito",Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();

            extras.setText("");

            precio.setText("");

            descripcion.setText("");

        } else {
            Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
        }



    }

    private boolean valida() {

        boolean valida = true;
        if (TextUtils.isEmpty(extras.getText())) {
            extras.setError("campo obligatorio");
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
