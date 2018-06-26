package com.example.joserayo.myrestaurantev3.View;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.example.joserayo.myrestaurantev3.Presentador.MenuViewHolder;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ListaFragment extends Fragment{


    private ImageView imagensinConexion;
    private RecyclerView recyclerView;
    private DatabaseReference  mDatabase;
    private DatabaseReference  mDatabaselike;
    private DatabaseReference  mDatabaseuser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseRecyclerAdapter<LocationModel,MenuViewHolder>firebaseRecyclerAdapter;
    //funcionalidades del buscador
    FirebaseRecyclerAdapter<LocationModel,MenuViewHolder>searchRecyclerAdapter;
    List<String> suggestList= new ArrayList<>();
       String email;
       String uid;
    MaterialSearchBar materialSearchBar;
    String idresta;
     String postkey;
    private boolean like= false;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.lista_fragment,container,false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("location2");
        mDatabaselike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseuser = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.keepSynced(true);
        mDatabaselike.keepSynced(true);
        mDatabaseuser.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();


        //llama al metodo de recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.myreclicleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //se instacion al id de imagensinconexion
        imagensinConexion = (ImageView)vista.findViewById(R.id.imagenSinConexion);
        imagensinConexion.setVisibility(View.INVISIBLE);


            mDatabaseuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot IDGenerado : dataSnapshot.getChildren()){
                        String useremail = IDGenerado.child("email").getValue(String.class);
                        if (useremail.equals(email)){


                            uid = IDGenerado.getKey();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        //se carga la instacion de conexion
        ConnectivityManager con=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){
            imagensinConexion.setVisibility(View.INVISIBLE);
        }else{
            imagensinConexion.setVisibility(View.VISIBLE);
        }

        //Se llama al metodo en donde se liste los datos en cardwied
        loadMenu();

        //obtengo idau
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }




        //se llama la metodo de listar las sugerencias
        loadSuggest();

        //search
        materialSearchBar = (MaterialSearchBar) vista.findViewById(R.id.searchBar);
        materialSearchBar.setHint("Busque su Restaurante");
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList){

                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    recyclerView.setAdapter(firebaseRecyclerAdapter);
                }
            }
            @Override
            public void onSearchConfirmed(CharSequence text) {
                stardSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
        //retorna los metodos que estamos utlizando



         return vista;
    }
    private void stardSearch(CharSequence text) {
        searchRecyclerAdapter = new FirebaseRecyclerAdapter<LocationModel, MenuViewHolder>
                (LocationModel.class, R.layout.blog_row, MenuViewHolder.class, mDatabase.orderByChild("nombreRest").equalTo(text.toString())) {
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
                        intent.putExtra("idRest",searchRecyclerAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });


            }
        };
        //devuelve el metodo para que paresca los datos en el recyclerView
        recyclerView.setAdapter(searchRecyclerAdapter);
    }

    private void loadSuggest(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    LocationModel item = dataSnapshot1.getValue(LocationModel.class);
                    suggestList.add(item.getNombreRest());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
             like=true;
            }
        });

    }

    private void loadMenu() {

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter
                <LocationModel, MenuViewHolder>(LocationModel.class, R.layout.blog_row, MenuViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, final LocationModel model, int position) {
                postkey = getRef(position).getKey();
                viewHolder.nomnrePro.setText(model.getNombreRest());
                viewHolder.nombrecatego.setText(model.getCategoria());
                viewHolder.direccion.setText(model.getDireccion());
                viewHolder.direccion.setText(model.getIdUser());
                Picasso.with(getContext()).load(model.getUrl()).into(viewHolder.imegenPro);
                viewHolder.setLike(postkey);

                viewHolder.favorito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        like = true;

                            mDatabaselike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {



                                    if (like) {
                                        if (dataSnapshot.child(postkey).hasChild(uid)) {

                                            mDatabaselike.child(postkey).child(uid).removeValue();
                                            like = false;

                                        } else {
                                            mDatabaselike.child(postkey).child(uid).setValue("like");
                                            like = false;
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    }


                });

                //Se instancia el metodo Model.LocationModel para utilizar para llamar los nombres
                final LocationModel clickItem = model;

                //Se utiliza el onclick al precionar el nombre del cardview
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getActivity(), DetallesFoodActivity.class);
                        intent.putExtra("idRest", firebaseRecyclerAdapter.getRef(position).getKey());



                        startActivity(intent);
                    }
                });
            }
        };
        //devuelve el metodo para que paresca los datos en el recyclerView
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}
