package com.bitone.saldometro.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bitone.saldometro.model.business.EstacionBusiness;
import com.bitone.saldometro.model.entity.Estacion;

import java.util.List;

public class EstacionFragment extends Fragment {
    public EstacionFragment() {
        // Required empty public constructor
    }

    ListView lstEstaciones=null;
    Estacion[] datos=null;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getActivity().setTitle("Estaciones y Horarios");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_estacion, container, false);

        cargarDatos();
        return rootView;

    }



    public void mostrarHorario(int idEstacion, String estacion) {
        Intent intent = new Intent(this.getActivity(), HorarioActivity.class);
        intent.putExtra("idEstacion", idEstacion);
        intent.putExtra("nombreEstacion", estacion);
        startActivity(intent);
    }

    public void mostrarInformacion(int idEstacion, String estacion, String distrito, String ubicacion) {
        Intent intent = new Intent(this.getActivity(), InformacionEstacionActivity.class);
        intent.putExtra("idEstacion",idEstacion);
        intent.putExtra("nombreEstacion", estacion);
        intent.putExtra("distrito",distrito);
        intent.putExtra("ubicacion", ubicacion);
        startActivity(intent);
    }

    public void mostrarMapa(int idEstacion, String estacion, String coordenada) {
        Intent intent = new Intent(this.getActivity(), EstacionMapsActivity.class);
        intent.putExtra("idEstacion",idEstacion);
        intent.putExtra("nombreEstacion", estacion);
        intent.putExtra("coordenada",coordenada);
        startActivity(intent);
    }

    public void cargarDatos(){
        EstacionBusiness estacionBusiness= new EstacionBusiness();
        List<Estacion> estacionList=estacionBusiness.obtenerEstaciones(this.getActivity().getApplicationContext(),this.getActivity().MODE_PRIVATE);

        datos= new Estacion[estacionList.size()];
        for(int i=0; i<estacionList.size(); i++){
            datos[i]=estacionList.get(i);
        }

        AdaptadorListaEstacion adaptadorListaEstacion = new AdaptadorListaEstacion(this.getActivity().getApplicationContext(),datos);
        lstEstaciones = (ListView)rootView.findViewById(R.id.LstEstaciones);
        lstEstaciones.setAdapter(adaptadorListaEstacion);
    }

    class AdaptadorListaEstacion extends ArrayAdapter<Estacion> {

        public AdaptadorListaEstacion(Context context, Estacion[] datos) {
            super(context, R.layout.listitem_estacion, datos);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_estacion, null);

            TextView tvEstacion = (TextView)item.findViewById(R.id.tvEstacion);
            tvEstacion.setText(datos[position].getNombreEstacion());

            ImageButton btnInformacion = (ImageButton)item.findViewById(R.id.btnInformacion);
            btnInformacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarInformacion(datos[position].getIdEstacion(), datos[position].getNombreEstacion(), datos[position].getDistrito(), datos[position].getUbicacion());
                }
            });

            ImageButton btn = (ImageButton)item.findViewById(R.id.btnHorario);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarHorario(datos[position].getIdEstacion(), datos[position].getNombreEstacion());
                }
            });

            ImageButton btnMapa = (ImageButton)item.findViewById(R.id.btnMapa);
            btnMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarMapa(datos[position].getIdEstacion(), datos[position].getNombreEstacion(), datos[position].getCoordenada());
                }
            });
            return(item);
        }
    }

}
