package com.example.joserayo.myrestaurantev3.Model;

public class LocationModel {

    private String IdRestaurante;
    private String IdUser;
    private String nombreRest;
    private String url;
    private Long Telefono;
    private String direccion;
    private String categoria;
    Double latitude;
    Double longitude;

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public void setIdRestaurante(String idRestaurante) {
        IdRestaurante = idRestaurante;
    }

    public String getIdRestaurante() {
        return IdRestaurante;
    }


    public LocationModel() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private String horarios;

    public LocationModel(Long telefono) {
        Telefono = telefono;
    }

    public Long getTelefono() {
        return Telefono;
    }

    public void setTelefono(Long telefono) {
        Telefono = telefono;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public LocationModel(String horarios) {
        this.horarios = horarios;
    }



    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public LocationModel( String numero, String direccion, String categoria, double latitude, double longitude) {

        this.direccion = direccion;
        this.categoria = categoria;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }





    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





    public LocationModel(String nombreRest,  String url) {
        this.nombreRest = nombreRest;

        this.url = url;


    }

}
