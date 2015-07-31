package com.bitone.saldometro.utils;

import java.util.StringTokenizer;

public class SMString {
    public static boolean esVacioONulo(String cadena){
        if(cadena == null) return true;
        else return (cadena.length() == 0);
    }

    public static String obtenerFormatoMonto(double monto){
        String saldoFormato = Double.toString(monto);

        StringTokenizer st = new StringTokenizer(saldoFormato, ".");
        st.nextToken();

        if(st.nextToken().length() == 1) saldoFormato = saldoFormato + "0";

        return saldoFormato;
    }
}
