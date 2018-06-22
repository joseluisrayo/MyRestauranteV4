package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.example.joserayo.myrestaurantev3.Presentador.MenuViewHolder;
import com.example.joserayo.myrestaurantev3.Presentador.RestUserViewHolder;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.IdentityHashMap;

public class GestionRestUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    String IDUser="";
    FirebaseRecyclerAdapter<LocationModel,RestUserViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_rest_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mis Restaurantes");
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("location2");///se instancia la base de datos y lo que se quiere listar
        mDatabase.keepSynced(true);

        //Bundle para obtener el iduser
        Bundle bundle3 = GestionRestUserActivity.this.getIntent().getExtras();
        if(bundle3!=null){
            IDUser = bundle3.getString("idUsers"); ///se resive el email de MainActivity
        }

        //llama al metodo de recycleview
        recyclerView = (RecyclerView)findViewById(R.id.myreclicleview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GestionRestUserActivity.this));
        loadMenu(IDUser);
    }

    private void loadMenu(String idUser){
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter
                <LocationModel, RestUserViewHolder>(LocationModel.class, R.layout.blog_row2, RestUserViewHolder.class,
                mDatabase.orderByChild("idUser").equalTo(idUser)) {
            @Override
            protected void populateViewHolder(RestUserViewHolder viewHolder, LocationModel model, int position) {
                viewHolder.nomnrePro2.setText(model.getNombreRest());
                viewHolder.nombrecatego2.setText(model.getCategoria());
                viewHolder.direccion2.setText(model.getDireccion());
                Picasso.with(getBaseContext()).load(model.getUrl()).into(viewHolder.imegenPro2);

                //Se instancia el metodo Model.LocationModel para utilizar para llamar los nombres
                final LocationModel clickItem = model;

                //Se utiliza el onclick al precionar el nombre del cardview
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent= new Intent(GestionRestUserActivity.this, RegistroMenu1.class);
                        intent.putExtra("idres",firebaseRecyclerAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        //devuelve el metodo para que paresca los datos en el recyclerView
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
