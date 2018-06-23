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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.Model.SegundoModel;
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

public class SegundoFragment extends Fragment {
    final int COD_FOTO=20;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    public static final String FB_DATABASE_PAT= "Categorias";
    private static final int REQUEST_CODE = 1234;
    String path;
    private Uri imgUri,foto1;
    private ImageView imagen;
    private EditText segundo,precio,descripcion;
    private Button registrar;
    private Spinner diaReg2;
    private ArrayAdapter<String> adapter;
    private ImageView infoEntrada2;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;
    String DiaRegi2="";

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.segundo, container, false);

        segundo = (EditText)v. findViewById(R.id.segundo);
        precio = (EditText)v. findViewById(R.id.precio);
        descripcion = (EditText) v.findViewById(R.id.descripcion);

        registrar=(Button)v.findViewById(R.id.registrarsegundo);
        imagen=(ImageView)v.findViewById(R.id.fotosegundo);
        diaReg2 = (Spinner)v.findViewById(R.id.diaReg2) ;

        infoEntrada2 = (ImageView) v.findViewById(R.id.infoEntrada2);
        storageReference = FirebaseStorage.getInstance().getReference(FB_DATABASE_PAT);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PAT);

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
        infoEntrada2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSpinner();
            }
        });
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.shopping_item2));
        diaReg2.setAdapter(adapter);
        diaReg2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                if (!diaReg2.getSelectedItem().toString().equalsIgnoreCase("Seleccione Dia")){
                    DiaRegi2 = diaReg2.getSelectedItem().toString();
                    Toast.makeText(getActivity(), DiaRegi2,Toast.LENGTH_SHORT).show();
                }else{
                    DiaRegi2 ="";
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return v;
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
    //sirve para registrar foto
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

    }

    private void Registrar() {
        if (valida()){
            registrarfinal2();
        }
    }

    private void registrarfinal2(){
        final Bundle bundle = getActivity().getIntent().getExtras();
        final String dato1 = bundle.getString("idres");

        if ( imgUri!=null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Registrando Segundo...");
            dialog.show();
            //agrega con foto
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getImageExt(imgUri) );
            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final String segundo1 = segundo.getText().toString().trim();
                    final Long prec =Long.valueOf(precio.getText().toString());
                    final String descrip = descripcion.getText().toString().trim();

                    if (!TextUtils.isEmpty(segundo1) && !TextUtils.isEmpty(prec.toString())  && !TextUtils.isEmpty(descrip)) {
                        SegundoModel comidas = new SegundoModel(segundo1, taskSnapshot.getDownloadUrl().toString());
                        comidas.setDescripcionsegundo(descrip);
                        comidas.setPreciosegundo(prec);
                        comidas.setIdRestaurante(dato1);
                        segundo.setText("");
                        precio.setText("");
                        descripcion.setText("");

                        mDatabaseRef.child(DiaRegi2).child("Segundo").child(segundo1).setValue(comidas);
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Registrado!!", Toast.LENGTH_LONG).show();
                         }
                     }
                  }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //se borra el dialogo
                            dialog.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //Show upload progress
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getActivity(),"Por favor ingrese una imagen del plato que va a registrar!!",Toast.LENGTH_LONG).show();
        }
    }

    private boolean valida() {
        boolean valida = true;

         if (TextUtils.isEmpty(segundo.getText())) {
            segundo.setError("campo obligatorio");
            valida = false;
        } else if (TextUtils.isEmpty(precio.getText())) {
            precio.setError("campo obligatorio");
            valida = false;

        } else if (TextUtils.isEmpty(descripcion.getText())){
            descripcion.setError("campo obligatorio");
            valida = false;
        } else if (DiaRegi2.equals("")){
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

