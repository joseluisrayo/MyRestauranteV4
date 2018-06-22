package com.example.joserayo.myrestaurantev3.Presentador;

import com.example.joserayo.myrestaurantev3.Interfaces.SignupInterface;
import com.example.joserayo.myrestaurantev3.Model.SignupModel;

public class SingUpPresenter implements SignupInterface.Presenter ,SignupInterface.validar{
    SignupInterface.view2 view2;
    SignupInterface.Model model;

    public SingUpPresenter(SignupInterface.view2 view2) {
        this.view2 = view2;
        model=new SignupModel(this);
    }

    @Override

    public void onDestroy() {
     view2=null;
    }

    @Override
    public void doSignUp(String Email, String Password) {
    if(view2!=null){

        view2.ShowProgres();
    }
    model.Registro(Email,Password);
    }

    @Override
    public void onSucess() {
   if(view2!=null){

       view2.Hideprogres();
       view2.onSingup();
   }
    }

    @Override
    public void onError(String error) {
     if (view2!=null){

         view2.Hideprogres();
         view2.onError(error);
     }
    }
}
