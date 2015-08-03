package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class TipoMovimientoDataSource {

    //Metainformación de la base de datos
    public static final String TIPOMOVIMIENTO_TABLE_NAME = "TipoMovimiento";
    public static final String STRING_TYPE = "nvarchar";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla TipoMovimiento
    public static class ColumnQuotes{
        public static final String ID_TIPOMOVIMIENTO = "idTipoMovimiento";//BaseColumns._ID;
        public static final String TIPO_MOVIMIENTO = "tipoMovimiento";
    }

    //Script de Creación de
    public static final String CREATE_TIPOMOVIMIENTO_SCRIPT =
            "create table "+TIPOMOVIMIENTO_TABLE_NAME+"(" +
                    ColumnQuotes.ID_TIPOMOVIMIENTO+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.TIPO_MOVIMIENTO+" "+STRING_TYPE+" not null)";

    //Scripts de inserción por defecto
    public static final String INSERT_TIPOMOVIMIENTO_SCRIPT =
            "insert into "+TIPOMOVIMIENTO_TABLE_NAME+" values(null, 'Recarga')," +
                    "(null,'Viaje'),(null,'Reestablecimiento de saldo'),(null,'Cambio de tarjeta')";

    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public TipoMovimientoDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
