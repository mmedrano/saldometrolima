package com.bitone.saldometro.model.business;

import android.content.Context;

import java.util.List;

import com.bitone.saldometro.dao.crud.TarjetaDao;
import com.bitone.saldometro.model.entity.HistoriaTarjeta;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.Tarjeta;

public class TarjetaBusiness {
    private TarjetaDao tarjetaDao = null;
    private Context context;

    public TarjetaBusiness(Context context){
        this.context = context;
    }

    public SMResultado consultarTarjeta(int id){
        SMResultado resultado;
        try{
            resultado = new SMResultado();
            tarjetaDao = new TarjetaDao(context);
            Tarjeta tarjetaConsultada = tarjetaDao.obtener(id);
            resultado.setEntidad(tarjetaConsultada);
        }catch (Exception ex){
            resultado = new SMResultado();
            resultado.setMensaje("Ocurrio un error al consultar la tarjeta.");
            resultado.setDetalleMensaje(ex.getMessage());
        }finally {
            tarjetaDao = null;
        }

        return resultado;
    }

    public List<HistoriaTarjeta> obtenerHistorial(Context context, int mode, int idTarjeta){
        tarjetaDao= new TarjetaDao(context);
        List<HistoriaTarjeta> historiaTarjetaList=tarjetaDao.obtenerHistorial(context, mode, idTarjeta);

        return historiaTarjetaList;
    }
}
