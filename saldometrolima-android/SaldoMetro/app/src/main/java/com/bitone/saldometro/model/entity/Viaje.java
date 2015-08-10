package com.bitone.saldometro.model.entity;

/**
 * Created by Christian Medrano on 09/08/2015.
 */
public class Viaje {

    private String descripcion;
    private String hora;

    public Viaje(){};
    public Viaje(String descripcion, String hora){
        this.descripcion=descripcion;
        this.hora=hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
