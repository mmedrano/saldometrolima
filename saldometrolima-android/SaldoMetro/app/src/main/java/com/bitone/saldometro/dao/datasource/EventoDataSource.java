package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class EventoDataSource {

    //Metainformación de la base de datos
    public static final String EVENTO_TABLE_NAME = "EVENTO";
    public static final String STRING_TYPE = "nvarchar";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla Evento
    public static class ColumnQuotes{
        public static final String ID_EVENTO = "idEvento";//BaseColumns._ID;
        public static final String ID_TARJETA = "idTarjeta";
        public static final String TIPO_TARJETA = "tipoTarjeta";
        public static final String DIA = "dia";
        public static final String TIPO_MOVIMIENTO = "tipoMovimiento";
        public static final String MONTO = "monto";
        public static final String FECHA_EVENTO = "fechaEvento";
    }

    //Script de Creación de la EVENTO
    public static final String CREATE_EVENTO_SCRIPT =
            "create table "+EVENTO_TABLE_NAME+"(" +
                    ColumnQuotes.ID_EVENTO+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.ID_TARJETA+" "+INT_TYPE+" not null," +
                    ColumnQuotes.TIPO_TARJETA+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.DIA+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.TIPO_MOVIMIENTO+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.MONTO+" "+DEC_TYPE+" not null," +
                    ColumnQuotes.FECHA_EVENTO+" "+STRING_TYPE+" not null)";



    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public EventoDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
