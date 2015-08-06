package com.bitone.saldometro.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitone.saldometro.model.entity.HorarioEstacion;
import com.bitone.saldometro.utils.CalculaHorario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class HorarioActivity extends ActionBarActivity {
    public HorarioActivity() {
        // Required empty public constructor
    }

    //Instancia de objetos
    private ListView LstHorariosABayovar=null;
    private ListView LstHorariosAVilla=null;
    private TextView tvDireccionBayovar;
    private TextView tvDireccionVilla;

    //Variables Globales
    private String[] horasABayovar=null;
    private String[] horasAVilla=null;

    private boolean horaEncontradaBayovar=false;
    private String horaSeleccionadaBayovar="";

    private boolean horaEncontradaVilla=false;
    private String horaSeleccionadaVilla="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        this.setTitle(this.getIntent().getExtras().getString("nombreEstacion"));
        tvDireccionBayovar=(TextView)findViewById(R.id.tvDireccionBayovar);
        tvDireccionVilla=(TextView)findViewById(R.id.tvDireccionVilla);

        LstHorariosABayovar = (ListView)findViewById(R.id.LstHorarioABayovar);
        LstHorariosAVilla = (ListView)findViewById(R.id.LstHorarioAVilla);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cargarDatos();

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


    public void cargarDatos(){
        CalculaHorario calculaHorario= new CalculaHorario();
        int idEstacion=this.getIntent().getExtras().getInt("idEstacion");
        esTerminal(idEstacion);

        //Obtengo la fecha
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(c.getTime());

        List<HorarioEstacion> calcularHorariosList=calculaHorario.calcularHorarios(idEstacion,formattedDate);
        horasABayovar = new String[calcularHorariosList.size()];
        horasAVilla = new String[calcularHorariosList.size()];


        for(int i=0; i<calcularHorariosList.size(); i++){
            horasABayovar[i]=calcularHorariosList.get(i).getHoraABayovar();
            horasAVilla[i]=calcularHorariosList.get(i).getHoraAVillaSalvador();
        }



        AdaptadorListaHorarioABayovar adaptadorListaHorarioABayovar = new AdaptadorListaHorarioABayovar(this,horasABayovar);
        AdaptadorListaHorarioAVillaSalvador adaptadorListaHorarioAVillaSalvador = new AdaptadorListaHorarioAVillaSalvador(this,horasAVilla);

        LstHorariosABayovar.setAdapter(adaptadorListaHorarioABayovar);
        LstHorariosAVilla.setAdapter(adaptadorListaHorarioAVillaSalvador);

        seteaCursorLista(horasABayovar, LstHorariosABayovar);
        seteaCursorLista(horasAVilla, LstHorariosAVilla);
    }



    public void mostrarHoraSiguiente(TextView tvHora, String[]horaDireccion, int position, boolean horaEncontrada, ListView listaHorariosDireccion, String direccion){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:a");
        String formattedDate = dateFormat.format(c.getTime());

        //Obteniendo la hora actual
        StringTokenizer stActual= new StringTokenizer(formattedDate,":");
        int horaActual=Integer.parseInt(stActual.nextToken());
        int minutoActual=Integer.parseInt((stActual.nextToken()));
        String AMPM=stActual.nextToken();
        //validando las 24 y 00 horas
        if(AMPM.equals("PM")&&horaActual==12){horaActual=horaActual;}
        else if(AMPM.equals("PM")&&horaActual!=12){horaActual+=12;}
        else if(AMPM.equals("AM")&&horaActual==12){horaActual=0;}

        //horario a Direccion
        StringTokenizer stDireccion= new StringTokenizer(horaDireccion[position],":");
        int horaADireccion=Integer.parseInt(stDireccion.nextToken());
        int minutoADireccion=Integer.parseInt((stDireccion.nextToken()));

        //Última hora a la Direccion
        StringTokenizer stDireccionUltimo= new StringTokenizer(horaDireccion[horaDireccion.length-1],":");
        int horaultimaDireccion=Integer.parseInt(stDireccionUltimo.nextToken());
        int minutoultimoDireccion=Integer.parseInt((stDireccionUltimo.nextToken()));

        if(((horaActual>=horaultimaDireccion)&&minutoADireccion>=minutoultimoDireccion)||(horaActual<6)){
            listaHorariosDireccion.setSelection(0);

            if(position==0){textViewHoraFormato(tvHora);}
            /*if(!horaEncontrada){
                textViewHoraFormato(tvHora);
                if(direccion.equals("V")){
                    horaEncontradaVilla=true;
                }
                else {horaEncontradaBayovar=true;}
            }*/
        }
        else{
            if(((horaADireccion>=horaActual)&&minutoADireccion>=minutoActual)||(horaADireccion>horaActual)){
                if(!horaEncontrada){
                    textViewHoraFormato(tvHora);
                    if(direccion.equals("V")){
                        horaEncontradaVilla=true;
                        horaSeleccionadaVilla=horaDireccion[position];
                    }
                    else{
                        horaEncontradaBayovar=true;
                        horaSeleccionadaBayovar=horaDireccion[position];
                    }
                }
                else{
                    if(direccion.equals("V")){
                        if (tvHora.getText().toString().equals(horaSeleccionadaVilla)){
                            textViewHoraFormato(tvHora);
                        }
                    }
                    else{
                        if (tvHora.getText().toString().equals(horaSeleccionadaBayovar)){
                            textViewHoraFormato(tvHora);
                        }
                    }
                }
            }
        }


    }


    public void seteaCursorLista(String[]horaDireccion, ListView listaHorariosDireccion){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:a");
        String formattedDate = dateFormat.format(c.getTime());

        //Obteniendo la hora actual
        StringTokenizer stActual= new StringTokenizer(formattedDate,":");
        int horaActual=Integer.parseInt(stActual.nextToken());
        int minutoActual=Integer.parseInt((stActual.nextToken()));
        String AMPM=stActual.nextToken();
        //validando las 24 y 00 horas
        if(AMPM.equals("PM")&&horaActual==12){horaActual=horaActual;}
        else if(AMPM.equals("PM")&&horaActual!=12){horaActual+=12;}
        else if(AMPM.equals("AM")&&horaActual==12){horaActual=0;}

        //Última hora a la Direccion
        StringTokenizer stDireccionUltimo= new StringTokenizer(horaDireccion[horaDireccion.length-1],":");
        int horaultimaDireccion=Integer.parseInt(stDireccionUltimo.nextToken());
        int minutoultimoDireccion=Integer.parseInt((stDireccionUltimo.nextToken()));

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

        listaHorariosDireccion.setSelection(varPosition);

    }


    public void esTerminal(int idEstacion){
        if(idEstacion==1){
            tvDireccionVilla.setText("Llegada a V. E. S.");
        }
        else if(idEstacion==26){
            tvDireccionBayovar.setText("Llegada a Bayóvar");
        }
    }

    public void textViewHoraFormato(TextView textView){
        textView.setTextColor(Color.parseColor("#006400"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    }

    private void cerrar(){
        finish();
    }


    class AdaptadorListaHorarioABayovar extends ArrayAdapter<String> {

        public AdaptadorListaHorarioABayovar(Context context, String[] horasABayovar) {
            super(context, R.layout.listitem_horarios, horasABayovar);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_horarios, null);

            TextView tvHora = (TextView)item.findViewById(R.id.tvHora);
            tvHora.setText(horasABayovar[position].toString());

            mostrarHoraSiguiente(tvHora, horasABayovar, position, horaEncontradaBayovar, LstHorariosABayovar, "B");
            return(item);
        }
    }


    class AdaptadorListaHorarioAVillaSalvador extends ArrayAdapter<String> {

        public AdaptadorListaHorarioAVillaSalvador(Context context, String[] horasAVilla) {
            super(context, R.layout.listitem_horarios, horasAVilla);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_horarios, null);

            TextView tvHora = (TextView)item.findViewById(R.id.tvHora);
            tvHora.setText(horasAVilla[position].toString());

            mostrarHoraSiguiente(tvHora,horasAVilla, position, horaEncontradaVilla, LstHorariosAVilla, "V");
            return(item);
        }
    }





}
