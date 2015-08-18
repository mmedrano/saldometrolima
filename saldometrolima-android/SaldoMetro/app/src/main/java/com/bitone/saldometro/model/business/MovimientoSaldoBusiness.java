package com.bitone.saldometro.model.business;

import android.content.Context;
import android.text.Html;


import com.bitone.saldometro.dao.crud.MovimientoDao;
import com.bitone.saldometro.dao.crud.TarjetaDao;
import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.Tarjeta;

/**
 * Created by Christian Medrano on 18/07/2015.
 */
public class MovimientoSaldoBusiness {
    private Context context;
    private MovimientoDao movimientoDao = null;
    private TarjetaDao tarjetaDao = null;

    public MovimientoSaldoBusiness(Context context){
        this.context = context;
    }

    public SMResultado recargarSaldo(MovimientoSaldo nuevoMovimientoSaldo, Tarjeta tarjetaAcualizada){
        SMResultado resultado;
        try{
            resultado = new SMResultado();
            if(nuevoMovimientoSaldo == null){
                resultado.setMensaje("Llene los datos solicitados.");
            }else{
                if(nuevoMovimientoSaldo.getMonto() == 0 && nuevoMovimientoSaldo.getIdTipoMovimiento()!=MovimientoSaldo.MOV_REESTABLECER_SALDO){
                    resultado.setMensaje("El monto a recargar debe ser mayor a cero.");
                }
                else{
                    movimientoDao = new MovimientoDao(context);
                    movimientoDao.insertar(nuevoMovimientoSaldo);
                    tarjetaDao = new TarjetaDao(context);
                    tarjetaDao.actualizar(tarjetaAcualizada);
                }
            }
        }catch (Exception ex){
            resultado = new SMResultado();
            resultado.setMensaje("Ocurrió un error al registrar el movimiento de saldo.");
            resultado.setDetalleMensaje(ex.getMessage());
        }
        return resultado;
    }




    public SMResultado marcarViaje(MovimientoSaldo nuevoMovimientoSaldo, Tarjeta tarjetaAcualizada){
        SMResultado resultado;
        try{
            resultado = new SMResultado();
            if(nuevoMovimientoSaldo == null){
                resultado.setMensaje("Llene los datos solicitados.");
            }else{
                movimientoDao = new MovimientoDao(context);
                movimientoDao.insertar(nuevoMovimientoSaldo);
                tarjetaDao = new TarjetaDao(context);
                tarjetaDao.actualizar(tarjetaAcualizada);
                resultado.setMensaje("Se ha descontado S/" + nuevoMovimientoSaldo.getMonto() + " de su tarjeta");
            }
        }catch (Exception ex){
            resultado = new SMResultado();
            resultado.setMensaje("Ocurrió un error al registrar el movimiento de saldo.");
            resultado.setDetalleMensaje(ex.getMessage());
        }
        return resultado;
    }

    /*public MovimientoSaldo consultarMovimientos(Context context, int mode, int id){
        MovimientoDao movimientoDao= new MovimientoDao();
        return movimientoDao.obtener(context, mode, id);
    }*/
}
