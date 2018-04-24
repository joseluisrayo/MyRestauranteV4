package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    //private FirebaseAuth firebaseAuth;
    private TextView nombuser;
    private Button btnLogUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user !=null){
            //profile activity here
          //  finish();
           // startActivity(new Intent(this,SingInActivity.class));
            btnLogUp = (Button) findViewById(R.id.logout);
            nombuser = (TextView) findViewById(R.id.nombreUser);
            nombuser.setText("Bienvenido:"+user.getEmail());
        }else {
            goloinScreen();
        }

    }
    private void goloinScreen(){
        finish();
        Intent intent= new Intent(this, SingInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void serrarSeccion(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goloinScreen();
    }
}
