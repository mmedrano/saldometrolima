package com.bitone.saldometro.dao.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.model.entity.TipoTarjeta;

public class TipoTarjetaDao {
    public void insertar(Tarjeta nuevaTarjeta){

    }

    public TipoTarjeta obtener(SQLiteDatabase db, int idTipo){
        //Crear nuevo objeto DataSource
        //TarjetaDataSource dataSource = new TarjetaDataSource(context);
        //SQLiteDatabase db = context.openOrCreateDatabase("SaldoMetro.db", mode, null);

        String selection = "idTipoTarjeta = ? ";//
        String selectionArgs[] = new String[]{idTipo+""};

        Cursor c = db.query(
                "TipoTarjeta",  //Nombre de la tabla
                null,  //Lista de Columnas a consultar
                selection,  //Columnas para la clausula WHERE
                selectionArgs,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );

        //List tarjetaList = new ArrayList<>();
        TipoTarjeta tipoTarjeta= new TipoTarjeta();
        while(c.moveToNext()){

            tipoTarjeta.setId(c.getInt(0));
            tipoTarjeta.setNombreTipo(c.getString(1));

            //tarjetaList.add(tipoTarjeta);
        }
        db.close();
        return tipoTarjeta;
    }


}
