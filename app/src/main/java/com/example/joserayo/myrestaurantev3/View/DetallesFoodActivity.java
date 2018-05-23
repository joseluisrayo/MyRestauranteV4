package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.Model.LocationModel;
import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetallesFoodActivity extends AppCompatActivity {
    TextView nombreRes,categoriRest,numerocel;
    ImageView imageRest;
    CardView call;
    String foodid="";
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference location2;
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_food);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //instanciamos la base de datos
        firebaseDatabase = FirebaseDatabase.getInstance();
        location2 = firebaseDatabase.getReference("location2");

        //int texviw
        nombreRes = (TextView)findViewById(R.id.food_name);
        categoriRest = (TextView)findViewById(R.id.nombreCategoria);
        numerocel = (TextView)findViewById(R.id.layout_llamar);
        imageRest = (ImageView)findViewById(R.id.imag_food);

        //Se cambia los nombre s en el collagse
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollagsedAppbar);


        //Get foodid from Intent
        if(getIntent()!=null){
            foodid = getIntent().getStringExtra("idRest");
        }if (!foodid.isEmpty()){
            getDetailFood(foodid);
        }
    }

    private void getDetailFood(String foodid){
        location2.child(foodid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final LocationModel food = dataSnapshot.getValue(LocationModel.class);
                final String numero=String.valueOf(food.getTelefono());

                //setImagen
                Picasso.with(getBaseContext()).load(food.getUrl()).into(imageRest);
                collapsingToolbarLayout.setTitle(food.getNombreRest());
                nombreRes.setText(food.getNombreRest());
                categoriRest.setText(food.getCategoria());
                numerocel.setText(String.valueOf(food.getTelefono()));

                numerocel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+numero));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
