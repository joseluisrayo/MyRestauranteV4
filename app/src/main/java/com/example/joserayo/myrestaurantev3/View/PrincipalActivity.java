package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Model.UsersModel;
import com.example.joserayo.myrestaurantev3.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nombuser;
    private ImageView imagenPhoto;
    private TextView nombuser2;
    String userid="";
    String correop="";
    String nombreperr;
    String apellidperr;
    String urlImgen;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //instanciamos la base de datos
        users = FirebaseDatabase.getInstance().getReference().child("users");
        users.keepSynced(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buscador Restaurantes");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ///validamos el iduser recibido
        View hView = navigationView.getHeaderView(0);
        nombuser = (TextView) hView.findViewById(R.id.nombreUser);
        nombuser2 =(TextView) hView.findViewById(R.id.nombreUser);
        imagenPhoto = (ImageView)hView.findViewById(R.id.imageView);

        //Bundle para obtener el correo
        Bundle bundle2 = PrincipalActivity.this.getIntent().getExtras();
        correop = bundle2.getString("correo"); ///se resive el email de MainActivity
        if(bundle2!=null){
            users.addValueEventListener(new ValueEventListener() { ///se instancia para poder recorrer en la db_firebase para validar el email(user)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot IDGenerado : dataSnapshot.getChildren()){
                        String useremail = IDGenerado.child("email").getValue(String.class);
                        if (useremail.equals(correop)){
                            nombreperr= IDGenerado.child("nombreUser").getValue(String.class);
                            //apellidperr = IDGenerado.child("apellidoUser").getValue(String.class);
                            urlImgen = IDGenerado.child("mImagenUrl").getValue(String.class);
                            userid = IDGenerado.getKey();
                        }
                    }
                    //se envia los datos obtenidos para que se muestren en el menu
                    nombuser.setText(nombreperr);
                    Picasso.with(getBaseContext()).load(urlImgen).into(imagenPhoto);
                    Toast.makeText(PrincipalActivity.this,"El idUser:"+userid,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            nombuser2.setText("Eat Guest");
        }
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListaFragment()).commit();

    }

    private void goloinScreen(){
        finish();
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment selectedFrament = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        else if (id == R.id.nav_about) {

        }
        else if (id == R.id.nav_MiRest) {


        }
        else if (id==R.id.nav_Restau){
            Intent intent= new Intent(PrincipalActivity.this,HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("idUsers", userid);
            intent.putExtras(bundle);
            startActivity(intent);
            //startActivity(new Intent(PrincipalActivity.this,HomeActivity.class));
        }
        else if (id == R.id.nav_exit) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goloinScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrament = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_ListaRest:
                            selectedFrament = new ListaFragment();
                            break;
                        case R.id.navigation_LocaMaps:
                            selectedFrament = new LocationMapsFragment();
                            break;
                        case R.id.navigation_Megusta:
                            selectedFrament = new MegustaFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrament).commit();
                    return true;
                }
            };
}