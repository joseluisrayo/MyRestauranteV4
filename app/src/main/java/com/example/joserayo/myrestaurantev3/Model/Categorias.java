package com.example.joserayo.myrestaurantev3.Model;

public class Categorias {
    private String nombre;
    private String Url1;
    private String precio;
    private String describebidas;
    private String idRestaurante;

    public Categorias() {
    }

    public Categorias(String nombre, String url1) {
        this.nombre = nombre;
        Url1 = url1;
    }

    public Categorias(String nombre, String url1, String precio, String describebidas, String idRestaurante) {
        this.nombre = nombre;
        Url1 = url1;
        this.precio = precio;
        this.describebidas = describebidas;
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl1() {
        return Url1;
    }

    public void setUrl1(String url1) {
        Url1 = url1;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescribebidas() {
        return describebidas;
    }

    public void setDescribebidas(String describebidas) {
        this.describebidas = describebidas;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}
