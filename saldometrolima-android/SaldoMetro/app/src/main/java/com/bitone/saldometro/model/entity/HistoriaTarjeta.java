package com.bitone.saldometro.model.entity;

/**
 * Created by Christian Medrano on 22/07/2015.
 */
public class HistoriaTarjeta {

    private String tipoTarjeta;
    private String tipoMovimiento;
    private double monto;
    private String fechaMovimiento;

    public HistoriaTarjeta(String fechaMovimiento, String tipoMovimiento, double monto){
        this.fechaMovimiento=fechaMovimiento;
        this.tipoMovimiento=tipoMovimiento;
        this.monto=monto;
    }
    public HistoriaTarjeta(){}
    public String getTipoTarjeta() {return tipoTarjeta;}

    public void setTipoTarjeta(String tipoTarjeta) {this.tipoTarjeta = tipoTarjeta;}

    public String getTipoMovimiento() {return tipoMovimiento;}

    public void setTipoMovimiento(String tipoMovimiento) {this.tipoMovimiento = tipoMovimiento;}

    public double getMonto() {return monto;}

    public void setMonto(double monto) {this.monto = monto;}

    public String getFechaMovimiento() {return fechaMovimiento;}

    public void setFechaMovimiento(String fechaMovimiento) {this.fechaMovimiento = fechaMovimiento;}
}
