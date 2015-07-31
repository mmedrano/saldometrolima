package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class TarjetaDataSource {

    //Metainformación de la base de datos
    public static final String TARJETA_TABLE_NAME = "Tarjeta";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla Tarjeta
    public static class ColumnQuotes{
        public static final String ID_TARJETA = "idTarjeta";//BaseColumns._ID;
        public static final String TARIFA_TARJETA = "tarifa";
        public static final String SALDO_TARJETA = "saldo";
        public static final String IDTIPO_TARJETA = "idTipoTarjeta";
    }

    //Script de Creación de Tarjeta
    public static final String CREATE_TARJETA_SCRIPT =
            "create table "+TARJETA_TABLE_NAME+"(" +
                    ColumnQuotes.ID_TARJETA+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.TARIFA_TARJETA+" "+DEC_TYPE+" not null," +
                    ColumnQuotes.SALDO_TARJETA+" "+DEC_TYPE+" not null," +
                    ColumnQuotes.IDTIPO_TARJETA+" "+INT_TYPE+" not null)";

    //Scripts de inserción por defecto
    public static final String INSERT_TARJETA_SCRIPT =
            "insert into "+TARJETA_TABLE_NAME+" values(null, 1.50, 0.00, 1),(null, 0.75, 0.00, 2),(null, 0.75, 0.00,3)";


    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public TarjetaDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
