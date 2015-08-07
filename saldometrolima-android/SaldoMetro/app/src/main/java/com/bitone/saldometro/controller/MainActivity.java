package com.bitone.saldometro.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitone.saldometro.utils.SMPreferences;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SMPreferences preferences = new SMPreferences(getApplicationContext());
        if(preferences.existeTarjetaSeleccionada()){
            setContentView(R.layout.activity_main);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }else{
            irASeleccionTarjeta();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frgOpcion = null;
        boolean elegirTarjeta = false;

        switch (position){
            case 0:
                mTitle = getString(R.string.titulo_opcion_inicio);
                frgOpcion = new InicioFragment();
                break;
            case 1:
                mTitle = getString(R.string.titulo_opcion_elegir_tarjeta);
                elegirTarjeta = true;
                break;
            case 2:
                mTitle = getString(R.string.titulo_opcion_horario);
                frgOpcion = new EstacionFragment();
                break;
            case 3:
                mTitle = getString(R.string.titulo_opcion_viajes);
                frgOpcion = new CalcularViajeFragment();
                break;
            case 4:
                mTitle = getString(R.string.titulo_opcion_movimientos);
                frgOpcion = new MovimientosFragment();
                break;
            case 5:
                mTitle = getString(R.string.titulo_opcion_reestablecer);
                frgOpcion = new ReestablecerSaldoFragment();
                break;
            case 6:
                mTitle = getString(R.string.titulo_opcion_ayuda);
                frgOpcion = new SobreSaldoMetroFragment();
                break;
            default:
                mTitle = getString(R.string.titulo_opcion_inicio);
                frgOpcion = new InicioFragment();
                break;
        }

        if(frgOpcion != null){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, frgOpcion)
                    .commit();
        }

        if(elegirTarjeta){
            irASeleccionTarjeta();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    //METODOS

    private void irASeleccionTarjeta(){
        Intent intentMain = new Intent();
        intentMain.setClass(getApplicationContext(), ElegirTarjetaActivity.class);
        intentMain.setAction(Intent.ACTION_MAIN);
        startActivity(intentMain);
        finish();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}
