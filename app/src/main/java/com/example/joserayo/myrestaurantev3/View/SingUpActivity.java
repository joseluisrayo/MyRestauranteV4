package com.example.joserayo.myrestaurantev3.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.Presentador.LoginPresenter;
import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpActivity extends AppCompatActivity implements LoginInterfaces.View2{
    private EditText user2,pass2;
    private Button btnsignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private LoginInterfaces.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        user2 = (EditText) findViewById(R.id.signup_user_id);
        pass2 = (EditText) findViewById(R.id.signup_pass_id);
        btnsignup = (Button) findViewById(R.id.btnLogerSignUp);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        presenter = new LoginPresenter(this);

       if (firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    public void registerUser(View view){
        //cambiamos las variable para enviar al pesentador-->RegistroPresenter
        String email = user2.getText().toString().trim();
        String password = pass2.getText().toString().trim();
        //se envia los datos capturados al metodo registroPresenter
        presenter.RegistroPresenter(email,password);
    }

    @Override
    public void RegisterSucces(String r) {
        finish();
        goMainScreen();
        Toast.makeText(SingUpActivity.this,r,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterError(String r) {
        Toast.makeText(SingUpActivity.this,r,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterValidation(String r) {
        //Se recibe la respuesta del loginModelo(r)
        Toast.makeText(SingUpActivity.this,r,Toast.LENGTH_SHORT).show();
    }
    private void goMainScreen(){
        Intent intent= new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
