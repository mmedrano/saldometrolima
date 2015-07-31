package com.bitone.saldometro.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitone.saldometro.model.business.MovimientoSaldoBusiness;
import com.bitone.saldometro.model.entity.MovimientoSaldo;
import com.bitone.saldometro.model.entity.SMResultado;
import com.bitone.saldometro.model.entity.Tarifa;
import com.bitone.saldometro.model.entity.Tarjeta;
import com.bitone.saldometro.model.entity.TipoMovimiento;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.Globales;
import com.bitone.saldometro.utils.SMPreferences;
import com.bitone.saldometro.utils.SMString;
import com.bitone.saldometro.utils.Validar;


public class AgregarSaldoActivity extends ActionBarActivity implements View.OnClickListener {
    Button btnAgregar;
    EditText txtCredito;
    TextView tvSaldoActual;

    double montoSaldo = 0.00;
    SMPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_saldo);

        txtCredito = (EditText) findViewById(R.id.txtCredito);
        tvSaldoActual = (TextView) findViewById(R.id.tvSaldoActual);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);

        preferences = new SMPreferences(this);

        cargarDatos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar_saldo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAgregar){
            agregarSaldo();
        }
    }

    private void cargarDatos(){
        montoSaldo = getIntent().getExtras().getDouble(Globales.EXTRA_SALDO_ACTUAL);
        montoSaldo = Validar.round(montoSaldo, 2);
        tvSaldoActual.setText(Globales.MONEDA + SMString.obtenerFormatoMonto(montoSaldo));
    }

    private void agregarSaldo(){
        try{
            String strMontoRecarga = txtCredito.getText().toString();
            SMResultado resultadoValidacion = Validar.validaDouble(strMontoRecarga, montoSaldo);
            if(resultadoValidacion.esCorrecto()){
                double montoRecarga = Validar.round(Double.parseDouble(strMontoRecarga), 2);

                MovimientoSaldo movimientoSaldo = new MovimientoSaldo();
                movimientoSaldo.setCantidadPersonas(0);
                CalculaHorario calculaHorario = new CalculaHorario();
                movimientoSaldo.setFechaMovimiento(calculaHorario.obtenerFechaActual("dd-MMM-yyyy h:mm a"));

                movimientoSaldo.setIdTarjeta(preferences.obtenerTarjetaSeleccionada());
                movimientoSaldo.setIdTipoMovimiento(MovimientoSaldo.MOV_RECARGA);
                movimientoSaldo.setMonto(montoRecarga);

                Tarjeta tarjetaActualizada= new Tarjeta();
                tarjetaActualizada.setId(preferences.obtenerTarjetaSeleccionada());
                tarjetaActualizada.setSaldo(Validar.round((montoSaldo + montoRecarga), 2));

                MovimientoSaldoBusiness movimientoSaldoBusiness = new MovimientoSaldoBusiness(getApplicationContext());
                SMResultado resultado = movimientoSaldoBusiness.recargarSaldo(movimientoSaldo, tarjetaActualizada);

                if(resultado.esCorrecto()){
                    finish();
                }else{
                    Toast.makeText(this, resultadoValidacion.getMensaje(), Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, resultadoValidacion.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Ocurrio un error al agregar el monto a recargar.", Toast.LENGTH_SHORT).show();
        }
    }
}
