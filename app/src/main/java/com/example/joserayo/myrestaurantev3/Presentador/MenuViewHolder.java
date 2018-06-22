package com.example.joserayo.myrestaurantev3.Presentador;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.R;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nomnrePro,nombrecatego,direccion;
    public ImageView imegenPro,favorito;
 DatabaseReference mDatabaselike;
 FirebaseAuth auth;
 Context context;
    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);
        nomnrePro = (TextView)itemView.findViewById(R.id.post_nombreRest);
        nombrecatego = (TextView)itemView.findViewById(R.id.post_categoria);
        direccion = (TextView)itemView.findViewById(R.id.post_direccion);
        imegenPro = (ImageView)itemView.findViewById(R.id.postImgen);
        favorito=(ImageView)itemView.findViewById(R.id.favorite1);

        mDatabaselike= FirebaseDatabase.getInstance().getReference().child("Likes");
        auth=FirebaseAuth.getInstance();
        mDatabaselike.keepSynced(true);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLike(final String poskey){
           mDatabaselike.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if (dataSnapshot.child(poskey).hasChild(auth.getCurrentUser().getUid())){

                       favorito.setImageResource(R.drawable.ic_favorite);
                      


                   }else {
                       favorito.setImageResource(R.drawable.ic_favorite_black_24dp);


                   }

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
