package com.example.joserayo.myrestaurantev3.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.joserayo.myrestaurantev3.Interfaces.SignupInterface;
import com.example.joserayo.myrestaurantev3.Model.UsersModel;
import com.example.joserayo.myrestaurantev3.Presentador.SingUpPresenter;
import com.example.joserayo.myrestaurantev3.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SingUpActivity extends AppCompatActivity implements SignupInterface.view2 {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView fotoPerfil;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private String uplodId;
    private EditText NombrePer, ApellidoPer,user1, pass1,pass2;
    private Button btnsignup;
    private MaterialDialog dialog;
    private FirebaseAuth firebaseAuth;
    private SignupInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        //se llama al id del layout
        NombrePer = (EditText)findViewById(R.id.signupNombre);
        ApellidoPer = (EditText)findViewById(R.id.signupApellido);
        user1 = (EditText)findViewById(R.id.signupUser);
        pass1 = (EditText)findViewById(R.id.SignupPass1);
        fotoPerfil = (ImageView) findViewById(R.id.signupFotoPerfil);
        btnsignup = (Button)findViewById(R.id.btnRegis);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        //se instancia el nombre del nuevo dato a resibir en la dbFirebase
        mStorageRef = FirebaseStorage.getInstance().getReference("users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        ///Validar los campos a registar
        setViews();
    }

    //Metodo para llamar al file del telefono
    private void openFileChooser(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data != null && data.getData() !=null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(fotoPerfil);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if (mImageUri != null){
            StorageReference fileReferene = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            fileReferene.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ///Se instancia el modelo userModel para utilizar el get par poder registrar en firebase
                    UsersModel upload = new UsersModel(
                            NombrePer.getText().toString().trim(),
                            ApellidoPer.getText().toString().trim(),
                            user1.getText().toString().trim(),
                            pass1.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    uplodId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uplodId).setValue(upload);
                    //direcciona para que se registre el user en firebaseAunt
                    presenter.doSignUp(user1.getText().toString().trim(),pass1.getText().toString().trim());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SingUpActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

       }else {
            Toast.makeText(this,"Imagen no Seleccionada, Por favor ingrese una imagen para su Perfil",Toast.LENGTH_SHORT).show();
        }
    }

    ////-----Parte de validar los campos antes del registro------->
    private void setViews() {
        presenter=new SingUpPresenter(this);
        NombrePer=(EditText)findViewById(R.id.signupNombre);
        ApellidoPer=(EditText)findViewById(R.id.signupApellido);
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
        NombrePer.setEnabled(b);
        ApellidoPer.setEnabled(b);
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

    public boolean Valida() {
        boolean valida = true;

        if (TextUtils.isEmpty(NombrePer.getText())){
            NombrePer.setError("Campo Obligatorio");
            valida=false;
        }
        else if (TextUtils.isEmpty(ApellidoPer.getText())){
            ApellidoPer.setError("Campo Obligarotio");
            valida=false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(user1.getText().toString().trim()).matches()) {
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

    public void Ejecutar() {
        if(Valida()){
            uploadFile();
        }
    }

    @Override
    public void onSingup() {
        Toast.makeText(this,"Registrado Correctamente",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,PrincipalActivity.class);
        ///se envia el iduser con bundle
        Bundle bundle=new Bundle();
        bundle.putString("nombre", uplodId);
        intent.putExtras(bundle);
        startActivity(intent);
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