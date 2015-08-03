package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class TipoTarjetaDataSource {

    //Metainformación de la base de datos
    public static final String TIPOTARJETA_TABLE_NAME = "TipoTarjeta";
    public static final String STRING_TYPE = "nvarchar";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla TipoTarjeta
    public static class ColumnQuotes{
        public static final String ID_TIPOTARJETA = "idTipoTarjeta";//BaseColumns._ID;
        public static final String TIPO_TARJETA = "tipoTarjeta";
    }

    //Script de Creación de TipoTarjeta
    public static final String CREATE_TIPOTARJETA_SCRIPT =
            "create table "+TIPOTARJETA_TABLE_NAME+"(" +
                    ColumnQuotes.ID_TIPOTARJETA+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.TIPO_TARJETA+" "+STRING_TYPE+" not null)";

    //Scripts de inserción por defecto
    public static final String INSERT_TIPOTARJETA_SCRIPT =
            "insert into "+TIPOTARJETA_TABLE_NAME+" values(null, 'Tarjeta Adulto')," +
                    "(null,'Tarjeta Medio Universitario'),(null,'Tarjeta Medio Escolar')";

    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public TipoTarjetaDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}