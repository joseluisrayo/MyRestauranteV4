package com.example.joserayo.myrestaurantev3.Model;

public class Comidas {
    private String entrada;
    private String url;
    private String idmenu;
    private long precio1;
    private  String segundo;
    private String descripcion;
    private String nombreRest;
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

    public String getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(String idmenu) {
        this.idmenu = idmenu;
    }

    public long getPrecio1() {
        return precio1;
    }

    public void setPrecio1(long precio1) {
        this.precio1 = precio1;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    public Comidas(String idmenu, long precio1, String segundo, String descripcion, String nombreRest) {
        this.idmenu = idmenu;
        this.precio1 = precio1;
        this.segundo = segundo;
        this.descripcion = descripcion;
        this.nombreRest = nombreRest;
    }

    public Comidas(String entrada, String url) {
        this.entrada = entrada;
        this.url = url;
    }

    public Comidas() {
    }
}
