package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class MovimientoSaldoDataSource {

    //Metainformación de la base de datos
    public static final String MOVIMIENTOSALDO_TABLE_NAME = "MovimientoSaldo";
    public static final String STRING_TYPE = "nvarchar";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla MovimientoSaldo
    public static class ColumnQuotes{
        public static final String ID_MOVIMIENTOSALDO = "idMovimientoSaldo";//BaseColumns._ID;
        public static final String CANTIDAD_PERSONAS = "cantidadPersonas    ";
        public static final String MONTO = "monto";
        public static final String FECHA_MOVIMIENTO = "fechaMovimiento";
        public static final String ID_TARJETA = "idTarjeta";
        public static final String ID_TIPOMOVIMIENTO = "idTipoMovimiento";
    }

    //Script de Creación de MovimientoSaldo
    public static final String CREATE_MOVIMIENTOSALDO_SCRIPT =
            "create table "+MOVIMIENTOSALDO_TABLE_NAME+"(" +
                    ColumnQuotes.ID_MOVIMIENTOSALDO+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.CANTIDAD_PERSONAS+" "+INT_TYPE+" not null," +
                    ColumnQuotes.MONTO+" "+DEC_TYPE+" not null," +
                    ColumnQuotes.FECHA_MOVIMIENTO+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.ID_TARJETA+" "+INT_TYPE+" not null," +
                    ColumnQuotes.ID_TIPOMOVIMIENTO+" "+INT_TYPE+" not null)";



    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public MovimientoSaldoDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}