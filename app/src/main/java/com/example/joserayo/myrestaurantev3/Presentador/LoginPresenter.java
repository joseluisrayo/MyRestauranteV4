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
        if(r.equals("Please enter email and enter password")){
            view.RegisterValidation(r);

        }else if (r.equals("Registered Successfully")){
            view.RegisterSucces(r);

        }else if (r.equals("Error, please try again")){
            view.RegisterError(r);
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
