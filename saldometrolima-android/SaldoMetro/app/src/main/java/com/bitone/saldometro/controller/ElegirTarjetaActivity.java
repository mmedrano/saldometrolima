package com.bitone.saldometro.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bitone.saldometro.model.entity.TipoTarjeta;
import com.bitone.saldometro.utils.SMPreferences;

import java.nio.charset.Charset;


public class ElegirTarjetaActivity extends ActionBarActivity implements View.OnClickListener {

    RadioButton rbTa, rbTmu, rbTme; RadioGroup rg;
    SMPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_tarjeta);
        preferences = new SMPreferences(this.getApplicationContext());

        rbTa = (RadioButton) findViewById(R.id.rbTa);
        rbTmu = (RadioButton) findViewById(R.id.rbTmu);
        rbTme = (RadioButton) findViewById(R.id.rbTme);
        focusTarjetaPreferencia(preferences.obtenerTarjetaSeleccionada());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if(v.getId() == R.id.rbTa || v.getId() == R.id.rbTme || v.getId() == R.id.rbTmu){
            return;
        }
    }

    public void onRadioButtonClicked(View v){
        int tarjetaSeleccionada = TipoTarjeta.DEFAULT;
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.rbTa:
                clearRb(); rbTa.setChecked(true);
                if (checked) tarjetaSeleccionada = TipoTarjeta.ADULTO;
                break;
            case R.id.rbTmu:
                clearRb(); rbTmu.setChecked(true);
                if (checked) tarjetaSeleccionada = TipoTarjeta.UNIVERSITARIO;
                break;
            case R.id.rbTme:
                clearRb(); rbTme.setChecked(true);
                if (checked) tarjetaSeleccionada = TipoTarjeta.ESCOLAR;
                break;
            default:
                tarjetaSeleccionada = TipoTarjeta.DEFAULT;
                break;
        }

        if(tarjetaSeleccionada == TipoTarjeta.DEFAULT){
            Toast.makeText(this.getApplicationContext(), "Seleccione una tarjeta.", Toast.LENGTH_SHORT).show();
        }else{
            SMPreferences preferences = new SMPreferences(getApplicationContext());
            preferences.registrarTarjetaSeleccionada(new TipoTarjeta(tarjetaSeleccionada));
            irAInicio();
        }
    }

    //METODOS
    private void focusTarjetaPreferencia(int idTipoTarjeta){
        switch(idTipoTarjeta){
            case 1:
                rbTa.setChecked(true);
                break;
            case 2:
                rbTmu.setChecked(true);
                break;
            case 3:
                rbTme.setChecked(true);
                break;
        }
    }
    private void clearRb(){
        rbTa.setChecked(false);
        rbTmu.setChecked(false);
        rbTme.setChecked(false);
    }

    private void irAInicio(){
        Intent intentMain = new Intent();
        intentMain.setClass(getApplicationContext(), MainActivity.class);
        intentMain.setAction(Intent.ACTION_MAIN);
        startActivity(intentMain);
        finish();
    }

    private void cerrar(){
        Intent intentMain = new Intent();
        intentMain.setClass(getApplicationContext(), MainActivity.class);
        intentMain.setAction(Intent.ACTION_MAIN);
        startActivity(intentMain);
        finish();
    }
}
