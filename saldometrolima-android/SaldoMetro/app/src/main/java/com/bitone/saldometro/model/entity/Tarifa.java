package com.bitone.saldometro.model.entity;

public class Tarifa {
    public static double TARIFA_BASE = 1.50;

    private boolean feriado;
    private double monto;

    public boolean esFeriado() {
        return feriado;
    }

    public void setFeriado(boolean feriado) {
        this.feriado = feriado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
