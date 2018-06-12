package com.example.joserayo.myrestaurantev3.View;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Entrada extends Fragment {

     Comunicador comunicador;
    EditText text;
    String text1="holaa";
    String text2="holaa12";
    String text3="holaa13";
    String text4="holaa14";
    String text5="holaa15";
    String text6="holaa16";
    String text7="holaa17";
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private TextView mostrar;
    private EditText entrada,segundo,precio,precio1,cantidad,cantidad1,descripcion,descripcion1;

    public interface Comunicador{
        public void enviar(String envia);

    }
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entrada, container, false);
        text=(EditText)v.findViewById(R.id.entrada1);
        mostrar = (TextView)v. findViewById(R.id.mostrar2);
        entrada = (EditText)v. findViewById(R.id.entrada1);
        segundo = (EditText)v. findViewById(R.id.Segundo);
        precio = (EditText) v.findViewById(R.id.precio);
        precio1 = (EditText)v. findViewById(R.id.precio1);
        cantidad1 = (EditText)v. findViewById(R.id.cantida1);
        cantidad = (EditText) v.findViewById(R.id.cantidad);
        descripcion = (EditText) v.findViewById(R.id.descripcion);
        descripcion1 = (EditText) v.findViewById(R.id.descripcion1);
        ImageView  registrar=(ImageView)v.findViewById(R.id.Registrar);
        final LinearLayout layout = (LinearLayout)v. findViewById(R.id.ocultar);



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




        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });



        return v;

    }

    private void Registrar() {
        final Bundle bundle=getActivity().getIntent().getExtras();

        final  String dato=bundle.getString("nombre");
        final    String dato1=bundle.getString("idrestaurante");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Categorias/Menu");
        final String entrada2=(entrada).getText().toString().trim();
        final String segundo2=(segundo).getText().toString().trim();
        final String precio2=(precio).getText().toString().trim();
        final String descripcion2=(descripcion).getText().toString().trim();
        final String precio3=(precio1).getText().toString().trim();
        final String descripcion3=(descripcion1.getText().toString()).trim();
        final String cantidad3=(cantidad).getText().toString().trim();

        final String cantidad4=(cantidad1).getText().toString().trim();

            if(!TextUtils.isEmpty(entrada2)&&!TextUtils.isEmpty(segundo2)&&!TextUtils.isEmpty(precio2)&&!TextUtils.isEmpty(precio3)

                    &&!TextUtils.isEmpty(descripcion2) &&!TextUtils.isEmpty(descripcion3)&&!TextUtils.isEmpty(cantidad3) &&!TextUtils.isEmpty(cantidad4)){


                mDatabaseRef.child("entrada").setValue(entrada2);
                mDatabaseRef.child("segundo").setValue(segundo2);
                mDatabaseRef.child("precio").setValue(precio2);
                mDatabaseRef.child("descripcion").setValue(descripcion2);
                mDatabaseRef.child("descriSegundo").setValue(descripcion3);
                mDatabaseRef.child("precioSegun").setValue(precio3);
                mDatabaseRef.child("cantidad").setValue(cantidad3);
                mDatabaseRef.child("IDRestaurante").setValue(dato1);
                mDatabaseRef.child("cantidadSegun").setValue(cantidad4);
                entrada.setText("");
                segundo.setText("");
                precio.setText("");
                descripcion.setText("");
                descripcion1.setText("");
                precio1.setText("");
                cantidad.setText("");
                cantidad1.setText("");
                Toast.makeText(getActivity(),"registrado",Toast.LENGTH_LONG).show();
        } else {
                Toast.makeText(getActivity(),"Error al registrar",Toast.LENGTH_LONG).show();
            }



        }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        comunicador=(Comunicador) context;
        super.onAttach(context);
    }
}
