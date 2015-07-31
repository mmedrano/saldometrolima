package com.bitone.saldometro.model.business;

import android.content.Context;

import java.util.List;

import com.bitone.saldometro.dao.crud.EstacionDao;
import com.bitone.saldometro.model.entity.Estacion;

public class EstacionBusiness {
    private EstacionDao estacionDao=null;



    public List<Estacion> obtenerEstaciones(Context context, int mode){
        estacionDao= new EstacionDao();
        List<Estacion> estacionList=estacionDao.obtenerEstaciones(context, mode);

        return estacionList;
    }
}
