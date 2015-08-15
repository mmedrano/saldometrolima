package com.bitone.saldometro.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitone.saldometro.dao.crud.MovimientoDao;
import com.bitone.saldometro.model.business.MovimientoSaldoBusiness;
import com.bitone.saldometro.model.business.TarjetaBusiness;
import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.Globales;
import com.bitone.saldometro.utils.SMPreferences;
import com.bitone.saldometro.utils.SMString;
import com.bitone.saldometro.utils.Validar;


public class ReestablecerSaldoFragment extends Fragment implements View.OnClickListener{
    public ReestablecerSaldoFragment() {
        // Required empty public constructor
    }

    EditText txtSaldoReestablecido;
    TextView tvSaldoIncorrecto;
    Button btnReestablecer;
    Context context;
    View rootView;

    SMPreferences preferences;
    double montoSaldo = 0.00;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.getActivity().setTitle("Reestablecer Saldo");
        context=this.getActivity().getApplicationContext();
        rootView = inflater.inflate(R.layout.fragment_reestablecer_saldo, container, false);

        txtSaldoReestablecido = (EditText)rootView.findViewById(R.id.txtSaldoReestablece);
        tvSaldoIncorrecto = (TextView)rootView.findViewById(R.id.tvSaldoIncorrecto);
        btnReestablecer = (Button)rootView.findViewById(R.id.btnReestablecer);

        btnReestablecer.setOnClickListener(this);

        CargarDatosTarjeta();

        return rootView;
    }




    private void CargarDatosTarjeta(){
        preferences = new SMPreferences(context);
        int tarjetaSeleccionada = preferences.obtenerTarjetaSeleccionada();

        TarjetaBusiness tarjetaBusiness = new TarjetaBusiness(context);
        SMResultado resultado = tarjetaBusiness.consultarTarjeta(tarjetaSeleccionada);

        if(resultado.esCorrecto()){
            Tarjeta tarjetaActual = (Tarjeta)resultado.getEntidad();
            if(tarjetaActual != null){
                montoSaldo = Validar.round(tarjetaActual.getSaldo(), 2);

                tvSaldoIncorrecto.setText(SMString.obtenerFormatoMonto(montoSaldo));
                //tvTarjeta.setText(tarjetaActual.getTipo().getNombreTipo());
            }
        }else{
            Toast.makeText(context, resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarMontoReestableer(){
        String strMontoReestablecer = txtSaldoReestablecido.getText().toString();
        if(strMontoReestablecer.length() == 0) strMontoReestablecer = "0.00";
        SMResultado resultadoValidacion = Validar.validaDouble(strMontoReestablecer, montoSaldo, MovimientoSaldo.MOV_REESTABLECER_SALDO);
        if(resultadoValidacion.esCorrecto()){
            return true;
        }else{
            Toast.makeText(this.getActivity(), resultadoValidacion.getMensaje(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void reestablecerSaldo(){
        try{
            String strMontoReestablecer = txtSaldoReestablecido.getText().toString();
            if(strMontoReestablecer.length() == 0) strMontoReestablecer = "0.00";
            double montoReestablece = Validar.round(Double.parseDouble(strMontoReestablecer), 2);

            MovimientoSaldo movimientoSaldo = new MovimientoSaldo();
            movimientoSaldo.setCantidadPersonas(0);
            CalculaHorario calculaHorario = new CalculaHorario(this.getActivity().getApplicationContext());
            movimientoSaldo.setFechaMovimiento(calculaHorario.obtenerFechaActual("dd-MMM-yyyy h:mm a"));

            movimientoSaldo.setIdTarjeta(preferences.obtenerTarjetaSeleccionada());
            movimientoSaldo.setIdTipoMovimiento(MovimientoSaldo.MOV_REESTABLECER_SALDO);
            movimientoSaldo.setMonto(montoReestablece);

            Tarjeta tarjetaActualizada= new Tarjeta();
            tarjetaActualizada.setId(preferences.obtenerTarjetaSeleccionada());
            tarjetaActualizada.setSaldo(Validar.round((montoReestablece), 2));

            MovimientoSaldoBusiness movimientoSaldoBusiness = new MovimientoSaldoBusiness(context);
            SMResultado resultado = movimientoSaldoBusiness.recargarSaldo(movimientoSaldo, tarjetaActualizada);

            if(resultado.esCorrecto()){
                confirmaBorrarHistorial();

                /*Intent intentMain = new Intent();
                intentMain.setClass(context, MainActivity.class);
                intentMain.setAction(Intent.ACTION_MAIN);
                startActivity(intentMain);
                this.getActivity().finish();*/
            }else{
                Toast.makeText(this.getActivity(), resultado.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(this.getActivity(), "Ocurrió un error al agregar el monto a reestablecer.", Toast.LENGTH_SHORT).show();
        }
    }


    public void confirmaReestablecer(){
        String strMontoReestablecer = txtSaldoReestablecido.getText().toString();
        if(strMontoReestablecer.length() == 0) strMontoReestablecer = "0.00";
        double monto = Double.parseDouble(strMontoReestablecer);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Se actualizará el saldo de su tarjeta a " + Globales.MONEDA + SMString.obtenerFormatoMonto(monto) + " ¿Desea continuar?")
                .setTitle("Corregir saldo")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reestablecerSaldo();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }


    public void finalizarActividad(){
        Intent intentMain = new Intent();
        intentMain.setClass(context, MainActivity.class);
        intentMain.setAction(Intent.ACTION_MAIN);
        startActivity(intentMain);
        this.getActivity().finish();
    }

    public void confirmaBorrarHistorial(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Se ha corregido su saldo con éxito. ¿Desea también borrar el historial de movimientos?")
                .setTitle("Eliminar historial")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MovimientoDao movimientoDao = new MovimientoDao(getActivity().getApplicationContext());
                        preferences = new SMPreferences(context);
                        int tarjetaSeleccionada = preferences.obtenerTarjetaSeleccionada();
                        movimientoDao.eliminarMovimientos(tarjetaSeleccionada+"");
                        dialog.cancel();
                        finalizarActividad();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finalizarActividad();
                    }
                });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReestablecer){
            if(validarMontoReestableer()){
                confirmaReestablecer();

            }
        }
    }
}
