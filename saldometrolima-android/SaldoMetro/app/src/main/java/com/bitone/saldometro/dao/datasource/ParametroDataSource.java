package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class ParametroDataSource {

    //Metainformación de la base de datos
    public static final String PARAMETRO_TABLE_NAME = "Parametro";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla Parametro
    public static class ColumnQuotes{
        public static final String ID_PARAMETRO = "idParametro";//BaseColumns._ID;
        public static final String ABREV = "abrev";
        public static final String PARAMETRO = "parametro";
        public static final String DESPARAMETRO = "desParametro";
    }

    //Script de Creación de Parametro
    public static final String CREATE_PARAMETRO_SCRIPT =
            "create table "+PARAMETRO_TABLE_NAME+"(" +
                    ColumnQuotes.ID_PARAMETRO+" "+INT_TYPE+" primary key," +
                    ColumnQuotes.ABREV+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.PARAMETRO+" "+STRING_TYPE+" not null,"+
                    ColumnQuotes.DESPARAMETRO+" "+STRING_TYPE+" not null)";

    //Scripts de inserción por defecto
    public static final String INSERT_PARAMETRO_SCRIPT =
            "insert into "+PARAMETRO_TABLE_NAME +
                    "(" + ColumnQuotes.ID_PARAMETRO + "," + ColumnQuotes.ABREV + "," + ColumnQuotes.PARAMETRO + "," + ColumnQuotes.DESPARAMETRO + ")" +
                    " VALUES " +
                    "(1, 'PASAJEADULTO', '1.50','Tarifa de la tarjeta adulto');"
                    /*+
                    "(2,'PASAJEMEDIO','0.75','Tarifa de la tarjeta de medio pasaje')," +
                    "(3,'FERIADOS','01/01','Ano Nuevo')," +
                    "(4,'FERIADOS','01/05','Dia del Trabajo')," +
                    "(5,'FERIADOS','24/06','Inti Raymi (Fiesta Inca del Sol)'),"+
                    "(6,'FERIADOS','29/06','Festividad de San Pedro y San Pablo')," +
                    "(7,'FERIADOS','28/07','Fiesta de la Independecia Nacional')," +
                    "(8,'FERIADOS','29/07','Fiesta de la Independecia Nacional(2)')," +
                    "(9,'FERIADOS','30/08','Festividad de Santa Rosa de Lima')," +
                    "(10,'FERIADOS','08/10','Celebracion de la Batalla de Angamos')," +
                    "(11,'FERIADOS','01/11','Dia de Todos los Santos')," +
                    "(12,'FERIADOS','08/12','Dia de la Inmaculada Concepcion')," +
                    "(13,'FERIADOS','25/12','Navidad')"*/
            ;



    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public ParametroDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}