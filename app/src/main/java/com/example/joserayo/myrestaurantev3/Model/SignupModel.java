package com.example.joserayo.myrestaurantev3.Model;

import android.support.annotation.NonNull;

import com.example.joserayo.myrestaurantev3.Interfaces.SignupInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupModel implements SignupInterface.Model {
    SignupInterface.validar validar;
    FirebaseAuth auth;


    public SignupModel(SignupInterface.validar validar) {
        this.validar = validar;
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public void Registro(String Email, String Password) {
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 validar.onSucess();

             } else if (task.getException()!=null){
                 validar.onError(task.getException().getMessage());
             }
            }
        });

    }
}
