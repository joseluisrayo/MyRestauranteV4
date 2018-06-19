package com.example.joserayo.myrestaurantev3.Model;

public class Categorias {
private String idcategoria;
private String nombre;
private String Url1;
private String categoria;
private String marca;
private String precio;
private String describebidas;
private String cantidad;
private String idRestaurante;

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Categorias(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    private String nombreRest;
private String idMenu;

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public Categorias(String idcategoria, String categoria, String marca, String precio, String describebidas, String cantidad, String nombreRest, String idMenu) {
        this.idcategoria = idcategoria;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
        this.describebidas = describebidas;
        this.cantidad = cantidad;
        this.nombreRest = nombreRest;
        this.idMenu = idMenu;
    }

    public Categorias(String nombre, String url1) {
        this.nombre = nombre;
        Url1 = url1;
    }

    public Categorias() {

    }




}
