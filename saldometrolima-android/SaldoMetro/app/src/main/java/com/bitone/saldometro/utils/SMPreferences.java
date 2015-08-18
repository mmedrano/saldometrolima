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

    public String obtenerNombreTarjetaSeleccionada(int idTipoTarjeta){
        return idTipoTarjeta ==1 ? "Adulto" : (idTipoTarjeta ==2 ? "Universitario" : "Escolar");
    }


}
