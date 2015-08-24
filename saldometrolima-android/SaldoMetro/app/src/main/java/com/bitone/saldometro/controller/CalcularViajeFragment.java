package com.bitone.saldometro.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
    private DatePickerDialog ViajeDatePickerDialog;
    private SimpleDateFormat formatoDia;


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

    CalculaHorario calculaHorario=null;

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

        formatoDia = new SimpleDateFormat("dd-MM-yyyy");


        origen = CalculaViaje.ORIGEN_DEFAULT;
        destino = CalculaViaje.DESTINO_DEFAULT;

        btnDia.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        btnDestino.setOnClickListener(this);
        btnOrigen.setOnClickListener(this);
        btnCalculaViaje.setOnClickListener(this);
        setDateTimeField();


        cargarDatos();
        return rootView;
    }





    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnHora){
            establecerHora();
        }
        else if(v.getId() == R.id.btnDia){
            //elegirDia();
            ViajeDatePickerDialog.show();
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
                Toast.makeText(activity,"Marque la hora en que realizará su viaje por favor "+(btnHora.getText().toString()) , Toast.LENGTH_SHORT).show();
            }
            else if(origen==destino){
                Toast.makeText(activity,"Seleccione un origen y destino válido por favor" , Toast.LENGTH_SHORT).show();
            }
            else{
                mostrarDialogoResultado();
            }

        }
    }


    public void establecerHora(){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        /*TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));*/
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,false);
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


    public void mostrarDialogoResultado(){
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

        //Instanciando objetos del dialog
        TextView tvHoraEspera = (TextView)dialoglayout.findViewById(R.id.tvHoraEspera);
        TextView tvHoraLlegTren = (TextView)dialoglayout.findViewById(R.id.tvHoraLlegTren);
        TextView tvTiempoEstimado = (TextView)dialoglayout.findViewById(R.id.tvTiempoEstimado);
        TextView tvHoraLlegada = (TextView)dialoglayout.findViewById(R.id.tvHoraLlegada);
        TextView tvNota = (TextView)dialoglayout.findViewById(R.id.tvNota);
        tvNota.setVisibility(View.GONE);

        //Calculando horarios
        calculaHorario= new CalculaHorario(activity.getApplicationContext());
        List<HorarioEstacion> calcularHorariosList=calculaHorario.calcularHorarios(origen+1,btnDia.getText().toString());

        horasABayovar = new String[calcularHorariosList.size()];
        horasAVilla = new String[calcularHorariosList.size()];

        for(int i=0; i<calcularHorariosList.size(); i++){
            horasABayovar[i]=calcularHorariosList.get(i).getHoraABayovar();
            horasAVilla[i]=calcularHorariosList.get(i).getHoraAVillaSalvador();
        }

        /*
        //Obteniendo hora actual
        Calendar c = Calendar.getInstance();*/

        //calculando datos para llenar en Popup
            //}Hora de Espera
        String HoraEspera=btnHora.getText().toString();
            //HoraLLegada Tren
        String varHoraLlegadaTren="00:00";
        String varUltimaHora="", varPrimeraHora="";
        if(destino<origen){
            varHoraLlegadaTren=obtieneSguiente(horasAVilla);
            varUltimaHora = horasAVilla[horasAVilla.length-1];
            varPrimeraHora = horasAVilla[0];

        }
        else if(destino>origen){
            varHoraLlegadaTren=obtieneSguiente(horasABayovar);
            varUltimaHora = horasABayovar[horasABayovar.length-1];
            varPrimeraHora = horasABayovar[0];
        }

            //Verifica si ya pasó la última salida
        if(varHoraLlegadaTren.equals(varPrimeraHora)){
            tvNota.setText("* Última hora de salida: "+varUltimaHora);
            tvNota.setVisibility(View.VISIBLE);
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
        StringTokenizer stHoraSeleccionada= new StringTokenizer(HoraEspera,": ");
        int horaEspera=Integer.parseInt(stHoraSeleccionada.nextToken());
        int minutoEspera=Integer.parseInt((stHoraSeleccionada.nextToken()));


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

        calculaHorario= new CalculaHorario(activity.getApplicationContext());
        String defaultHorarioViaje = calculaHorario.obtenerFechaActual("dd-MM-yyyy h:mm a");
        StringTokenizer stHora = new StringTokenizer(defaultHorarioViaje.substring(11,defaultHorarioViaje.length()),": ");
        int horaTemp = Integer.parseInt(stHora.nextToken());
        String minutoTemp = stHora.nextToken();//minuto
        String am_pm=stHora.nextToken();
        String vHora="";
        if((am_pm.toUpperCase().equals("PM") || am_pm.toUpperCase().equals("P.M.")) && horaTemp<12){horaTemp+=12; vHora=horaTemp+"";}
        else if((am_pm.toUpperCase().equals("AM") || am_pm.toUpperCase().equals("A.M.")) && horaTemp==12){vHora="00";}
        else {vHora = horaTemp+"";}
        btnDia.setText(defaultHorarioViaje.substring(0,10));
        btnHora.setText((vHora)+":"+minutoTemp+" "+am_pm);
    }


    private List<HorarioEstacion> validaObtenerHorario(String ultimaHoraDireccion){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:a");
        String formattedDate = dateFormat.format(c.getTime());

        //Obteniendo la hora actual
        StringTokenizer stActual= new StringTokenizer(formattedDate,":");
        int horaActual=Integer.parseInt(stActual.nextToken());
        int minutoActual=Integer.parseInt((stActual.nextToken()));
        String AMPM=stActual.nextToken().toUpperCase();
        //validando las 24 y 00 horas
        if(horaActual<=12){
            if((AMPM.equals("PM")&&horaActual!=12) || (AMPM.equals("P.M.")&&horaActual!=12)){horaActual+=12;}
            else if((AMPM.equals("AM")&&horaActual==12) || (AMPM.equals("A.M.")&&horaActual==12)){horaActual=0;}
        }

        //Última hora a la Direccion
        StringTokenizer stDireccionUltimo= new StringTokenizer(ultimaHoraDireccion,":");
        int horaultimaDireccion=Integer.parseInt(stDireccionUltimo.nextToken());
        int minutoultimoDireccion=Integer.parseInt((stDireccionUltimo.nextToken()));

        calculaHorario= new CalculaHorario(activity.getApplicationContext());
        List<HorarioEstacion> calcularHorariosList=calculaHorario.calcularHorarios(origen + 1, btnDia.getText().toString());

        if(((horaActual>horaultimaDireccion))||((horaActual==horaultimaDireccion)&&minutoActual>minutoultimoDireccion)){
            StringTokenizer stDay = new StringTokenizer(btnDia.getText().toString(),"-");
            int day=Integer.parseInt(stDay.nextToken()); int month=Integer.parseInt(stDay.nextToken());
            int year=Integer.parseInt(stDay.nextToken());
            c.set(year, month, day);
            c.add(Calendar.DAY_OF_MONTH, 1);

            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            String formatoDia = date.format(c.getTime());

            calcularHorariosList=calculaHorario.calcularHorarios(origen + 1, formatoDia);
        }
        return calcularHorariosList;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm = (hourOfDay < 12) ? "AM" : "PM";
        String vMinute = (minute+"").length()==1 ? "0"+minute : ""+minute;
        btnHora.setText(hourOfDay + ":" + vMinute+ " " + am_pm);
    }



    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        ViajeDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                btnDia.setText(formatoDia.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}






