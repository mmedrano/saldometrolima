package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class EstacionDataSource {

    //Metainformaci�n de la base de datos
    public static final String ESTACION_TABLE_NAME = "Estacion";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String DEC_TYPE = "double";

    //Campos de la tabla Estacion
    public static class ColumnQuotes{
        public static final String ID_ESTACION = "idEstacion";//BaseColumns._ID;
        public static final String NOMBRE_ESTACION= "nombre";
        public static final String DISTRITO_ESTACION= "distrito";
        public static final String UBICACION_ESTACION= "ubicacion";
    }

    //Script de Creaci�n de Estacion
    public static final String CREATE_ESTACION_SCRIPT =
            "create table "+ESTACION_TABLE_NAME+"(" +
                    ColumnQuotes.ID_ESTACION+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.NOMBRE_ESTACION+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.DISTRITO_ESTACION+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.UBICACION_ESTACION+" "+STRING_TYPE+" not null)";

    //Scripts de inserci�n por defecto
    public static final String INSERT_ESTACION_SCRIPT =
            "insert into "+ESTACION_TABLE_NAME+" values" +
                    "(null, 'Villa El Salvador', 'Villa El Salvador', 'Av. Separadora Industrial / Av. Velasco Alvarado')," +
                    "(null, 'Parque Industrial', 'Villa El Salvador', 'Av. Separadora Industrial / Av. El Sol')," +
                    "(null, 'Pumacahua', 'Villa Mar�a del Triunfo', 'Av. La Uni�n / Pedro Ruiz Gallo / E. Aguirre')," +
                    "(null, 'Villa Mar�a', 'Villa Mar�a del Triunfo', 'Av. Pachac�tec / Av. Santa Rosa')," +
                    "(null, 'Mar�a Auxiliadora', 'Villa Mar�a del Triunfo', 'Av. Pachac�tec / Av. Manco C�pac')," +
                    "(null, 'San Juan', 'San Juan de Miraflores', 'Los H�roes / Av. C�sar Canevaro')," +
                    "(null, 'Atocongo', 'San Juan de Miraflores', 'Av. Los H�roes / Av. Buckingham')," +
                    "(null, 'Jorge Ch�vez', 'Santiago de Surco', 'Av. Tom�s Marsano / Av. Jorge Ch�vez')," +
                    "(null, 'Ayacucho', 'Santiago de Surco', 'Av. Tom�s Marsano / Av. Ayacucho')," +
                    "(null, 'Cabitos', 'Santiago de Surco', 'Av. Aviaci�n / �valo Los Cabitos (Higuereta)')," +
                    "(null, 'Angamos', 'San Borja', 'Av. Aviaci�n / Av. Primavera')," +
                    "(null, 'San Borja Sur', 'San Borja', 'Av. Aviaci�n / Av. San Borja Sur')," +
                    "(null, 'La Cultura', 'San Borja', 'Av. Aviaci�n / Av. Javier Prado')," +
                    "(null, 'Arriola', 'La Victoria', 'Av. Aviaci�n / �valo Arriola')," +
                    "(null, 'Gamarra', 'La Victoria', 'Av. Aviaci�n / Jir�n Hip�lito Unanue')," +
                    "(null, 'Grau', 'Cercado de Lima', 'Av. Grau / Nicol�s Ayll�n')," +
                    "(null, 'El �ngel', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio el �ngel)')," +
                    "(null, 'Presb�tero Maestro', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio Presb�tero Maestro)')," +
                    "(null, 'Caja de Agua', 'San Juan de Lurigancho', 'AV. Pr�ceres de la Independencia / �valo Z�rate')," +
                    "(null, 'Pir�mide del Sol', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Pir�mide del Sol')," +
                    "(null, 'Los Jardines', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Los Jardines')," +
                    "(null, 'Los Postes', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Los Postes')," +
                    "(null, 'San Carlos', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. El Sol')," +
                    "(null, 'San Mart�n', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Canto Rey')," +
                    "(null, 'Santa Rosa', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Santa Rosa')," +
                    "(null, 'Bay�var', 'San Juan de Lurigancho', 'Av. Pr�ceres de la Independencia / Av. Bay�var')";


    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public EstacionDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
