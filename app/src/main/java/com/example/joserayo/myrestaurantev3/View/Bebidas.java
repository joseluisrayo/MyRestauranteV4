package com.example.joserayo.myrestaurantev3.View;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class Bebidas extends Fragment  {
 private   EditText txtarea;
 private  String hola;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
 private EditText nombre,marca,precio,canidad,cantidad1,descripcion,descripcion1;
 private Spinner categoria;
 private FloatingActionButton registrar;
 private ImageView foto;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.bebidas, container, false);
txtarea=(EditText)view.findViewById(R.id.comentario);





        return view;


    }
}
