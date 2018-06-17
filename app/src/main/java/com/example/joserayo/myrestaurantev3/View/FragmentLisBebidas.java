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
import com.example.joserayo.myrestaurantev3.Model.CategoriasHolder;
import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FragmentLisBebidas  extends Fragment {
    RecyclerView recyclerView;
    String id="";
    RecyclerView.LayoutManager manager;

    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseRecyclerAdapter<Comidas,CategoriasHolder> recyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bebidas, container, false);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Categorias");

        recyclerView = (RecyclerView)rootView. findViewById(R.id.myreclicleview);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        if (getActivity().getIntent()!=null){
            id= getActivity().getIntent().getStringExtra("id");
            if (!id.isEmpty()&&id!=null){
                listar(id);
            }
        }





        return rootView;
    }

    private void listar(String id) {
        recyclerAdapter=new FirebaseRecyclerAdapter<Comidas, CategoriasHolder>(Comidas.class,R.layout.item_categoria,
                CategoriasHolder.class,reference.orderByChild("nombreRest").equalTo(id)){
            @Override
            protected void populateViewHolder(CategoriasHolder viewHolder, Comidas model, int position) {
                viewHolder.nombre.setText(model.getEntrada());
                viewHolder.precio.setText(model.getPrecio());
                viewHolder.descripio.setText(model.getDescripcion1());
                Picasso.with(getContext()).load(model.getUrl()).fit()
                        .into(viewHolder.foto);

                final Comidas comidas=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),""+comidas.getEntrada(),Toast.LENGTH_LONG).show();
                    }
                });

            }

        };
        recyclerView.setAdapter(recyclerAdapter);

        Log.d("nombre","aaa"+id);
    }
}
