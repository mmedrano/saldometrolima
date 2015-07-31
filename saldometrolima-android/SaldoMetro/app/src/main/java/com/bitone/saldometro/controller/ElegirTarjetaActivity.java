package com.bitone.saldometro.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bitone.saldometro.model.entity.TipoTarjeta;
import com.bitone.saldometro.utils.SMPreferences;


public class ElegirTarjetaActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_tarjeta);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
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
                if (checked) tarjetaSeleccionada = TipoTarjeta.ADULTO;
                break;
            case R.id.rbTmu:
                if (checked) tarjetaSeleccionada = TipoTarjeta.UNIVERSITARIO;
                break;
            case R.id.rbTme:
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
    private void irAInicio(){
        Intent intentMain = new Intent();
        intentMain.setClass(getApplicationContext(), MainActivity.class);
        intentMain.setAction(Intent.ACTION_MAIN);
        startActivity(intentMain);
        finish();
    }
}
