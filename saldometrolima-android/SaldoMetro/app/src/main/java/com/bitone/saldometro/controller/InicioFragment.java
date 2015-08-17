package com.bitone.saldometro.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitone.saldometro.model.business.TarifaBusiness;
import com.bitone.saldometro.model.business.TarjetaBusiness;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.Tarifa;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.Globales;
import com.bitone.saldometro.utils.SMPreferences;
import com.bitone.saldometro.utils.SMString;
import com.bitone.saldometro.utils.Validar;

public class InicioFragment extends Fragment implements View.OnClickListener{
    TextView tvTarjeta, tvSaldo, tViajes, tvTarifa;
    Button btnAgregarSaldo, btnMarcarViaje;
    Context context;

    double montoSaldo = 0.00, montoTarifa = 0.00;
    SMPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        context = getActivity().getApplicationContext();

        tvTarjeta = (TextView)rootView.findViewById(R.id.tvTarjeta);
        tvSaldo = (TextView)rootView.findViewById(R.id.tvSaldo);
        tViajes = (TextView)rootView.findViewById(R.id.tvViajesP);
        tvTarifa = (TextView)rootView.findViewById(R.id.tvTarifa);

        btnAgregarSaldo = (Button)rootView.findViewById(R.id.btnAgregarSaldo);
        btnMarcarViaje = (Button)rootView.findViewById(R.id.btnMarcarViaje);

        btnAgregarSaldo.setOnClickListener(this);
        btnMarcarViaje.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume(){
        CargarDatosTarjeta();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAgregarSaldo){
            irAAgregarSaldo();
        }else if(v.getId() == R.id.btnMarcarViaje){
            irAMarcarViaje();
        }
    }

    private void CargarDatosTarjeta(){
        SMPreferences preferences = new SMPreferences(context);
        int tarjetaSeleccionada = preferences.obtenerTarjetaSeleccionada();

        TarjetaBusiness tarjetaBusiness = new TarjetaBusiness(context);
        SMResultado resultado = tarjetaBusiness.consultarTarjeta(tarjetaSeleccionada);

        if(resultado.esCorrecto()){
            Tarjeta tarjetaActual = (Tarjeta)resultado.getEntidad();
            if(tarjetaActual != null){
                TarifaBusiness tarifaBusiness = new TarifaBusiness(context);

                montoSaldo = Validar.round(tarjetaActual.getSaldo(), 2);
                Tarifa tarifaActual = tarifaBusiness.obtenerTarifa(tarjetaActual);
                montoTarifa = tarifaActual.getMonto();

                CalculaHorario calculaHorario = new CalculaHorario(this.getActivity().getApplicationContext());
                String dia=calculaHorario.muestraDiaDeSemana(calculaHorario.obtenerFechaActual("dd-MM-yyyy"));
                String textoTarifa = "Tarifa: " + Globales.MONEDA + SMString.obtenerFormatoMonto(tarifaActual.getMonto());
                textoTarifa = textoTarifa + (tarifaActual.esFeriado() ? " (Feriado)" : "")+ (tarifaActual.esTarifaDoble() ? " ("+dia+")" : "");

                tvSaldo.setText(SMString.obtenerFormatoMonto(montoSaldo));
                tvTarjeta.setText(tarjetaActual.getTipo().getNombreTipo());
                tvTarifa.setText(textoTarifa);
                tViajes.setText(tarifaBusiness.obtenerFormatoViajes(montoSaldo, montoTarifa));
            }
        }else{
            Toast.makeText(context, resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
    }

    private void irAAgregarSaldo(){
        Intent intentAgregar = new Intent();
        intentAgregar.setClass(context, AgregarSaldoActivity.class);
        intentAgregar.putExtra(Globales.EXTRA_SALDO_ACTUAL, montoSaldo);
        startActivity(intentAgregar);
    }

    private void irAMarcarViaje(){
        Intent intentMarcar = new Intent();
        intentMarcar.setClass(context, MarcarViajeActivity.class);
        intentMarcar.putExtra(Globales.EXTRA_SALDO_ACTUAL, montoSaldo);
        intentMarcar.putExtra(Globales.EXTRA_TARIFA_ACTUAL, montoTarifa);
        startActivity(intentMarcar);
    }
}
