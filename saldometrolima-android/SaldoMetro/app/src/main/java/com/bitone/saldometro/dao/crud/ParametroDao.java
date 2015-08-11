package com.bitone.saldometro.dao.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.bitone.saldometro.dao.datasource.TarjetaDataSource;
import com.bitone.saldometro.model.entity.Parametro;
import com.bitone.saldometro.utils.Globales;


public class ParametroDao {
    private Context context;

    public ParametroDao(Context context){
        this.context = context;
    }

    public List<Parametro> obtenerParametros(String abrevParametro){

        TarjetaDataSource dataSource = new TarjetaDataSource(context);//Es el que se utiliza por defecto para la DB
        SQLiteDatabase db = context.openOrCreateDatabase(Globales.DBNAME, Context.MODE_PRIVATE, null);

        String selection = "abrev = ? ";//
        String selectionArgs[] = new String[]{abrevParametro+""};


        Cursor c = db.query(
                "Parametro",  //Nombre de la tabla
                null,  //Lista de Columnas a consultar
                selection,  //Columnas para la clausula WHERE
                selectionArgs,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );


        List<Parametro> parametroList= new ArrayList<Parametro>();
        while(c.moveToNext()){
            Parametro parametro = new Parametro();

            parametro.setIdParametro(c.getInt(0));
            parametro.setAbrev(c.getString(1));
            parametro.setParametro(c.getString(2));
            parametro.setDesParametro(c.getString(3));

            parametroList.add(parametro);
        }
        db.close();

        return parametroList;
    }
}
