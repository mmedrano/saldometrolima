package com.bitone.saldometro.model.entity;

/**
 * Created by Christian Medrano on 25/07/2015.
 */
public class Estacion {

    private int idEstacion;
    private String nombreEstacion;
    private String distrito;
    private String ubicacion;
    private String coordenada;

    public int getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCoordenada() {return coordenada;}

    public void setCoordenada(String coordenada) {this.coordenada = coordenada;}

    public Estacion(){}
    public Estacion(int idEstacion, String nombreEstacion, String distrito, String ubicacion, String coordenada){
        this.idEstacion = idEstacion;
        this.nombreEstacion = nombreEstacion;
        this.distrito = distrito;
        this.ubicacion = ubicacion;
        this.coordenada = coordenada;
    }


}
