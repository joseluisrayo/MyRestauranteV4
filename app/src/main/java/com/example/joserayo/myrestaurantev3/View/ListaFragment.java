package com.example.joserayo.myrestaurantev3.View;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.example.joserayo.myrestaurantev3.Presentador.MenuViewHolder;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListaFragment extends Fragment{
    private RecyclerView recyclerView;
    private DatabaseReference  mDatabase;
    FirebaseRecyclerAdapter<LocationModel,MenuViewHolder>firebaseRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.lista_fragment,container,false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("location2");
        mDatabase.keepSynced(true);

        //llama al metodo de recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.myreclicleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Se llama al metodo en donde se liste los datos en cardwied
        loadMenu();

        //retorna los metodos que estamos utlizando
        return vista;
    }

    private void loadMenu(){
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter
                <LocationModel, MenuViewHolder>(LocationModel.class, R.layout.blog_row, MenuViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, LocationModel model, int position) {
                viewHolder.nomnrePro.setText(model.getNombreRest());
                viewHolder.nombrecatego.setText(model.getCategoria());
                viewHolder.direccion.setText(model.getDireccion());
                Picasso.with(getContext()).load(model.getUrl()).into(viewHolder.imegenPro);

                //Se instancia el metodo Model.LocationModel para utilizar para llamar los nombres
                final LocationModel clickItem = model;

                //Se utiliza el onclick al precionar el nombre del cardview
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent= new Intent(getActivity(), DetallesFoodActivity.class);
                        intent.putExtra("idRest",firebaseRecyclerAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        } ;
        //devuelve el metodo para que paresca los datos en el recyclerView
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
