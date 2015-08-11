package com.bitone.saldometro.dao.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.bitone.saldometro.dao.datasource.TarjetaDataSource;
import com.bitone.saldometro.model.entity.Estacion;

public class EstacionDao {
    public void insertar(Estacion nuevaEstacion){

    }

    public List<Estacion> obtenerEstaciones(Context context, int mode){

        TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase("SaldoMetro.db", mode, null);


        Cursor c = db.query(
                "Estacion",  //Nombre de la tabla
                null,  //Lista de Columnas a consultar
                null,  //Columnas para la clausula WHERE
                null,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );


        List<Estacion> estacionList= new ArrayList<Estacion>();
        while(c.moveToNext()){
            Estacion estacion = new Estacion();

            estacion.setIdEstacion(c.getInt(0));
            estacion.setNombreEstacion(c.getString(1));
            estacion.setDistrito(c.getString(2));
            estacion.setUbicacion(c.getString(3));
            estacion.setCoordenada(c.getString(4));

            estacionList.add(estacion);
        }
        db.close();

        return estacionList;
    }
}
