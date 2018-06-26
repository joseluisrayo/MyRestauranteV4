package com.example.joserayo.myrestaurantev3.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

public class MegustaFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;
    DatabaseReference mDatabaselike ;
    RecyclerView.LayoutManager manager;
    private DatabaseReference  mUsers;
    String uid,email;
   TextView recibe;

    FirebaseRecyclerAdapter<LocationModel,MenuViewHolder> firebaseRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.activity_megusta_fragment,container,false);

        recyclerView = (RecyclerView)vista. findViewById(R.id.myreclicleview);

        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        mDatabaselike= FirebaseDatabase.getInstance().getReference().child("Likes");
        mUsers= FirebaseDatabase.getInstance().getReference().child("location2");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }


        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot IDGenerado : dataSnapshot.getChildren()){
                    uid = IDGenerado.getKey();
                        Listar(uid);
                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return vista;

    }

    private void Listar(String uid) {
      firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<LocationModel, MenuViewHolder>(LocationModel.class, R.layout.blog_row, MenuViewHolder.class,
              mDatabaselike .orderByChild(uid).equalTo(uid)) {
          @Override
          protected void populateViewHolder(MenuViewHolder viewHolder, LocationModel model, int position) {
              viewHolder.direccion.setText(model.getDireccion());
              viewHolder.nombrecatego.setText(model.getCategoria());
              viewHolder.nomnrePro.setText(model.getNombreRest());
              Picasso.with(getContext()).load(model.getUrl()).fit().into(viewHolder.imegenPro);

          }
      };
                      recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}
