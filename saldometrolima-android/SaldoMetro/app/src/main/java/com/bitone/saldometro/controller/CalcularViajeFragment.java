package com.bitone.saldometro.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class CalcularViajeFragment extends DialogFragment implements View.OnClickListener,TimePickerDialog.OnTimeSetListener{



    public CalcularViajeFragment() {
        // Required empty public constructor
    }

    Button btnHora;
    TextView tvHoraViaje;
    Spinner spinnerEstacionOri;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_calcular_viaje, container, false);
        btnHora = (Button)rootView.findViewById(R.id.btnHora);
        tvHoraViaje = (TextView)rootView.findViewById(R.id.tvHoraViaje);
        spinnerEstacionOri = (Spinner)rootView.findViewById(R.id.spinnerEstacionOri);

        btnHora.setOnClickListener(this);

        cargarDatos();
        return rootView;
    }





    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnHora){
            establecerHora();
        }
    }

    public void cargarDatos(){
        final String[] datos =
                new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_spinner_item, datos);
        spinnerEstacionOri.setAdapter(adaptador);

    }

    public void establecerHora(){
        //DialogFragment newFragment = new TimePickerFragment();
        //newFragment.show(this.getActivity().getSupportFragmentManager(), "timePicker");
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tvHoraViaje.setText(hourOfDay + ":" + minute);
    }



    //No uso
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            /*TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));*/
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
            return timePickerDialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

        }
    }


}
