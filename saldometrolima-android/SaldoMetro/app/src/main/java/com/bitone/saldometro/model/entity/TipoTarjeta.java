package com.bitone.saldometro.model.entity;

public class TipoTarjeta {
    public static final int DEFAULT = 0;
    public static final int ADULTO = 1;
    public static final int UNIVERSITARIO = 2;
    public static final int ESCOLAR = 3;

    private int id;
    private String nombreTipo;

    public TipoTarjeta(int id){
        this.id = id;
    }

    public TipoTarjeta(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}
