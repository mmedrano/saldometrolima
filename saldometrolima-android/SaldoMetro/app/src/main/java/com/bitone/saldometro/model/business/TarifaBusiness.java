package com.bitone.saldometro.model.business;

import android.content.Context;

import com.bitone.saldometro.dao.crud.ParametroDao;
import com.bitone.saldometro.model.entity.Parametro;
import com.bitone.saldometro.model.entity.Tarifa;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.Validar;

import java.util.List;
import java.util.StringTokenizer;

public class TarifaBusiness {
    private Context context;
    private static double TARIFA_NORMAL = 1.50;
    private static double tarifaActual = 0.00;

    public TarifaBusiness(Context context){
        this.context = context;
    }

    public Tarifa obtenerTarifa(Tarjeta tarjetaBase){
        Tarifa tarifaActual = new Tarifa();
        if(esFeriado()){
            tarifaActual.setMonto(Validar.round(Tarifa.TARIFA_BASE, 2));
            tarifaActual.setFeriado(true);
        }else{
            tarifaActual.setMonto(Validar.round(tarjetaBase.getTarifa(), 2));
            tarifaActual.setFeriado(false);
        }
        return tarifaActual;
    }

    public String obtenerFormatoViajes(double montoSaldo, double montoTarifa){
        try{
            StringTokenizer st= new StringTokenizer(Double.toString(montoSaldo / montoTarifa), ".");
            return st.nextToken();
        }catch (Exception ex){
            return "0";
        }
    }

    public boolean esFeriado(){
        boolean feriado = false;
        ParametroDao parametroDao = new ParametroDao(context);
        List<Parametro> lstParametros = parametroDao.obtenerParametros(Parametro.FERIADO);

        CalculaHorario calculaHorario = new CalculaHorario();
        String fechaActual = calculaHorario.obtenerFechaActual("dd/MM");

        for(Parametro prm : lstParametros){
            if(prm.getParametro().equals(fechaActual)){
                feriado = true;
                break;
            }
        }

        return feriado;
    }

}
