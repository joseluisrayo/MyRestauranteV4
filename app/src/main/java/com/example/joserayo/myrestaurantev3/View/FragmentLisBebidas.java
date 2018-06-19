package com.example.joserayo.myrestaurantev3.View;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Model.BebidasHolder;
import com.example.joserayo.myrestaurantev3.Model.Categorias;
import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.Model.ComidasHolder;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FragmentLisBebidas extends Fragment{
    RecyclerView recyclerView;
    String id="";
    RecyclerView.LayoutManager manager;

    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseRecyclerAdapter<Categorias,BebidasHolder> recyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bebidas, container, false);
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Categorias/bebidas");

        recyclerView = (RecyclerView)rootView. findViewById(R.id.myreclicleview);

        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);




//Get foodid from Intent
        if(getActivity().getIntent()!=null){
            id =getActivity(). getIntent().getStringExtra("id");

        }if (!id.isEmpty()){
            listar(id);
        }




        return rootView;
    }

    private void listar(String id) {
        recyclerAdapter=new FirebaseRecyclerAdapter<Categorias, BebidasHolder>(Categorias.class,R.layout.item_categoria,BebidasHolder.class,
                reference.orderByChild("idRestaurante").equalTo(id)) {
            @Override
            protected void populateViewHolder(BebidasHolder viewHolder, Categorias model, int position) {

                viewHolder.nombre.setText(model.getNombre());

                viewHolder.descripio.setText(model.getDescribebidas());
                Picasso.with(getContext()).load(model.getUrl1()).fit()
                        .into(viewHolder.foto);

                final Categorias comidas=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),""+comidas.getNombre(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        };


        recyclerView.setAdapter(recyclerAdapter);
    }
}
