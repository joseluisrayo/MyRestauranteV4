package com.example.joserayo.myrestaurantev3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpActivity extends AppCompatActivity{
    private EditText user2,pass2;
    private Button btnsignup,btnface2;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        user2 = (EditText) findViewById(R.id.signup_user_id);
        pass2 = (EditText) findViewById(R.id.signup_pass_id);
        btnsignup = (Button) findViewById(R.id.btnLogerSignUp);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

       if (firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    public void registerUser(View view){
        String email = user2.getText().toString().trim();
        String password = pass2.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user..");
        progressDialog.show();

        //Inicializamos relacionando con la db
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                        //profile activity here
                        finish();
                        startActivity(new Intent(SingUpActivity.this,HomeActivity.class));
                        Toast.makeText(SingUpActivity.this,"Registereed Successfully",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SingUpActivity.this,"Could not register, please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
