package com.example.joserayo.myrestaurantev3.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.R;

public class BebidasHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nombre, descripio,precio;
    public ImageView foto;
    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    public BebidasHolder(View itemView) {
        super(itemView);

        nombre=(TextView)itemView.findViewById(R.id.nom);
        descripio=(TextView)itemView.findViewById(R.id.descr);
        precio=(TextView) itemView.findViewById(R.id.precio);
        foto=(ImageView)itemView.findViewById(R.id.imagenlistar);
        itemView.setOnClickListener(this);
        itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
