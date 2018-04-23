package com.example.joserayo.myrestaurantev3.Presentador;

import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.Model.LoginModel;
import com.example.joserayo.myrestaurantev3.View.SingInActivity;
import com.example.joserayo.myrestaurantev3.View.SingUpActivity;

public class LoginPresenter implements LoginInterfaces.Presenter{
    private LoginInterfaces.View2 view;
    private LoginInterfaces.View view2;
    private LoginInterfaces.Model model;

    public LoginPresenter(SingUpActivity view) {
        this.view = view;
        model = new LoginModel(this);
    }

    public LoginPresenter(SingInActivity view2) {
        this.view2 = view2;
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
    public void mostrarNotificacionLogNorm(String p) {

        if(p.equals("Please enter email and enter password")){
            view2.LoginValidation(p);

        }else if (p.equals("Bienvenido")){
            view2.LoginSucces(p);

        }else if (p.equals("Error, please try again")){
            view2.LginError(p);
        }

    }

    @Override
    public void mostrarNotificacionLogFace(String r) {

    }

    @Override
    public void LoginNormalPresenter(String email, String pass) {
        if (view2 !=null){
            model.LoginNormalModel(email,pass);
        }
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
