package com.example.joserayo.myrestaurantev3.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.R;

public class RegistroMenus extends AppCompatActivity {
  private TextView mostrar;
  private EditText entrada,segundo,precio,precio1,cantidad,cantidad1,descripcion,descripcion1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_menus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mostrar = (TextView) findViewById(R.id.mostrar2);
        entrada = (EditText) findViewById(R.id.entrada1);
        segundo = (EditText) findViewById(R.id.Segundo);
        precio = (EditText) findViewById(R.id.precio);
        precio1 = (EditText) findViewById(R.id.precio1);
        cantidad1 = (EditText) findViewById(R.id.cantida1);
        cantidad = (EditText) findViewById(R.id.cantidad);
        descripcion = (EditText) findViewById(R.id.descripcion);
        descripcion1 = (EditText) findViewById(R.id.descripcion1);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.ocultar);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility()==View.VISIBLE ){
                    layout.setVisibility(View.GONE);
                    segundo.setVisibility(View.GONE);
                    precio1.setVisibility(View.GONE);
                    cantidad1.setVisibility(View.GONE);
                    descripcion1.setVisibility(View.GONE);

                } else {

                    layout.setVisibility(View.VISIBLE);
                    segundo.setVisibility(View.VISIBLE);
                    precio1.setVisibility(View.VISIBLE);
                    cantidad1.setVisibility(View.VISIBLE);
                    descripcion1.setVisibility(View.VISIBLE);

                }
            }
        });

    }




}
