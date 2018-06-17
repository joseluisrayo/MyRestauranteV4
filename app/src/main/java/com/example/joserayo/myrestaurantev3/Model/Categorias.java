package com.example.joserayo.myrestaurantev3.Model;

public class Categorias {
private String idcategoria;
private String nombre;
private String Url;

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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Categorias(String nombre, String url) {
        this.nombre = nombre;
        Url = url;
    }

    public Categorias(String categoria, String marca, String precio, String descripcion, String cantidad, String nombreRest, String idMenu) {
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.nombreRest = nombreRest;
        this.idMenu = idMenu;
    }

    private String categoria;
private String marca;
private String precio;
private String descripcion;
private String cantidad;
private String nombreRest;
private String idMenu;

    public Categorias() {
    }





}
