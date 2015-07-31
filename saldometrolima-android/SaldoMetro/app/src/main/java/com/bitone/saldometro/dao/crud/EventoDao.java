package com.bitone.saldometro.dao.crud;

import android.content.Context;

import com.bitone.saldometro.model.entity.Evento;


/*//Campos de la tabla Evento
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
*/

public class EventoDao {
    public void insertar(Context context, int mode,Evento nuevoEvento){
        /*TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase("SaldoMetro.db", mode, null);
        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando columnas

        values.put("idTajeta",nuevoMovimientoSaldo.getCantidadPersonas());
        values.put("tipoTarjeta",nuevoMovimientoSaldo.getMonto());
        values.put("dia",nuevoMovimientoSaldo.getFechaMovimiento());
        values.put("tipoMovimiento",nuevoMovimientoSaldo.getMonto());
        values.put("monto",nuevoMovimientoSaldo.getIdTarjeta());
        values.put("fechaEvento",nuevoMovimientoSaldo.getIdTipoMovimiento());

        //Insertando en la base de datos
        db.insert("MovimientoSaldo",null,values);*/


    }



}
