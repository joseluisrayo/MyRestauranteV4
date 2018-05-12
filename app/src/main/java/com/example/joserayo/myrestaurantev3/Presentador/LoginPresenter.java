package com.example.joserayo.myrestaurantev3.Presentador;
import com.example.joserayo.myrestaurantev3.Interfaces.LoginInterfaces;
import com.example.joserayo.myrestaurantev3.Model.LoginModel;

public class LoginPresenter implements LoginInterfaces.Presenter,LoginInterfaces.TaskListener{

    private  LoginInterfaces.View vista;
    private LoginInterfaces.Model model;

    public LoginPresenter(LoginInterfaces.View vista) {
        this.vista = vista;
        model= new LoginModel(this);
    }

    @Override
    public void onDestoy() {
        vista=null;
    }

    @Override
    public void LoginFacebookPresenter(String email, String pass) {

    }

    @Override
    public void toLogin(String email, String password) {
        if(vista!=null){

            vista.showprogres();
        }
        model.doLogin(email,password);
    }

    @Override
    public void onSucess() {
        if(vista!=null){

            vista.hideprogres();
            vista.loginValidacion();
        }
    }

    @Override
    public void onError(String error) {
        if(vista!=null){
            vista.hideprogres();
            vista.Error(error);
        }
    }
}
