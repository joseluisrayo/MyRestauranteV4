package com.example.joserayo.myrestaurantev3.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SingUpActivity extends AppCompatActivity{
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView fotoPerfil;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private EditText NombrePer, ApellidoPer,user1, pass1,pass2;
    private Button btnsignup;
    private MaterialDialog dialog;
    private FirebaseAuth firebaseAuth;
    private SignupInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //setViews();
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
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        //se instancia el nombre del nuevo dato a resibir en la dbFirebase
        mStorageRef = FirebaseStorage.getInstance().getReference("users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

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
                    Toast.makeText(SingUpActivity.this, "Registrado Correctamente!!!",Toast.LENGTH_SHORT).show();
                    UsersModel upload = new UsersModel(
                            NombrePer.getText().toString().trim(),
                            ApellidoPer.getText().toString().trim(),
                            user1.getText().toString().trim(),
                            pass1.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    String uplodId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uplodId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SingUpActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    ////------------------------------->

}