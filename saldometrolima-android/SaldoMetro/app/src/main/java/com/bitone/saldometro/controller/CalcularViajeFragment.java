package com.bitone.saldometro.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitone.saldometro.model.business.EstacionBusiness;
import com.bitone.saldometro.model.entity.Estacion;
import com.bitone.saldometro.model.entity.HistoriaTarjeta;
import com.bitone.saldometro.model.entity.HorarioEstacion;
import com.bitone.saldometro.model.entity.Viaje;
import com.bitone.saldometro.utils.CalculaHorario;
import com.bitone.saldometro.utils.CalculaViaje;
import com.bitone.saldometro.utils.Validar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;


public class CalcularViajeFragment extends DialogFragment implements View.OnClickListener,TimePickerDialog.OnTimeSetListener{



    public CalcularViajeFragment() {
        // Required empty public constructor
    }

    Button  btnDia, btnHora, btnDestino, btnOrigen, btnCalculaViaje;
    Context activity;
    int origen, destino;
    String dia="";
    String [] estaciones=null;
    Viaje[] datosViaje;


    View rootView;
    View rootViewAlert;

    //Variables Globales
    private String[] horasABayovar=null;
    private String[] horasAVilla=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = this.getActivity();
        rootView = inflater.inflate(R.layout.fragment_calcular_viaje, container, false);
        btnHora = (Button)rootView.findViewById(R.id.btnHora);
        btnDia = (Button)rootView.findViewById(R.id.btnDia);
        btnOrigen = (Button)rootView.findViewById(R.id.btnOrigen);
        btnDestino = (Button)rootView.findViewById(R.id.btnDestino);
        btnCalculaViaje = (Button)rootView.findViewById(R.id.btnCalculaViaje);



        origen = CalculaViaje.ORIGEN_DEFAULT;
        destino = CalculaViaje.DESTINO_DEFAULT;

        btnDia.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        btnDestino.setOnClickListener(this);
        btnOrigen.setOnClickListener(this);
        btnCalculaViaje.setOnClickListener(this);



        cargarDatos();
        return rootView;
    }





    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnHora){
            establecerHora();
        }
        else if(v.getId() == R.id.btnDia){
            elegirDia();
        }
        else if(v.getId() == R.id.btnOrigen){
            elegirEstacion("Origen");
        }
        else if(v.getId() == R.id.btnDestino){
            elegirEstacion("Destino");
        }
        else if(v.getId() == R.id.btnCalculaViaje){
            Validar validar = new Validar();
            if(btnDia.getText().toString().length()==3){
                Toast.makeText(activity,"Seleccione el día que realizará su viaje por favor" , Toast.LENGTH_SHORT).show();
            }
            else if(!validar.isNumeric((btnHora.getText().toString().charAt(0))+"")){
                Toast.makeText(activity,"Marque la hora en que realizará su viaje por favor" , Toast.LENGTH_SHORT).show();
            }
            else if(origen==destino){
                Toast.makeText(activity,"Seleccione un origen y destino válido por favor" , Toast.LENGTH_SHORT).show();
            }
            else{
                muestraCalculo();
            }

        }
    }


    public void establecerHora(){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    public void elegirEstacion(final String punto){
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Estación "+punto);

        b.setItems(estaciones, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                //Toast.makeText(activity, estaciones[which]+" "+which, Toast.LENGTH_SHORT).show();
                if(punto.equals("Origen")) {btnOrigen.setText(estaciones[which]); origen=which;} else {btnDestino.setText(estaciones[which]); destino=which;}
            }
        });

        b.show();
    }

    public void elegirDia(){
        final String dias []= {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
        AlertDialog.Builder builderDia = new AlertDialog.Builder(this.getActivity());
        builderDia.setTitle("Día");

        builderDia.setItems(dias, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                btnDia.setText(dias[which]);
                dia=dias[which].toUpperCase();
            }
        });

        builderDia.show();
    }


    public void muestraCalculo(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getActivity());
        builder.setMessage("")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        final FrameLayout frameView = new FrameLayout(activity.getApplicationContext());
        builder.setView(frameView);

        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_calculoviaje_layout, frameView);

        TextView tvHoraEspera = (TextView)dialoglayout.findViewById(R.id.tvHoraEspera);
        TextView tvHoraLlegTren = (TextView)dialoglayout.findViewById(R.id.tvHoraLlegTren);
        TextView tvTiempoEstimado = (TextView)dialoglayout.findViewById(R.id.tvTiempoEstimado);
        TextView tvHoraLlegada = (TextView)dialoglayout.findViewById(R.id.tvHoraLlegada);

        //Calculando horarios
        CalculaHorario calculaHorario= new CalculaHorario();
        /*Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(c.getTime());//obtengo la fecha*/

        List<HorarioEstacion> calcularHorariosList=calculaHorario.calcularHorarios(origen+1,dia);

        horasABayovar = new String[calcularHorariosList.size()];
        horasAVilla = new String[calcularHorariosList.size()];


        for(int i=0; i<calcularHorariosList.size(); i++){
            horasABayovar[i]=calcularHorariosList.get(i).getHoraABayovar();
            horasAVilla[i]=calcularHorariosList.get(i).getHoraAVillaSalvador();
        }

        //calculando datos para llenar en Popup
            //}Hora de Espera
        String HoraEspera=btnHora.getText().toString();
            //HoraLLegada Tren
        String varHoraLlegadaTren="00:00";
        if(destino<origen){
            varHoraLlegadaTren=obtieneSguiente(horasAVilla);
        }
        else if(destino>origen){
            varHoraLlegadaTren=obtieneSguiente(horasABayovar);
        }
            //Tiempo estimado
        String varTiempoEstimado="0 mins";
        CalculaViaje calculaViaje = new CalculaViaje();
        List <CalculaViaje.ViajeEstacion> viajeEstacionList = calculaViaje.obtenerCalculoViajes(origen+1);
        varTiempoEstimado = viajeEstacionList.get(destino).getMinutosViaje();



            //Hora llegada
        int varHoraLlegada=0;
        int varMinutoLlegada=0;
        int minutosEsperandoTren=0;
                //Obteniendo la hora de espera
        StringTokenizer stActual= new StringTokenizer(HoraEspera,": ");
        int horaEspera=Integer.parseInt(stActual.nextToken());
        int minutoEspera=Integer.parseInt((stActual.nextToken()));


                //Obteniendo la hora de llegada del tre
        StringTokenizer stHrLlegadaTren = new StringTokenizer(varHoraLlegadaTren,":");
        int horaLlegadaTren=Integer.parseInt(stHrLlegadaTren.nextToken());
        int minutoLlegadaTren=Integer.parseInt((stHrLlegadaTren.nextToken()));

        if(horaEspera==horaLlegadaTren){
            minutosEsperandoTren = minutoLlegadaTren - minutoEspera;
        }
        else{
            minutosEsperandoTren = (calculaHorario.HORA_MINUTOS - minutoEspera) + minutoLlegadaTren;
        }
        StringTokenizer stTiempoEstimado = new StringTokenizer(varTiempoEstimado," ");
        int tiempoEstimado=Integer.parseInt(stTiempoEstimado.nextToken());
        int minutosTotalViaje=minutosEsperandoTren + tiempoEstimado;

        if(minutoLlegadaTren+tiempoEstimado>=calculaHorario.HORA_MINUTOS){
            varHoraLlegada = horaLlegadaTren+1;
            varMinutoLlegada = (minutoLlegadaTren+tiempoEstimado)-calculaHorario.HORA_MINUTOS;
        }
        else{
            varHoraLlegada = horaLlegadaTren;
            varMinutoLlegada = minutoLlegadaTren+tiempoEstimado;
        }


        tvHoraEspera.setText(horaEspera+":"+((minutoEspera+"").length()==1 ? "0"+minutoEspera : ""+minutoEspera));
        tvHoraLlegTren.setText(varHoraLlegadaTren);
        tvTiempoEstimado.setText(varTiempoEstimado);
        tvHoraLlegada.setText(varHoraLlegada+":"+((varMinutoLlegada+"").length()==1? "0"+varMinutoLlegada : varMinutoLlegada));



        alertDialog.show();
    }


    public String obtieneSguiente(String[]horaDireccion){
        //Obteniendo la hora actual
        StringTokenizer stActual= new StringTokenizer(btnHora.getText().toString(),": ");
        int horaActual=Integer.parseInt(stActual.nextToken());
        int minutoActual=Integer.parseInt((stActual.nextToken()));

        //horario a Direccion
        int varPosition=0;
        for(int i=0; i<horaDireccion.length; i++){
            //comparando horario a direccion uno x uno
            StringTokenizer stDireccion= new StringTokenizer(horaDireccion[i],":");
            int horaADireccion=Integer.parseInt(stDireccion.nextToken());
            int minutoADireccion=Integer.parseInt((stDireccion.nextToken()));
            if(((horaADireccion>=horaActual)&&minutoADireccion>=minutoActual)||(horaADireccion>horaActual)){
                varPosition=i;
                break;
            }
        }

        return horaDireccion[varPosition];
    }

    public void cargarDatos(){
        //Carga de Estaciones
        EstacionBusiness estacionBusiness= new EstacionBusiness();
        List<Estacion> estacionList=estacionBusiness.obtenerEstaciones(this.getActivity().getApplicationContext(), this.getActivity().MODE_PRIVATE);
        estaciones= new String[estacionList.size()];
        for(int i=0; i<estacionList.size(); i++){
            estaciones[i]=estacionList.get(i).getNombreEstacion();
        }
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm = (hourOfDay < 12) ? "AM" : "PM";
        String vMinute = (minute+"").length()==1 ? "0"+minute : ""+minute;
        btnHora.setText(hourOfDay + ":" + vMinute+ " " + am_pm);
    }





    //Si se utilizara un ListView
    class AdaptadorViaje extends ArrayAdapter<Viaje> {

        public AdaptadorViaje(Context context, Viaje[] datosViaje) {
            super(context, R.layout.listitem_calculo_viaje, datosViaje);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_calculo_viaje, null);

            TextView tvEstacionViaje = (TextView)item.findViewById(R.id.tvEstacionViaje);
            tvEstacionViaje.setText(datosViaje[position].getDescripcion());

            TextView tvCalculoViaje = (TextView)item.findViewById(R.id.tvCalculoViaje);
            tvCalculoViaje.setText(datosViaje[position].getHora());


            return(item);
        }
    }
}






