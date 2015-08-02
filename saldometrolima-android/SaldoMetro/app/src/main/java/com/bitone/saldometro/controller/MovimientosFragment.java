package com.bitone.saldometro.controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitone.saldometro.model.business.TarjetaBusiness;
import com.bitone.saldometro.model.entity.HistoriaTarjeta;
import com.bitone.saldometro.utils.SMPreferences;

import java.util.List;

public class MovimientosFragment extends Fragment {
   public MovimientosFragment() {
        // Required empty public constructor
    }

    ListView lstHistorial=null;
    HistoriaTarjeta[] datos=null;

    SMPreferences preferences;

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.getActivity().setTitle("Historial de Movimientos");
        rootView = inflater.inflate(R.layout.fragment_movimientos, container, false);

        lstHistorial = (ListView)rootView.findViewById(R.id.LstHistorialTarjeta);

        preferences = new SMPreferences(this.getActivity());

        consultarHistorial();
        return rootView;
    }


    public void consultarHistorial(){

        int idTarjetaPreference=preferences.obtenerTarjetaSeleccionada();
        TarjetaBusiness tb= new TarjetaBusiness(this.getActivity().getApplicationContext());
        List<HistoriaTarjeta> historiaTarjetaList=tb.obtenerHistorial(this.getActivity().getApplicationContext(),this.getActivity().MODE_PRIVATE,idTarjetaPreference);

        if(historiaTarjetaList!=null){
            datos= new HistoriaTarjeta[historiaTarjetaList.size()];
            for(int i=0; i<historiaTarjetaList.size(); i++){
                datos[i]=historiaTarjetaList.get(i);
            }
            AdaptadorHistorial adaptadorHistorial = new AdaptadorHistorial(this.getActivity().getApplicationContext(),datos);
            lstHistorial.setAdapter(adaptadorHistorial);
        }
        else{
            Toast.makeText(this.getActivity(), "No hay movimientos de tarjeta para mostrar", Toast.LENGTH_SHORT).show();
        }
    }


    class AdaptadorHistorial extends ArrayAdapter<HistoriaTarjeta> {

        public AdaptadorHistorial(Context context, HistoriaTarjeta[] datos) {
            super(context, R.layout.listitem_historial, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_historial, null);

            TextView tvFechaMovimiento = (TextView)item.findViewById(R.id.tvFechaMovimiento);
            tvFechaMovimiento.setText(datos[position].getFechaMovimiento());

            TextView tvTipoMovimiento = (TextView)item.findViewById(R.id.tvTipoMovimiento);
            tvTipoMovimiento.setText(datos[position].getTipoMovimiento());

            TextView tvMontoMovimiento = (TextView)item.findViewById(R.id.tvMontoMovimiento);
            tvMontoMovimiento.setText(datos[position].getMonto()+"");
            Log.e("ASDASDASDASD", datos[position].getTipoMovimiento().toString());
            if(datos[position].getTipoMovimiento().toString().equals("Recarga")){
                tvMontoMovimiento.setTextColor(Color.parseColor("#006400"));
            }else{tvMontoMovimiento.setTextColor(Color.parseColor("#FF0000"));}
            return(item);
        }
    }
}
