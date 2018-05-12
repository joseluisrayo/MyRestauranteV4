package com.example.joserayo.myrestaurantev3.Interfaces;

public interface LoginInterfaces {

    interface View{
        void showprogres();
        void hideprogres();
        void Ejecutar();
        boolean isValidEmail();
        boolean isValidPass();
        void loginValidacion();
        void Error(String error);
    }

    interface Presenter{
        void onDestoy();
        void LoginFacebookPresenter(String email, String pass);

        void toLogin(String email, String password);
    }

    interface Model{
        void doLogin(String email, String password);
        void LoginFacebookModel(String email, String pass);

    }
    interface TaskListener{
        void onSucess();
        void onError(String error);
    }
}
