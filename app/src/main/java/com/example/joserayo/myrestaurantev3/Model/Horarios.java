package com.example.joserayo.myrestaurantev3.Model;

public class Horarios {
    private  String iddia;
    private String dia;

    public String getIddia() {
        return iddia;
    }

    public void setIddia(String iddia) {
        this.iddia = iddia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Horarios() {
    }

    public Horarios(String iddia, String dia) {
        this.iddia = iddia;
        this.dia = dia;

    }
}
