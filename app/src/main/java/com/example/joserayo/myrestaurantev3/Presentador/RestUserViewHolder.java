package com.example.joserayo.myrestaurantev3.Presentador;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.Interfaces.ItemClickListener;
import com.example.joserayo.myrestaurantev3.R;

public class RestUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView nomnrePro2,nombrecatego2,direccion2;
    public ImageView imegenPro2;
    private ItemClickListener itemClickListener;

    public RestUserViewHolder(View itemView) {
        super(itemView);
        nomnrePro2 = (TextView)itemView.findViewById(R.id.post_nombreRest2);
        nombrecatego2 = (TextView)itemView.findViewById(R.id.post_categoria2);
        direccion2 = (TextView)itemView.findViewById(R.id.post_direccion2);
        imegenPro2 = (ImageView)itemView.findViewById(R.id.postImgen2);

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
