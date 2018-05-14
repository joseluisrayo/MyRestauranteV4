package com.example.joserayo.myrestaurantev3.Interfaces;

public interface SignupInterface {
    interface view2{
        void EnableImputs();
        void DisableImputs();
        void ShowProgres();
        void Hideprogres();
     boolean Valida();
        void Ejecutar();
        void onSingup();
        void onError(String error);

    }
    interface Model{
     void Registro(String Email, String Password);
    }

    interface Presenter{
        void onDestroy();
        void doSignUp(String Email, String Password);
    }

    interface validar{
        void onSucess();
        void onError(String error);
    }
}
