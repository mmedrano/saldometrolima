package com.bitone.saldometro.dao.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bitone.saldometro.dao.datasource.TarjetaDataSource;
import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.utils.Globales;

public class MovimientoDao {
    private Context context;

    public MovimientoDao(Context context){
        this.context = context;
    }

    public void insertar(MovimientoSaldo nuevoMovimientoSaldo){
        TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase(Globales.DBNAME, Context.MODE_PRIVATE, null);
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando columnas

        values.put("cantidadPersonas",nuevoMovimientoSaldo.getCantidadPersonas());
        values.put("monto",nuevoMovimientoSaldo.getMonto());
        values.put("fechaMovimiento",nuevoMovimientoSaldo.getFechaMovimiento());
        values.put("idTarjeta",nuevoMovimientoSaldo.getIdTarjeta());
        values.put("idTipoMovimiento",nuevoMovimientoSaldo.getIdTipoMovimiento());

        //Insertando en la base de datos
        db.insert("MovimientoSaldo",null,values);
        db.close();
    }

    public void eliminarMovimientos(){
        //String selection = " Column = ?";
        //String[] selectionArgs = { "3" };
        TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase(Globales.DBNAME, Context.MODE_PRIVATE, null);
        db.delete("MovimientoSaldo", null, null);
        db.close();
    }


}
