package com.example.joserayo.myrestaurantev3.Model;

public class LocationModel {


    public LocationModel(String direccion, String nombreRest, String categoria, String url) {
        this.direccion = direccion;
        this.nombreRest = nombreRest;
        this.categoria = categoria;
        this.url = url;
    }

    private String direccion;
    private String nombreRest;
    private String categoria;
    private String url;

    public LocationModel() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
