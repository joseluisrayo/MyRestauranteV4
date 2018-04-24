package com.example.joserayo.myrestaurantev3.View;

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
import com.google.firebase.auth.FirebaseUser;

public class SingInActivity extends AppCompatActivity implements LoginInterfaces.View{
    private EditText user2,pass2;
    private Button btnsignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private LoginInterfaces.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        //add dependenci facebook
        callbackManager = CallbackManager.Factory.create();

        user2 = (EditText) findViewById(R.id.signin_user_id);
        pass2 = (EditText) findViewById(R.id.signin_pass_id);
        btnsignIn = (Button) findViewById(R.id.btnLogerSignIn);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        presenter = new LoginPresenter(this);

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

        //inicializamos la bd de firebase
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goMainScreen();
                }
              }
          };

        }

    public void LoginUsuarios(View view){
        //cambiamos las variable para enviar al pesentador-->RegistroPresenter
        String email = user2.getText().toString().trim();
        String password = pass2.getText().toString().trim();
        //se envia los datos capturados al metodo registroPresenter
        presenter.LoginNormalPresenter(email,password);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        loginButton.setVisibility(View.GONE);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                }

                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goMainScreen(){
        Intent intent= new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void LoginValidation(String p) {
        Toast.makeText(SingInActivity.this,p,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoginSucces(String p) {
        goMainScreen();
        Toast.makeText(SingInActivity.this,p,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LginError(String p) {
        Toast.makeText(SingInActivity.this,p,Toast.LENGTH_SHORT).show();
    }
}
