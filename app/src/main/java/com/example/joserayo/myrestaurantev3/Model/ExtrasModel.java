package com.example.joserayo.myrestaurantev3.Model;

public class ExtrasModel {
    private String extra;
    private String url2;
    private Long precioExtra;
    private String descripcionextra;
    private String idRestaurante;

    public ExtrasModel() {
    }

    public ExtrasModel(String extra, String url2) {
        this.extra = extra;
        this.url2 = url2;
    }

    public ExtrasModel(Long precioExtra, String descripcionextra, String idRestaurante) {
        this.precioExtra = precioExtra;
        this.descripcionextra = descripcionextra;
        this.idRestaurante = idRestaurante;
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

    public String getDescripcionextra() {
        return descripcionextra;
    }

    public void setDescripcionextra(String descripcionextra) {
        this.descripcionextra = descripcionextra;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}
