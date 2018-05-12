package com.example.joserayo.myrestaurantev3.Model;



import android.support.annotation.NonNull;

import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements LoginInterfaces.Model{
    private LoginInterfaces.Presenter presenter;
    private FirebaseAuth firebaseAuth;
    private LoginInterfaces.TaskListener listener;

    public LoginModel(LoginInterfaces.TaskListener listener) {
        this.listener = listener;
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    listener.onSucess();
                } else
                if(task.getException()!=null){
                    listener.onError(task.getException().getMessage());

                }
            }
        });
    }

    @Override
    public void LoginFacebookModel(String email, String pass) {

    }


}
