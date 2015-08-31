package com.bitone.saldometro.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitone.saldometro.model.business.EstacionBusiness;
import com.bitone.saldometro.model.entity.Estacion;
import com.bitone.saldometro.utils.CalculaViaje;

import java.util.List;

public class InformacionEstacionActivity extends ActionBarActivity {

    ListView lstInfoEstacion=null;
    ListView LstCalculoViaje=null;
    Estacion[] datos=null;
    CalculaViaje.ViajeEstacion [] datosCalculoViaje=null;

    TextView tvDistrito, tvUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_estacion);

        tvDistrito = (TextView)findViewById(R.id.tvDistrito);
        tvUbicacion = (TextView)findViewById(R.id.tvUbicacion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cargarInformacion();
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


    public void cargarInformacion(){
        int idEstacion= this.getIntent().getExtras().getInt("idEstacion");
        String nombrEstacion = this.getIntent().getExtras().getString("nombreEstacion");
        this.setTitle(nombrEstacion);
        String distrito= this.getIntent().getExtras().getString("distrito");
        String ubicacion= this.getIntent().getExtras().getString("ubicacion");
        Estacion estacion = new Estacion(idEstacion,null,distrito, ubicacion,null);

        /*datos = new Estacion[1];
        datos[0] = estacion;

        AdaptadorLstInformacionEstacion adaptadorLstInformacionEstacion= new AdaptadorLstInformacionEstacion(this.getApplicationContext(),datos);
        lstInfoEstacion = (ListView) findViewById(R.id.LstInfoEstacion);
        lstInfoEstacion.setAdapter(adaptadorLstInformacionEstacion);*/
        tvDistrito.setText(estacion.getDistrito());
        tvUbicacion.setText(estacion.getUbicacion());
        //

        CalculaViaje calculaViaje = new CalculaViaje();
        List <CalculaViaje.ViajeEstacion> viajeEstacionList = calculaViaje.obtenerCalculoViajes(idEstacion);

        EstacionBusiness estacionBusiness= new EstacionBusiness();
        List<Estacion> estacionList=estacionBusiness.obtenerEstaciones(this.getApplicationContext(), this.MODE_PRIVATE);
        for(int i=0; i<estacionList.size(); i++){
            viajeEstacionList.get(i).setNombreEstacion("Hacia "+estacionList.get(i).getNombreEstacion());
        }

        datosCalculoViaje = new CalculaViaje.ViajeEstacion[viajeEstacionList.size()];
        for(int i=0; i<viajeEstacionList.size(); i++){
            datosCalculoViaje[i]= viajeEstacionList.get(i);
        }


        AdaptadorLstCalculaViajeEstacion  adaptadorLstCalculaViajeEstacion = new AdaptadorLstCalculaViajeEstacion(this.getApplicationContext(),datosCalculoViaje);
        LstCalculoViaje = (ListView) findViewById(R.id.LstCalculoViaje);
        LstCalculoViaje.setAdapter(adaptadorLstCalculaViajeEstacion);
    }

    private void cerrar(){
        finish();
    }

    class AdaptadorLstInformacionEstacion extends ArrayAdapter<Estacion> {

        public AdaptadorLstInformacionEstacion(Context context, Estacion[] datos) {
            super(context, R.layout.listitem_estacion_informacion, datos);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_estacion_informacion, null);

            TextView tvDistrito = (TextView)item.findViewById(R.id.tvDistrito);
            tvDistrito.setText(datos[position].getDistrito());

            TextView tvUbicacion = (TextView)item.findViewById(R.id.tvUbicacion);
            tvUbicacion.setText(datos[position].getUbicacion() + "");

            return(item);
        }
    }


    class AdaptadorLstCalculaViajeEstacion extends ArrayAdapter<CalculaViaje.ViajeEstacion> {

        public AdaptadorLstCalculaViajeEstacion(Context context, CalculaViaje.ViajeEstacion[] datosCalculoViaje) {
            super(context, R.layout.listitem_calculo_viaje, datosCalculoViaje);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_calculo_viaje, null);

            TextView tvEstacionViaje = (TextView)item.findViewById(R.id.tvEstacionViaje);
            tvEstacionViaje.setText(datosCalculoViaje[position].getNombreEstacion());

            TextView tvCalculoViaje = (TextView)item.findViewById(R.id.tvCalculoViaje);
            tvCalculoViaje.setText(datosCalculoViaje[position].getMinutosViaje() + "");

            return(item);
        }
    }
}
