package com.example.joserayo.myrestaurantev3.Model;

import android.text.TextUtils;

import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements LoginInterfaces.Model{
    private LoginInterfaces.Presenter presenter;
    private FirebaseAuth firebaseAuth;

    public LoginModel(LoginInterfaces.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void LoginNormalModel(String email, String pass) {

    }

    @Override
    public void LoginFacebookModel(String email, String pass) {

    }

    @Override
    public void RegistroModel(String email, String pass) {
        firebaseAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            String notify = "Please enter email and enter password";
            presenter.mostrarNotificaciones(notify);
        }else {
            final Task<AuthResult> resultTask = firebaseAuth.createUserWithEmailAndPassword(email, pass);

            if (resultTask != null) {

                String succes = "Registered Successfully";
                presenter.mostrarNotificaciones(succes);

            }else {

                String error = "Error, please try again";
                presenter.mostrarNotificaciones(error);
            }
        }
    }


}
