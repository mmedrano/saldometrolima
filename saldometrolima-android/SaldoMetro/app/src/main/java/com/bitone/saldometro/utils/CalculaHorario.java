package com.bitone.saldometro.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Christian Medrano on 25/07/2015.
 */
public class CalculaHorario {

    /*Datos
    *
    * 26 estaciones
        -Salidas desde Villa el Salvador y Bayovar 6:00 am

        -tiempo entre estacion 2 min
        Excepciones:
            San Juan y Atocongo 3 min
            cabitos a Angamos 3 min
            La cultura a Arriola 3 min
            Prebistero a Caja de Agua 3 min

        tiempo entre salida de tren 6-10 min L-V, 10 min Sab, 14 min Dom
    * */
    public static final int FREC_DOMINGO = 14;
    public static final int FREC_SABADO = 10;
    public static final int FREC_L_V = 8;

    public static final int MINS_SERVICIO = 960; //horas de servicio 16 hrs
    public static final int HORA_MINUTOS = 60; // 1hr <> 60 min

    public static final int TOTALVIAJES_L_V = 120; // 120 viajes solo d�as L - V

    //Es lo �nico que se utilizar�
    private String[] salidasABayovar={"6:00","6:02","6:04","6:06","6:08","6:10","6:13","6:15","6:17",
            "6:19","6:22","6:24","6:26","6:29","6:31","6:33","6:35","6:37","6:40","6:42","6:44",
            "6:46","6:48","6:50","6:55","6:54",};//Se utilizar� la misma data a la inversa para la direcci�n contraria


    public String[] salidasAVillaSalvador(){
        String[] salidaAVilla= new String[salidasABayovar.length];
        for(int i=salidasABayovar.length-1, j=0; i>=0; i--,j++){
            salidaAVilla[j]=salidasABayovar[i];
        }
        return salidaAVilla;
    }


    int frecuenciaTrenes=0;
    public List<HorarioEstacion> calcularHorarios(int idEstacion, String ddMMyyyy){
        //Disponibilidad del tren al d�a 6:00 am a 10:00pm -> 16 hrs
        //Frecuencia entre trenes promedio 10 min
        //Cantidad de horarios al d�a por Estacion = MINS_SERVICIO /frecuencia entre trenes (min)

        frecuenciaTrenes=calculaFrecuencia(ddMMyyyy);
        int viajesAlDiaTotal=0;

        if(frecuenciaTrenes==FREC_L_V){
            viajesAlDiaTotal=TOTALVIAJES_L_V;
        }
        else{
            viajesAlDiaTotal=MINS_SERVICIO/frecuenciaTrenes;
        }


        //
        String primeraSalidaABayovar=salidasABayovar[idEstacion-1];
        String primeraSalidaAVilla=salidasAVillaSalvador()[idEstacion-1];

        HorarioEstacion primerHorarioEstacion= new HorarioEstacion();
        primerHorarioEstacion.setHoraABayovar(primeraSalidaABayovar);
        primerHorarioEstacion.setHoraAVillaSalvador(primeraSalidaAVilla);

        List<HorarioEstacion> horarioEstacionLst= new ArrayList<HorarioEstacion>();
        horarioEstacionLst.add(primerHorarioEstacion);

        StringTokenizer st = new StringTokenizer(primeraSalidaABayovar+":"+primeraSalidaAVilla,":");
        int horaBayovar=Integer.parseInt(st.nextToken());
        int minutoBayovar=Integer.parseInt(st.nextToken());
        int horaVilla=Integer.parseInt(st.nextToken());
        int minutoVilla=Integer.parseInt(st.nextToken());
        //

        for(int i=0; i<viajesAlDiaTotal; i++){
            String nuevaHoraABayovar="";
            String nuevaHoraAVillaSalvador="";
            
            if(viajesAlDiaTotal==TOTALVIAJES_L_V){frecuenciasDiaParticularABayovar(i);}// Calcula la frecuencia variable: Si es L-V

            if(minutoBayovar>=HORA_MINUTOS-frecuenciaTrenes){
                horaBayovar++;
                minutoBayovar-=(HORA_MINUTOS-frecuenciaTrenes);
                if(minutoBayovar<10){
                    nuevaHoraABayovar=horaBayovar+":0"+minutoBayovar;
                }
                else nuevaHoraABayovar=horaBayovar+":"+minutoBayovar;
            }
            else{
                minutoBayovar+=frecuenciaTrenes;
                if(minutoBayovar<10){
                    nuevaHoraABayovar=horaBayovar+":0"+minutoBayovar;
                }
                else nuevaHoraABayovar=horaBayovar+":"+minutoBayovar;
            }

            if(viajesAlDiaTotal==TOTALVIAJES_L_V){frecuenciasDiaParticularAVilla(i);}// Calcula la frecuencia variable: Si es L-V

            if(minutoVilla>=HORA_MINUTOS-frecuenciaTrenes){
                horaVilla++;
                minutoVilla-=(HORA_MINUTOS-frecuenciaTrenes);
                if(minutoVilla<10){
                    nuevaHoraAVillaSalvador=horaVilla+":0"+minutoVilla;
                }
                else nuevaHoraAVillaSalvador=horaVilla+":"+minutoVilla;
            }
            else{
                minutoVilla+=frecuenciaTrenes;
                if(minutoVilla<10){
                    nuevaHoraAVillaSalvador=horaVilla+":0"+minutoVilla;
                }
                else nuevaHoraAVillaSalvador=horaVilla+":"+minutoVilla;
            }

            HorarioEstacion nuevoHorarioEstacion= new HorarioEstacion();
            nuevoHorarioEstacion.setHoraABayovar(nuevaHoraABayovar);
            nuevoHorarioEstacion.setHoraAVillaSalvador(nuevaHoraAVillaSalvador);

            horarioEstacionLst.add(nuevoHorarioEstacion);
        }
        return horarioEstacionLst;
    }

    public void frecuenciasDiaParticularABayovar(int param){
        if(param<2){
            frecuenciaTrenes=10;
        }
        else if(param==2){
            frecuenciaTrenes=7;
        }
        else if(param>=3 && param<=33){
            frecuenciaTrenes=6;
        }
        else if(param>=34 && param<=81){
            frecuenciaTrenes=10;
        }
        else if(param>=82 && param<=109){
            frecuenciaTrenes=6;
        }
        else if(param>=110 && param<=118){
            frecuenciaTrenes=10;
        }
        else if(param==119){
            frecuenciaTrenes=9;
        }
    }

    public void frecuenciasDiaParticularAVilla(int param){
        if(param<3){
            frecuenciaTrenes=10;
        }
        else if(param>=3 && param<=32){
            frecuenciaTrenes=6;
        }
        else if(param>=33 && param<=80){
            frecuenciaTrenes=10;
        }
        else if(param>=81 && param<=110){
            frecuenciaTrenes=6;
        }
        else if(param>=111 && param<=119){
            frecuenciaTrenes=10;
        }
    }



    public String muestraDiaDeSemana(String ddMMyyyy){
        String nombreDia="";
        try{

            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inFormat.parse(ddMMyyyy);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            nombreDia = outFormat.format(date);
            return nombreDia;
        }
        catch(Exception e){return null;}
    }

    public int calculaFrecuencia(String ddMMyyyy){
        String diaNombre=muestraDiaDeSemana(ddMMyyyy);
        if(diaNombre!=null){
            diaNombre=diaNombre.toUpperCase();
            if((diaNombre.charAt(0)=='S')||diaNombre.equals("SATURDAY")){//Esto es solo porque no estoy seguro si me devolver� tildes->S�bado || Si est� en ingl�s
                return FREC_SABADO; // 10 min
            }
            else if(diaNombre.equals("DOMINGO")||diaNombre.equals("SUNDAY")){
                return FREC_DOMINGO; // 14 min
            }
            else return FREC_L_V;
        }
        else{
            return FREC_L_V;
        }
    }

    public String obtenerFechaActual(String formatoFecha){
        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
        String formattedDate = dateFormat.format(c.getTime());
        return formattedDate;
    }

    public class HorarioEstacion{
        private String horaABayovar;
        private String horaAVillaSalvador;

        public String getHoraABayovar() {return horaABayovar;}

        public void setHoraABayovar(String horaABayovar) {this.horaABayovar = horaABayovar;}

        public String getHoraAVillaSalvador() {return horaAVillaSalvador;}

        public void setHoraAVillaSalvador(String horaAVillaSalvador) {this.horaAVillaSalvador = horaAVillaSalvador;}
    }


}
