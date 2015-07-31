package com.bitone.saldometro.model.entity;

public class Tarjeta {
    private int id;
    private double tarifa;
    private double saldo;
    private TipoTarjeta tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    public double getTarifa() {return tarifa;}

    public void setTarifa(double tarifa) {this.tarifa = tarifa;}
}
