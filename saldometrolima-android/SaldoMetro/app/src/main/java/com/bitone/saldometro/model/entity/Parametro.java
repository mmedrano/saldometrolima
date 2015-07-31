package com.bitone.saldometro.model.entity;

/**
 * Created by Christian Medrano on 18/07/2015.
 */
public class Parametro {
    public static final String FERIADO = "FERIADOS";

    private int idParametro;
    private String abrev;
    private String parametro;
    private String desParametro;

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getDesParametro() {
        return desParametro;
    }

    public void setDesParametro(String desParametro) {
        this.desParametro = desParametro;
    }
}
