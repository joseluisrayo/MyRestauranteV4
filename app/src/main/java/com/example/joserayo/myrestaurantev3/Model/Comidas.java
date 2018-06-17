package com.example.joserayo.myrestaurantev3.Model;

public class Comidas {
    private String entrada;
    private String url;
    private String idmenu;

    public String getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(String idmenu) {
        this.idmenu = idmenu;
    }

    private String segundo;
    private String precio;
    private String cantidad;
    private String nombreRest;

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    private String descripcion1;
private String idRestaurante;

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Comidas(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public Comidas(String entrada, String url, String segundo, String precio, String cantidad, String descripcion1) {
        this.entrada = entrada;
        this.url = url;
        this.segundo = segundo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion1 = descripcion1;
    }

    public Comidas(String entrada, String url) {
        this.entrada = entrada;
        this.url = url;
    }

    public Comidas() {
    }
}
