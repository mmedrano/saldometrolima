package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class EstacionDataSource {

    //Metainformación de la base de datos
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

    //Script de Creación de Estacion
    public static final String CREATE_ESTACION_SCRIPT =
            "create table "+ESTACION_TABLE_NAME+"(" +
                    ColumnQuotes.ID_ESTACION+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnQuotes.NOMBRE_ESTACION+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.DISTRITO_ESTACION+" "+STRING_TYPE+" not null," +
                    ColumnQuotes.UBICACION_ESTACION+" "+STRING_TYPE+" not null)";

    //Scripts de inserción por defecto
    public static final String INSERT_ESTACION_SCRIPT =
            "insert into "+ESTACION_TABLE_NAME+" values" +
                    "(null, 'Villa El Salvador', 'Villa El Salvador', 'Av. Separadora Industrial / Av. Velasco Alvarado')," +
                    "(null, 'Parque Industrial', 'Villa El Salvador', 'Av. Separadora Industrial / Av. El Sol')," +
                    "(null, 'Pumacahua', 'Villa María del Triunfo', 'Av. La Unión / Pedro Ruiz Gallo / E. Aguirre')," +
                    "(null, 'Villa María', 'Villa María del Triunfo', 'Av. Pachacútec / Av. Santa Rosa')," +
                    "(null, 'María Auxiliadora', 'Villa María del Triunfo', 'Av. Pachacútec / Av. Manco Cápac')," +
                    "(null, 'San Juan', 'San Juan de Miraflores', 'Los Héroes / Av. César Canevaro')," +
                    "(null, 'Atocongo', 'San Juan de Miraflores', 'Av. Los Héroes / Av. Buckingham')," +
                    "(null, 'Jorge Chávez', 'Santiago de Surco', 'Av. Tomás Marsano / Av. Jorge Chávez')," +
                    "(null, 'Ayacucho', 'Santiago de Surco', 'Av. Tomás Marsano / Av. Ayacucho')," +
                    "(null, 'Cabitos', 'Santiago de Surco', 'Av. Aviación / Óvalo Los Cabitos (Higuereta)')," +
                    "(null, 'Angamos', 'San Borja', 'Av. Aviación / Av. Primavera')," +
                    "(null, 'San Borja Sur', 'San Borja', 'Av. Aviación / Av. San Borja Sur')," +
                    "(null, 'La Cultura', 'San Borja', 'Av. Aviación / Av. Javier Prado')," +
                    "(null, 'Arriola', 'La Victoria', 'Av. Aviación / Óvalo Arriola')," +
                    "(null, 'Gamarra', 'La Victoria', 'Av. Aviación / Jirón Hipólito Unanue')," +
                    "(null, 'Grau', 'Cercado de Lima', 'Av. Grau / Nicolás Ayllón')," +
                    "(null, 'El Ángel', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio el Ángel)')," +
                    "(null, 'Presbítero Maestro', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio Presbítero Maestro)')," +
                    "(null, 'Caja de Agua', 'San Juan de Lurigancho', 'AV. Próceres de la Independencia / Óvalo Zárate')," +
                    "(null, 'Pirámide del Sol', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Pirámide del Sol')," +
                    "(null, 'Los Jardines', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Los Jardines')," +
                    "(null, 'Los Postes', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Los Postes')," +
                    "(null, 'San Carlos', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. El Sol')," +
                    "(null, 'San Martín', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Canto Rey')," +
                    "(null, 'Santa Rosa', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Santa Rosa')," +
                    "(null, 'Bayóvar', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Bayóvar')";


    private ReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public EstacionDataSource(Context context) {
        //Creando una instancia hacia la base de datos
        openHelper = new ReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }
}
