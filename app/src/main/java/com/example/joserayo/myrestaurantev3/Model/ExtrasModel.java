package com.example.joserayo.myrestaurantev3.Model;

public class ExtrasModel {
    private String idextras;
    private String extra;
    private String url;
    private Long precioExtra;
    private String nombreRest;
    private String descripcion;

    public Long getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(Long precioExtra) {
        this.precioExtra = precioExtra;
    }

    public ExtrasModel(Long precioExtra) {
        this.precioExtra = precioExtra;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getNombreRest() {
        return nombreRest;
    }

    public void setNombreRest(String nombreRest) {
        this.nombreRest = nombreRest;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ExtrasModel(String idextras, String precioExtra, String nombreRest, String descripcion) {
        this.idextras = idextras;

        this.nombreRest = nombreRest;
        this.descripcion = descripcion;
    }

    public ExtrasModel(String extra, String url) {
        this.extra = extra;
        this.url = url;
    }

    public ExtrasModel() {
    }
}
