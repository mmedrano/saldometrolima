package com.bitone.saldometro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.model.entity.TipoTarjeta;

/**
 * Created by Christian Medrano on 19/07/2015.
 */
public class SMPreferences {
    private SharedPreferences preferences;
    private Context context;
    private Editor editor;

    private final String TARJETA = "TARJETA";
    private final String NOMBRE_PREFERENCES = "SALDOMETRO_PREFERENCES";

    public SMPreferences(Context context){
        this.context = context;
        preferences = this.context.getSharedPreferences(NOMBRE_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean existeTarjetaSeleccionada(){
        return obtenerTarjetaSeleccionada() != TipoTarjeta.DEFAULT;
    }

    public int obtenerTarjetaSeleccionada(){
        return preferences.getInt(TARJETA, TipoTarjeta.DEFAULT);
    }

    public void registrarTarjetaSeleccionada(TipoTarjeta tarjetaSeleccionada){
        editor.putInt(TARJETA, tarjetaSeleccionada.getId());
        editor.commit();
    }





    public void registrarActualizarPreferencia(Context context, String preferencia, String key,String value, int mode){
        SharedPreferences prefs =
                context.getSharedPreferences(preferencia,mode);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String  obtenerPreferenciaTarjeta(Context context, int mode){
        SharedPreferences prefs =
                context.getSharedPreferences("MisPreferencias", mode);
        return prefs.getString("tarjeta", "0");
    }


}
