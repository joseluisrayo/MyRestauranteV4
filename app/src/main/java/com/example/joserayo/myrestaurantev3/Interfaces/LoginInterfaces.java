package com.example.joserayo.myrestaurantev3.Interfaces;

public interface LoginInterfaces {
    interface View2{
        void RegisterValidation(String r);
    }
    interface View{
        void LoginValidation();
        void LoginSucces();
        void LginError();
    }

    interface Presenter{
        void mostrarNotificaciones(String r);
        void LoginNormalPresenter(String email,String pass);
        void LoginFacebookPresenter(String email, String pass);
        void RegistroPresenter(String email, String pass);
    }

    interface Model{
        void LoginNormalModel(String email, String pass);
        void LoginFacebookModel(String email, String pass);
        void RegistroModel(String email, String pass);
    }
}
