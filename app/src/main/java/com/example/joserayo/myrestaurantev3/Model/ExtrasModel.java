package com.example.joserayo.myrestaurantev3.Model;

public class ExtrasModel {
    private String idextras;
    private String extra;
    private String url2;
    private Long precioExtra;
    private String nombreRest;
    private String descripcionextra;
    private String idRestaurante;

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public ExtrasModel(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public ExtrasModel() {
    }

    public String getIdextras() {
        return idextras;
    }

    public void setIdextras(String idextras) {
        this.idextras = idextras;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public Long getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(Long precioExtra) {
        this.precioExtra = precioExtra;
    }

    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    public String getDescripcionextra() {
        return descripcionextra;
    }

    public void setDescripcionextra(String descripcionextra) {
        this.descripcionextra = descripcionextra;
    }

    public ExtrasModel(String idextras, Long precioExtra, String nombreRest, String descripcionextra) {
        this.idextras = idextras;
        this.precioExtra = precioExtra;
        this.nombreRest = nombreRest;
        this.descripcionextra = descripcionextra;
    }

    public ExtrasModel(String extra, String url2) {
        this.extra = extra;
        this.url2 = url2;
    }
}
