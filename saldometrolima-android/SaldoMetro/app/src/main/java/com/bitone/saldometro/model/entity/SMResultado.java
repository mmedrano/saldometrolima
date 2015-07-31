package com.bitone.saldometro.model.entity;

import com.bitone.saldometro.utils.SMString;

import java.util.ArrayList;

public class SMResultado {
    private String mensaje;
    private String detalleMensaje;
    private Object entidad;
    private ArrayList<?> listaEntidades;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalleMensaje() {
        return detalleMensaje;
    }

    public void setDetalleMensaje(String detalleMensaje) {
        this.detalleMensaje = detalleMensaje;
    }

    public boolean esCorrecto(){
        return SMString.esVacioONulo(mensaje);
    }

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }
}
