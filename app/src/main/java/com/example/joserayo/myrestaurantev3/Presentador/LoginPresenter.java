package com.example.joserayo.myrestaurantev3.Presentador;

import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.Model.LoginModel;
import com.example.joserayo.myrestaurantev3.View.SingUpActivity;

public class LoginPresenter implements LoginInterfaces.Presenter{
    private LoginInterfaces.View2 view;
    private LoginInterfaces.Model model;

    public LoginPresenter(SingUpActivity view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void mostrarNotificaciones(String r) {
        if(view!=null){
            view.RegisterValidation(r);
        }
    }

    @Override
    public void LoginNormalPresenter(String email, String pass) {

    }

    @Override
    public void LoginFacebookPresenter(String email, String pass) {

    }

    @Override
    public void RegistroPresenter(String email, String pass) {
        if (view != null){
            model.RegistroModel(email,pass);
        }
    }
}
