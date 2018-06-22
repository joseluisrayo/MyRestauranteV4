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
import com.example.joserayo.myrestaurantev3.Model.Categorias;
import com.example.joserayo.myrestaurantev3.Model.Comidas;
import com.example.joserayo.myrestaurantev3.Model.ComidasHolder;
import com.example.joserayo.myrestaurantev3.Model.ExtrasModel;
import com.example.joserayo.myrestaurantev3.Model.ExtrasVHolder;
import com.example.joserayo.myrestaurantev3.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class FragmentLisExtras  extends Fragment  {
    RecyclerView recyclerView;
    String id="";
    String fecha="";
    RecyclerView.LayoutManager manager;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseRecyclerAdapter<ExtrasModel,ExtrasVHolder> recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extras, container, false);

        fecha();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Categorias");

        recyclerView = (RecyclerView)rootView. findViewById(R.id.myreclicleview);

        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        if(getActivity().getIntent()!=null){
            id =getActivity(). getIntent().getStringExtra("id");

        }if (!id.isEmpty()){
            listar(id);
        }

        return rootView;
    }

    private void fecha(){
        String[] dias={"Domingo","Lunes","Martes", "Miercoles","Jueves","Viernes","Sabado"};
        int numeroDia = 0;
        Calendar cal= Calendar.getInstance();
        numeroDia = cal.get(Calendar.DAY_OF_WEEK);
        fecha = dias[numeroDia - 1];
    }

    private void listar(String id) {
         recyclerAdapter=new FirebaseRecyclerAdapter<ExtrasModel, ExtrasVHolder>(ExtrasModel.class,R.layout.item_categoria,
                 ExtrasVHolder.class,reference.child(fecha).child("Extras").orderByChild("idRestaurante").equalTo(id)) {
             @Override
             protected void populateViewHolder(ExtrasVHolder viewHolder, ExtrasModel model, int position) {
                          viewHolder.nombre.setText(model.getExtra());
                          viewHolder.descripio.setText(model.getDescripcionextra());
                 viewHolder.precio.setText(String.valueOf(model.getPrecioExtra()));
                 Picasso.with(getContext()).load(model.getUrl2()).fit()
                         .into(viewHolder.foto);
                 final ExtrasModel comidas=model;

                 viewHolder.setItemClickListener(new ItemClickListener() {
                     @Override
                     public void onClick(View view, int position, boolean isLongClick) {
                         //Toast.makeText(getActivity(),""+comidas.getExtra(),Toast.LENGTH_LONG).show();
                     }
                 });
             }
         };

        recyclerView.setAdapter(recyclerAdapter);
    }
}
