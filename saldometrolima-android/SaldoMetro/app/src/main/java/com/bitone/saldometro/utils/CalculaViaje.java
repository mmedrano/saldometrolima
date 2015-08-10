package com.bitone.saldometro.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Christian Medrano on 06/08/2015.
 */
public class CalculaViaje {

    public static final int ORIGEN_DEFAULT = 0;
    public static final int DESTINO_DEFAULT = 0;

    public List<ViajeEstacion> obtenerCalculoViajes(int idEstacion){
        List <ViajeEstacion> viajeEstacionList = new ArrayList();
        for(int i=0; i<CalculaHorario.SALIDAS_A_BAYOVAR.length; i++){
            ViajeEstacion viajeEstacion = new ViajeEstacion();
            viajeEstacion.setIdEstacion(i);

            if(obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[idEstacion-1])==obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[i])){
                viajeEstacion.setMinutosViaje("Estación actual");
            }
            else if(obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[idEstacion-1])>obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[i])){
                viajeEstacion.setMinutosViaje((obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[idEstacion-1])-obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[i]))+" min");
            }
            else if(obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[idEstacion-1])<obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[i])){
                viajeEstacion.setMinutosViaje((obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[i])-obtieneMinutos(CalculaHorario.SALIDAS_A_BAYOVAR[idEstacion-1]))+" min");
            }

            viajeEstacionList.add(viajeEstacion);
        }
        //viajeEstacionList.remove(idEstacion-1);
        return viajeEstacionList;
    }


    public int obtieneMinutos(String hora){
        StringTokenizer st = new StringTokenizer(hora,":");
        st.nextToken();
        int minuto = Integer.parseInt(st.nextToken());
        return minuto;
    }


    public class ViajeEstacion{
        private int idEstacion;
        private String nombreEstacion="";
        private String minutosViaje;

        public int getIdEstacion() {return idEstacion;}

        public void setIdEstacion(int idEstacion) {this.idEstacion = idEstacion;}

        public String getMinutosViaje() {return minutosViaje;}

        public void setMinutosViaje(String minutosViaje) {this.minutosViaje = minutosViaje;}

        public String getNombreEstacion() {return nombreEstacion;}

        public void setNombreEstacion(String nombreEstacion) {this.nombreEstacion = nombreEstacion;}
    }
}
