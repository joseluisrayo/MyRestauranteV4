package com.example.joserayo.myrestaurantev3.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Model.SegundoHolder;
import com.example.joserayo.myrestaurantev3.Model.SegundoModel;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class FragmentListaSegundo extends Fragment {

    RecyclerView recyclerView;
    String id="";
    String fecha="";
    RecyclerView.LayoutManager manager;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseRecyclerAdapter<SegundoModel,SegundoHolder> recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_lista_segundo, container, false);

        fecha();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Categorias");

        recyclerView = (RecyclerView)rootView. findViewById(R.id.myreclicleview);

        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);


        if(getActivity().getIntent()!=null){
            id =getActivity(). getIntent().getStringExtra("id");

        }if (!id.isEmpty()){
            listar(id);
        }

        return rootView;
    }

    private void fecha(){
        String[] dias={"Domingo","Lunes","Martes", "Miercoles","Jueves","Viernes","Sabado"};
        int numeroDia = 0;
        Calendar cal= Calendar.getInstance();
        numeroDia = cal.get(Calendar.DAY_OF_WEEK);
        fecha = dias[numeroDia - 1];
    }

    private void listar(String id) {
        recyclerAdapter=new FirebaseRecyclerAdapter<SegundoModel, SegundoHolder>(SegundoModel.class,R.layout.item_categoria,
                SegundoHolder.class,reference.child(fecha).child("Segundo").orderByChild("idRestaurante").equalTo(id)) {
            @Override
            protected void populateViewHolder(SegundoHolder viewHolder, SegundoModel model, int position) {
                viewHolder.nombre.setText(model.getSegundo());
                viewHolder.descripio.setText(model.getDescripcionsegundo());
                Picasso.with(getActivity()).load(model.getUrl4()).fit().into(viewHolder.foto);

                final SegundoModel comidas=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(getActivity(),""+comidas.getSegundo(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
    }

}
