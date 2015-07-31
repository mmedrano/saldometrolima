package com.bitone.saldometro.dao.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.bitone.saldometro.dao.datasource.TarjetaDataSource;
import com.bitone.saldometro.model.entity.HistoriaTarjeta;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.utils.Globales;

public class TarjetaDao {
    private Context context;

    public TarjetaDao(Context context){
        this.context = context;
    }

    public Tarjeta obtener(int id){
        TarjetaDataSource dataSource = new TarjetaDataSource(this.context);
        SQLiteDatabase db = this.context.openOrCreateDatabase(Globales.DBNAME, Context.MODE_PRIVATE, null);

        String columns[] = new String[]{"idTarjeta","tarifa","printf(\"%.2f\", saldo) as saldo","idTipoTarjeta"};
        String selection = "idTarjeta = ? ";//
        String selectionArgs[] = new String[]{id+""};
        Cursor c = db.query(
                "Tarjeta",  //Nombre de la tabla
                null,  //Lista de Columnas a consultar
                selection,  //Columnas para la clausula WHERE
                selectionArgs,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );

        Tarjeta tarjeta= new Tarjeta();
        while(c.moveToNext()){
            tarjeta.setId(c.getInt(0));
            tarjeta.setTarifa(c.getDouble(1));
            tarjeta.setSaldo(c.getDouble(2));
            TipoTarjetaDao ttd= new TipoTarjetaDao();
            tarjeta.setTipo(ttd.obtener(db, c.getInt(3)));;
        }
        return tarjeta;
    }

    public void actualizar(Tarjeta tarjetaModificada){
        TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase(Globales.DBNAME, Context.MODE_PRIVATE, null);

        //Nuestro contenedor de valores
        ContentValues values = new ContentValues();

        //Seteando
        values.put("saldo", tarjetaModificada.getSaldo());

        //Clausula WHERE
        String selection = "idTarjeta = ?";
        String[] selectionArgs = { tarjetaModificada.getId()+"" };

        //Actualizando
        db.update("Tarjeta", values, selection, selectionArgs);
    }


    public List<HistoriaTarjeta> obtenerHistorial(Context context, int mode, int idTarjeta){

        TarjetaDataSource dataSource = new TarjetaDataSource(context);
        SQLiteDatabase db = context.openOrCreateDatabase(Globales.DBNAME, mode, null);

        String[] args = new String[] {""+idTarjeta};
        /*String MY_QUERY = "SELECT * FROM MovimientoSaldo ms INNER JOIN TipoMovimiento tipmov ON ms.idTipoMovimiento=tipmov.idTipoMovimiento INNER JOIN" +
                " Tarjeta ta ON ms.idTarjeta=ta.idTarjeta INNER JOIN TipoTarjeta tipta ON " +
                "ta.idTipoTarjeta=tipta.idTipoTarjeta WHERE ms.idTarjeta=?";*/

        String MY_QUERY = "SELECT tipta.tipoTarjeta, tipmov.tipoMovimiento, ms.monto, ms.fechaMovimiento FROM Tarjeta ta " +
                "INNER JOIN TipoTarjeta tipta ON ta.idTipoTarjeta=tipta.idTipoTarjeta INNER JOIN" +
                " MovimientoSaldo ms ON ta.idTarjeta=ms.idTarjeta INNER JOIN TipoMovimiento tipmov ON " +
                "ms.idTipoMovimiento=tipmov.idTipoMovimiento WHERE ta.idTarjeta=?";

        Cursor c = db.rawQuery(MY_QUERY, args);

        List<HistoriaTarjeta> historiaTarjetaList=null;
        if (c.moveToFirst()) {

            historiaTarjetaList= new ArrayList<HistoriaTarjeta>();
            do {
                HistoriaTarjeta historiaTarjeta= new HistoriaTarjeta();

                historiaTarjeta.setTipoTarjeta(c.getString(0));
                historiaTarjeta.setTipoMovimiento(c.getString(1));
                historiaTarjeta.setMonto(c.getDouble(2));
                historiaTarjeta.setFechaMovimiento(c.getString(3));

                historiaTarjetaList.add(historiaTarjeta);
            } while(c.moveToNext());
        }
        return historiaTarjetaList;
    }
}
