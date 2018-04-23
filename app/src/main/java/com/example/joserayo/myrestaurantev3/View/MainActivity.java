package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{
    private Button btnsignUp, btnsignIn;
    private TextView textSlogann;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsignIn = (Button) findViewById(R.id.btnSignIn);
        btnsignUp = (Button) findViewById(R.id.btnSignUp);
        textSlogann = (TextView) findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        textSlogann.setTypeface(face);

       firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingInActivity.class);
                startActivity(intent);
            }
        });

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingUpActivity.class);
                startActivity(intent);
            }
        });

    }



}
