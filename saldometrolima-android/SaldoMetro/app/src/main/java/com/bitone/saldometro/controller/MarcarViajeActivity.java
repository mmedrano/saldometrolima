package com.bitone.saldometro.controller;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitone.saldometro.model.business.MovimientoSaldoBusiness;
import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.model.entity.Tarifa;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.model.entity.TipoMovimiento;
import com.bitone.saldometro.model.entity.TipoTarjeta;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.Globales;
import com.bitone.saldometro.utils.SMPreferences;
import com.bitone.saldometro.utils.SMString;
import com.bitone.saldometro.utils.Validar;


public class MarcarViajeActivity extends ActionBarActivity implements  View.OnClickListener{
    int numPersonas;
    double saldoActual, tarifa, totalViaje, saldoRestante;
    SMPreferences preferences;

    TextView tvSaldoActual, tvTotalViaje, tvSaldoRestante;
    EditText txtPersonas;
    Button btnMarcar, btnMasPersonas, btnMenosPersonas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_viaje);
        this.setTitle("Marcar Viaje");

        tvSaldoActual=(TextView)findViewById(R.id.tvSaldoA);
        tvTotalViaje=(TextView)findViewById(R.id.tvTotalViaje);
        tvSaldoRestante=(TextView)findViewById(R.id.tvSaldoRestante);

        txtPersonas = (EditText)findViewById(R.id.txtPersonas);

        btnMarcar = (Button)findViewById(R.id.btnMarcar);
        btnMasPersonas = (Button)findViewById(R.id.btnMasPersonas);
        btnMenosPersonas = (Button)findViewById(R.id.btnMenosPersonas);

        btnMarcar.setOnClickListener(this);
        btnMasPersonas.setOnClickListener(this);
        btnMenosPersonas.setOnClickListener(this);

        preferences = new SMPreferences(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cargarDatos();
        validarMumPersonas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_marcar_viaje, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                cerrar();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==findViewById(R.id.btnMarcar).getId()){
            if (saldoRestante < 0){
                Toast.makeText(this, "Es necesario realizar una recarga para efectuar esta operación", Toast.LENGTH_SHORT).show();
            }else{
                confirmaMarcar(tvTotalViaje.getText().toString());
            }
        }
        else if(v.getId()==findViewById(R.id.btnMasPersonas).getId()){
            if(numPersonas != Tarifa.MAXIMO_PERSONAS_VIAJE){
                numPersonas++;
                calcularViaje();
            }
        }
        else if(v.getId()==findViewById(R.id.btnMenosPersonas).getId()){
            if(numPersonas!=1){
                numPersonas--;
                calcularViaje();
            }
        }

    }

    private void cargarDatos(){
        saldoActual = getIntent().getExtras().getDouble(Globales.EXTRA_SALDO_ACTUAL);
        saldoActual = Validar.round(saldoActual, 2);
        tvSaldoActual.setText(Globales.MONEDA + SMString.obtenerFormatoMonto(saldoActual));

        tarifa = this.getIntent().getExtras().getDouble(Globales.EXTRA_TARIFA_ACTUAL);
        tarifa = Validar.round(tarifa, 2);

        numPersonas = Integer.parseInt(txtPersonas.getText().toString());

        totalViaje = Validar.round((tarifa * numPersonas), 2);
        tvTotalViaje.setText(Globales.MONEDA + SMString.obtenerFormatoMonto(totalViaje));

        saldoRestante = Validar.round((saldoActual - tarifa), 2);
        tvSaldoRestante.setText(Globales.MONEDA + SMString.obtenerFormatoMonto(saldoRestante));
    }

    public void validarMumPersonas(){
        int tipoTarjeta=preferences.obtenerTarjetaSeleccionada();
        if(tipoTarjeta!= TipoTarjeta.ADULTO){
            btnMasPersonas.setVisibility(View.GONE);
            btnMenosPersonas.setVisibility(View.GONE);
        }
        else{
            btnMasPersonas.setVisibility(View.VISIBLE);
            btnMenosPersonas.setVisibility(View.VISIBLE);
        }
    }

    public void calcularViaje(){
        txtPersonas.setText(numPersonas+"");

        totalViaje = Validar.round((tarifa * numPersonas), 2);
        saldoRestante = Validar.round((saldoActual - totalViaje),2);
        tvTotalViaje.setText(Globales.MONEDA + SMString.obtenerFormatoMonto(totalViaje));
        tvSaldoRestante.setText(Globales.MONEDA+ SMString.obtenerFormatoMonto(saldoRestante));
    }

    public void marcarViaje(){
        //Movimiento Saldo
        MovimientoSaldo movimientoSaldo= new MovimientoSaldo();
        movimientoSaldo.setCantidadPersonas(numPersonas);

        CalculaHorario calculaHorario = new CalculaHorario();
        movimientoSaldo.setFechaMovimiento(calculaHorario.obtenerFechaActual("dd-MMM-yyyy hh:mm a"));

        movimientoSaldo.setIdTarjeta(preferences.obtenerTarjetaSeleccionada());
        movimientoSaldo.setIdTipoMovimiento(MovimientoSaldo.MOV_MARCAR_VIAJE);
        movimientoSaldo.setMonto(totalViaje);

        //Tarjeta a actualizar
        Tarjeta tarjetaActualizada= new Tarjeta();
        tarjetaActualizada.setId(preferences.obtenerTarjetaSeleccionada());
        tarjetaActualizada.setSaldo(Validar.round((saldoActual - totalViaje), 2));

        MovimientoSaldoBusiness movimientoSaldoBusiness= new MovimientoSaldoBusiness(this.getApplicationContext());
        String resultado=movimientoSaldoBusiness.marcarViaje(movimientoSaldo, tarjetaActualizada).getMensaje();
        Toast.makeText(this,resultado , Toast.LENGTH_SHORT).show();
        finish();
    }


    public void confirmaMarcar(String monto){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setMessage("Se descontará " + monto + " de su tarjeta. ¿Desea continuar?")
                .setTitle("Marcar viaje")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        marcarViaje();
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

    private void cerrar(){
        finish();
    }
}
