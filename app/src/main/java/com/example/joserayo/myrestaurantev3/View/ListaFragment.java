package com.example.joserayo.myrestaurantev3.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListaFragment extends Fragment{
    private RecyclerView recyclerView;
    private DatabaseReference  mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View vista = inflater.inflate(R.layout.lista_fragment,container,false);

         mDatabase = FirebaseDatabase.getInstance().getReference().child("location2");
         mDatabase.keepSynced(true);

         recyclerView = (RecyclerView)vista.findViewById(R.id.myreclicleview);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         return vista;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<LocationModel,BlogViewVolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<LocationModel,
                BlogViewVolder>(LocationModel.class,R.layout.blog_row, BlogViewVolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewVolder viewHolder, LocationModel model, int position) {
                viewHolder.setNom_prod(model.getNombreRest());
                viewHolder.setCategriaP(model.getCategoria());
                viewHolder.setDireccion(model.getDireccion());
                viewHolder.setImagen(getActivity().getApplicationContext(),model.getUrl());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewVolder extends RecyclerView.ViewHolder{
        View view;
        public BlogViewVolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setNom_prod(String nombreRest){
            TextView post_nombreResta = (TextView)view.findViewById(R.id.post_nombreRest);
            post_nombreResta.setText(nombreRest);
        }
        public void setCategriaP(String categoria){
            TextView post_categotiap = (TextView)view.findViewById(R.id.post_categoria);
            post_categotiap.setText(categoria);

        }
        public void setDireccion(String direccion){
            TextView post_cantidad = (TextView)view.findViewById(R.id.post_direccion);
            post_cantidad.setText(direccion);
        }
        public void setImagen(Context ctx,String image){
            ImageView post_Imagen = (ImageView)view.findViewById(R.id.postImgen);
            Picasso.with(ctx).load(image).into(post_Imagen);
        }
    }

}
