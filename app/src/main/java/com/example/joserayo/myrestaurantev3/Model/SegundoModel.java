package com.example.joserayo.myrestaurantev3.Model;

public class SegundoModel {
    private String idRestaurante;
    private String segundo;
    private  String url4;
    private Long Preciosegundo;
    private String descripcionsegundo;

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getUrl4() {
        return url4;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public Long getPreciosegundo() {
        return Preciosegundo;
    }

    public void setPreciosegundo(Long preciosegundo) {
        Preciosegundo = preciosegundo;
    }

    public String getDescripcionsegundo() {
        return descripcionsegundo;
    }

    public void setDescripcionsegundo(String descripcionsegundo) {
        this.descripcionsegundo = descripcionsegundo;
    }

    public SegundoModel() {
    }

    public SegundoModel(String idRestaurante, Long preciosegundo, String descripcionsegundo) {
        this.idRestaurante = idRestaurante;
        Preciosegundo = preciosegundo;
        this.descripcionsegundo = descripcionsegundo;
    }

    public SegundoModel(String segundo, String url4) {
        this.segundo = segundo;
        this.url4 = url4;
    }
}
