package com.bitone.saldometro.controller;

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
import com.bitone.saldometro.utils.Validar;

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
        intent.putExtra("idEstacion",idEstacion);
        intent.putExtra("nombreEstacion", estacion);
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
            tvEstacion.setText(Validar.convertFromUTF8(Validar.convertToUTF8(datos[position].getNombreEstacion())));

            TextView tvDistrito = (TextView)item.findViewById(R.id.tvDistrito);
            tvDistrito.setText(Validar.convertFromUTF8(Validar.convertToUTF8(datos[position].getDistrito())));

            TextView tvUbicacion = (TextView)item.findViewById(R.id.tvUbicacion);
            tvUbicacion.setText(Validar.convertFromUTF8(Validar.convertToUTF8(datos[position].getUbicacion() + "")));


            ImageButton btn = (ImageButton)item.findViewById(R.id.btnHorario);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarHorario(datos[position].getIdEstacion(),datos[position].getNombreEstacion());
                }
            });
            return(item);
        }
    }

}
