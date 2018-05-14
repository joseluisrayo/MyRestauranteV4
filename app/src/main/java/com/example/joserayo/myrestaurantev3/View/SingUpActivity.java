package com.example.joserayo.myrestaurantev3.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.joserayo.myrestaurantev3.Interfaces.SignupInterface;
import com.example.joserayo.myrestaurantev3.Presentador.SingUpPresenter;
import com.example.joserayo.myrestaurantev3.R;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpActivity extends AppCompatActivity implements SignupInterface.view2 {
    private EditText user1, pass1,pass2;
    private Button btnsignup;
    private MaterialDialog dialog;
    private FirebaseAuth firebaseAuth;
    private SignupInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        setViews();

    }

    private void setViews() {
        presenter=new SingUpPresenter(this);
        user1=(EditText)findViewById(R.id.signupUser);
        pass1=(EditText)findViewById(R.id.SignupPass1);
        pass2=(EditText)findViewById(R.id.SignupPass2);
        btnsignup=(Button)findViewById(R.id.btnRegis);
        MaterialDialog.Builder builder=new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere porfavor ...")
                .cancelable(false)
                .progress(true,0);

                dialog=builder.build();

                btnsignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Ejecutar();
                    }
                });
    }

    @Override
    public void EnableImputs() {
      setImputs(false);
    }

    private void setImputs(boolean b) {
        user1.setEnabled(b);
        pass1.setEnabled(b);
        pass2.setEnabled(b);
        btnsignup.setEnabled(b);
    }

    @Override
    public void DisableImputs() {
   setImputs(true);
    }

    @Override
    public void ShowProgres() {
    dialog.show();
    }

    @Override
    public void Hideprogres() {
        dialog.dismiss();

    }

    @Override
    public boolean Valida() {

        boolean valida = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(user1.getText().toString().trim()).matches()) {
            user1.setError("No es un email válido.");

          valida=false;


        } else if(TextUtils.isEmpty(pass1.getText())){
            pass1.setError("Campo Obligatorio");
            valida=false;
        } else if(pass1.getText().toString().length()<6){
            pass1.setError("Minimo 7 Caracteres");
            valida=false;
//validando las contraseñas
        } else if(TextUtils.isEmpty(pass2.getText())){
            pass2.setError("Campo Obligatorio");
            valida=false;
        } else if(!pass1.getText().toString().trim().equals(pass2.getText().toString().trim())){
            pass2.setError("Las contraseñas no Considen");
            valida=false;

        }
        return valida;
    }

    @Override
    public void Ejecutar() {
      if(Valida()){
      presenter.doSignUp(user1.getText().toString().trim(),pass1.getText().toString().trim());
      }
    }

    @Override
    public void onSingup() {
      Toast.makeText(this,"Registrado Correctamente",Toast.LENGTH_LONG).show();
      startActivity(new Intent(this,PrincipalActivity.class));
      finish();
    }

    @Override
    public void onError(String error) {
      Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}