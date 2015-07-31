package com.bitone.saldometro.model.entity;

/**
 * Created by Christian Medrano on 18/07/2015.
 */
public class MovimientoSaldo {
    public static final int MOV_RECARGA = 1;
    public static final int MOV_MARCAR_VIAJE = 2;
    public static final int MOV_REESTABLECER_SALDO = 1;

    private int idMovimiento;
    private int cantidadPersonas;
    private double monto;
    private String fechaMovimiento;
    private int idTarjeta;
    private int idTipoMovimiento;

    public int getIdMovimiento() {return idMovimiento;}

    public void setIdMovimiento(int idMovimiento) {this.idMovimiento = idMovimiento;}

    public int getCantidadPersonas() {return cantidadPersonas;}

    public void setCantidadPersonas(int cantidadPersonas) {this.cantidadPersonas = cantidadPersonas;}

    public double getMonto() {return monto;}

    public void setMonto(double monto) {this.monto = monto;}

    public String getFechaMovimiento() {return fechaMovimiento;}

    public void setFechaMovimiento(String fechaMovimiento) {this.fechaMovimiento = fechaMovimiento;}

    public int getIdTarjeta() {return idTarjeta;}

    public void setIdTarjeta(int idTarjeta) {this.idTarjeta = idTarjeta;}

    public int getIdTipoMovimiento() {return idTipoMovimiento;}

    public void setIdTipoMovimiento(int idTipoMovimiento) {this.idTipoMovimiento = idTipoMovimiento;}
}
