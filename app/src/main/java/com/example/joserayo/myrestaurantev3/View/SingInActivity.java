package com.example.joserayo.myrestaurantev3.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.Presentador.LoginPresenter;
import com.example.joserayo.myrestaurantev3.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class SingInActivity extends AppCompatActivity implements LoginInterfaces.View{
    private EditText user2,pass2;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth firebaseAuth;
    private LoginInterfaces.Presenter presenter;
    private Button btnlogin;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        //login normal


        setvie();




        //add dependenci facebook
        callbackManager = CallbackManager.Factory.create();





        //login por facebook
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment(this);
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Se Canceló la Operación",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Ocurrió un problema al Ingresar",Toast.LENGTH_SHORT).show();
            }
        });



        }




    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        //loginButton.setVisibility(View.GONE);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                }

                //loginButton.setVisibility(View.VISIBLE);
            }
        });
    }
//declara los id a las etiquetas
    private void setvie() {
        presenter= new LoginPresenter(this);
        user2 = (EditText) findViewById(R.id.signin_user_id);
        pass2 = (EditText) findViewById(R.id.signin_pass_id);

//creo el progresbar
        btnlogin=(Button)findViewById(R.id.btnlogin);
        MaterialDialog.Builder builder=new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere Porfavor")
                .cancelable(false)
                .progress(true,0);
         dialog=builder.build();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Ejecutar();
            }
        });

    }
//  Inicio login normal



    @Override
    public void showprogres() {
        dialog.show();

    }

    @Override
    public void hideprogres() {
       dialog.dismiss();
    }

    @Override
    public void Ejecutar() {
        if(!isValidEmail()){
           Toast.makeText(this,"Correo Invalido",Toast.LENGTH_LONG).show();
        } else if(isValidPass()){
            Toast.makeText(this,"Password Invalido",Toast.LENGTH_LONG).show();
        } else{
            presenter.toLogin(user2.getText().toString(),pass2.getText().toString());
        }

    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(user2.getText().toString()).matches();
    }

    @Override
    public boolean isValidPass() {
        if (TextUtils.isEmpty(pass2.getText().toString())) {
            Toast.makeText(this, "No es una contraseña válida", Toast.LENGTH_SHORT).show();
            pass2.setError("No es una contraseña válida");

        }
        return false;
    }

    @Override
    public void loginValidacion() {
      Toast.makeText(this,"Bienvenido al Prinsipal SÑR(@): "+user2.getText().toString().trim(),Toast.LENGTH_LONG).show();

      Intent intent=new Intent(SingInActivity.this,PrincipalActivity.class);
      intent.putExtra("user",user2.getText().toString().trim());
      startActivity(intent);

        finish();

    }

    @Override
    public void Error(String error) {
       Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestoy();
    }
    public void registrar(View view){
        startActivity(new Intent(this,SingUpActivity.class));

    }
}
