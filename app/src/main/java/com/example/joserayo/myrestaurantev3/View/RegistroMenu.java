package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.R;

public class RegistroMenu extends AppCompatActivity {
    private TextView nombre;
    private CardView criollo,marino,polleria,oriental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_menu);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TIPO DE COMIDAS");


        nombre=(TextView)findViewById(R.id.nombreRestaurante) ;

        final Bundle bundle=this.getIntent().getExtras();


       final  String dato=bundle.getString("nombre");
        final    String dato1=bundle.getString("idrestaurante");
            nombre.setText("Restaurante:"+dato);




        criollo=(CardView)findViewById(R.id.criolla);
        marino=(CardView)findViewById(R.id.marina);
        polleria=(CardView)findViewById(R.id.polleria);
        oriental=(CardView)findViewById(R.id.oriental);

        criollo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegistroMenu.this,RegistroMenu1.class);
                Bundle llevar=new Bundle();
                llevar.putString("idrestaurante",dato1);
                intent.putExtras(llevar);
                startActivity(intent);
            }
        });

        marino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
            polleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            oriental.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

}
