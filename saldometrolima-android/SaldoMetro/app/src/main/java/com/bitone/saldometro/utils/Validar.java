package com.bitone.saldometro.utils;

import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.TipoMovimiento;
import com.bitone.saldometro.model.entity.TipoTarjeta;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Created by Christian Medrano on 19/07/2015.
 */
public class Validar {

    public double roundTwoDecimals(double d){
        DecimalFormat twoDForm = new DecimalFormat("#,##");
        return Double.valueOf(twoDForm.format(d));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static SMResultado validaDouble(String credito, double saldo, int tipoMovimiento, int tipoTarjeta){
        SMResultado resultado;
        try {
            resultado = new SMResultado();
            if(Double.parseDouble(credito)<0){
                resultado.setMensaje("Por favor ingrese un monto válido.");
            }
            else if(SMString.esVacioONulo(credito)){
                resultado.setMensaje("Debe ingresar el monto.");
            }else if(Double.parseDouble(credito) == 0 && tipoMovimiento!=MovimientoSaldo.MOV_REESTABLECER_SALDO){
                resultado.setMensaje("Por favor ingrese un monto válido");
            }else{
                double montoAValidarMaximo=0;
                if(tipoMovimiento== MovimientoSaldo.MOV_RECARGA){montoAValidarMaximo=Double.parseDouble(credito) + saldo;}
                else if(tipoMovimiento== MovimientoSaldo.MOV_REESTABLECER_SALDO) {montoAValidarMaximo=Double.parseDouble(credito);}
                if(montoAValidarMaximo > Globales.MONTO_MAXIMO_RECARGA){
                    resultado.setMensaje("Ha sobrepasado el límite, el saldo máximo es S/." + Double.toString(Globales.MONTO_MAXIMO_RECARGA));
                }
                else if(credito.contains(".") || credito.contains(",")){
                    StringTokenizer st = new StringTokenizer(credito,".,");
                    String e = st.nextToken();
                    if(SMString.esVacioONulo(e)){
                        resultado.setMensaje("Por favor ingrese un monto válido.");
                    }else{
                        String d = st.nextToken();
                        if(d.length() > 2){
                            resultado.setMensaje("Por favor ingrese un monto válido.");
                        }
                        else if(d.length() == 2){
                            if(tipoTarjeta != TipoTarjeta.ADULTO && tipoMovimiento==MovimientoSaldo.MOV_REESTABLECER_SALDO){
                                if(Integer.parseInt(d)%5 != 0){
                                    resultado.setMensaje("Por favor ingrese un monto válido.");
                                }
                            }
                            else{
                                if(Integer.parseInt(d.charAt(1) + "") > 0){
                                    resultado.setMensaje("Por favor ingrese un monto válido.");
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (NumberFormatException ex){
            resultado = new SMResultado();
            resultado.setMensaje("Por favor ingrese un monto válido.");
            resultado.setDetalleMensaje(ex.getMessage());
        }
        catch (Exception ex){
            resultado = new SMResultado();
            resultado.setMensaje("Por favor ingrese un monto válido.");
            resultado.setDetalleMensaje(ex.getMessage());
        }
        return resultado;
    }


    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }



    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
