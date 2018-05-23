package com.example.joserayo.myrestaurantev3.Presentador;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.joserayo.myrestaurantev3.R;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nomnrePro,nombrecatego,direccion;
    public ImageView imegenPro;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);
        nomnrePro = (TextView)itemView.findViewById(R.id.post_nombreRest);
        nombrecatego = (TextView)itemView.findViewById(R.id.post_categoria);
        direccion = (TextView)itemView.findViewById(R.id.post_direccion);
        imegenPro = (ImageView)itemView.findViewById(R.id.postImgen);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
