package com.bitone.saldometro.dao.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Christian Medrano on 13/07/2015.
 */
public class ReaderDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SaldoMetro.db";
    public static final int DATABASE_VERSION = 1;

    public ReaderDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear las tablas
        db.execSQL(EventoDataSource.CREATE_EVENTO_SCRIPT);
        db.execSQL(MovimientoSaldoDataSource.CREATE_MOVIMIENTOSALDO_SCRIPT);
        db.execSQL(ParametroDataSource.CREATE_PARAMETRO_SCRIPT);
        db.execSQL(TarjetaDataSource.CREATE_TARJETA_SCRIPT);
        db.execSQL(TipoMovimientoDataSource.CREATE_TIPOMOVIMIENTO_SCRIPT);
        db.execSQL(TipoTarjetaDataSource.CREATE_TIPOTARJETA_SCRIPT);
        db.execSQL(EstacionDataSource.CREATE_ESTACION_SCRIPT);
        //Insertar registros iniciales

        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (1, 'PASAJEADULTO', '1.50','Tarifa de la tarjeta adulto');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (2,'PASAJEMEDIO','0.75','Tarifa de la tarjeta de medio pasaje');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (3,'FERIADOS','01/01','Año Nuevo');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (4,'FERIADOS','01/05','Día del Trabajo');");
        //db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (5,'FERIADOS','24/06','Inti Raymi (Fiesta Inca del Sol)');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (6,'FERIADOS','29/06','Festividad de San Pedro y San Pablo');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (7,'FERIADOS','28/07','Fiesta de la Independecia Nacional');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (8,'FERIADOS','29/07','Fiesta de la Independecia Nacional(2)');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (9,'FERIADOS','30/08','Festividad de Santa Rosa de Lima');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (10,'FERIADOS','08/10','Celebracion de la Batalla de Angamos');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (11,'FERIADOS','01/11','Dia de Todos los Santos');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (12,'FERIADOS','08/12','Dia de la Inmaculada Concepcion');");
        db.execSQL("insert into " + ParametroDataSource.PARAMETRO_TABLE_NAME + " VALUES (13,'FERIADOS','25/12','Navidad');");

        db.execSQL("insert into " + TarjetaDataSource.TARJETA_TABLE_NAME + " VALUES (null, 1.50, 0.00, 1);");
        db.execSQL("insert into " + TarjetaDataSource.TARJETA_TABLE_NAME + " VALUES (null, 0.75, 0.00, 2);");
        db.execSQL("insert into " + TarjetaDataSource.TARJETA_TABLE_NAME + " VALUES (null, 0.75, 0.00,3);");

        db.execSQL("insert into " + TipoTarjetaDataSource.TIPOTARJETA_TABLE_NAME + " VALUES (null, 'Tarjeta Adulto');");
        db.execSQL("insert into " + TipoTarjetaDataSource.TIPOTARJETA_TABLE_NAME + " VALUES (null,'Tarjeta Universitario');");
        db.execSQL("insert into " + TipoTarjetaDataSource.TIPOTARJETA_TABLE_NAME + " VALUES (null,'Tarjeta Escolar');");

        db.execSQL("insert into " + TipoMovimientoDataSource.TIPOMOVIMIENTO_TABLE_NAME + " VALUES (null, 'Recarga');");
        db.execSQL("insert into " + TipoMovimientoDataSource.TIPOMOVIMIENTO_TABLE_NAME + " VALUES (null, 'Viaje');");
        db.execSQL("insert into " + TipoMovimientoDataSource.TIPOMOVIMIENTO_TABLE_NAME + " VALUES (null, 'Reestablecimiento de saldo');");
        db.execSQL("insert into " + TipoMovimientoDataSource.TIPOMOVIMIENTO_TABLE_NAME + " VALUES (null, 'Cambio de tarjeta');");

        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Villa El Salvador', 'Villa El Salvador', 'Av. Separadora Industrial / Av. Velasco Alvarado','-12.207408448324356,-76.93326104432344');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Parque Industrial', 'Villa El Salvador', 'Av. Separadora Industrial / Av. El Sol','-12.196514985462018,-76.93943314254284');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Pumacahua', 'Villa María del Triunfo', 'Av. La Unión / Pedro Ruiz Gallo / E. Aguirre','-12.181724759619065,-76.94696679711342');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Villa María', 'Villa María del Triunfo', 'Av. Pachacútec / Av. Santa Rosa','-12.169201367870857,-76.95039331912994');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'María Auxiliadora', 'Villa María del Triunfo', 'Av. Pachacútec / Av. Manco Cápac','-12.161171233593288,-76.95623885840178');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'San Juan', 'San Juan de Miraflores', 'Los Héroes / Av. César Canevaro','-12.15646172116063,-76.96578282862902');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Atocongo', 'San Juan de Miraflores', 'Av. Los Héroes / Av. Buckingham','-12.150894038425012,-76.97937492281199');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Jorge Chávez', 'Santiago de Surco', 'Av. Tomás Marsano / Av. Jorge Chávez','-12.142489360227882,-76.99103079736233');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Ayacucho', 'Santiago de Surco', 'Av. Tomás Marsano / Av. Ayacucho','-12.134755720638903,-76.9970040768385');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Cabitos', 'Santiago de Surco', 'Av. Aviación / Óvalo Los Cabitos (Higuereta)','-12.127397836271355,-77.00047921389341');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Angamos', 'San Borja', 'Av. Aviación / Av. Primavera','-12.110921103273679,-77.0002518966794');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'San Borja Sur', 'San Borja', 'Av. Aviación / Av. San Borja Sur','-12.10097471427203,-77.00168620795012');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'La Cultura', 'San Borja', 'Av. Aviación / Av. Javier Prado','-12.087186317364033,-77.00370959937572');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Arriola', 'La Victoria', 'Av. Aviación / Óvalo Arriola','-12.07532305238748,-77.01103270053864');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Gamarra', 'La Victoria', 'Av. Aviación / Jirón Hipólito Unanue','-12.065217962247793,-77.0123627409339');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Grau', 'Cercado de Lima', 'Av. Grau / Nicolás Ayllón','-12.054203927662579,-77.01279424130917');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'El Ángel', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio el Ángel)','-12.045918512965246,-77.00981196016073');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Presbítero Maestro', 'El Agustino', 'Av. Locumba Nro. S/N (Cementerio Presbítero Maestro)','-12.041781775849556,-77.01160468161106');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Caja de Agua', 'San Juan de Lurigancho', 'AV. Próceres de la Independencia / Óvalo Zárate','-12.027619789198809,-77.01112557202578');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Pirámide del Sol', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Pirámide del Sol','-12.018111371771997,-77.0027232170105');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Los Jardines', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Los Jardines','-12.00606206245877,-77.00593315064907');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Los Postes', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Los Postes','-11.997287827075041,-77.00966745615005');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'San Carlos', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. El Sol','-11.984826643168757,-77.00671266764402');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'San Martín', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Canto Rey','-11.97462730202997,-76.99986431747675');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Santa Rosa', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Santa Rosa','-11.968225049237555,-76.99355911463499');");
        db.execSQL("insert into " + EstacionDataSource.ESTACION_TABLE_NAME + " VALUES (null, 'Bayóvar', 'San Juan de Lurigancho', 'Av. Próceres de la Independencia / Av. Bayóvar','-11.959794638455444,-76.98751911520958');");

        //db.execSQL(TarjetaDataSource.INSERT_TARJETA_SCRIPT);
        //db.execSQL(TipoTarjetaDataSource.INSERT_TIPOTARJETA_SCRIPT);
        //db.execSQL(TipoMovimientoDataSource.INSERT_TIPOMOVIMIENTO_SCRIPT);
        //db.execSQL(EstacionDataSource.INSERT_ESTACION_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Añade los cambios que se realizarán en el esquema
    }
}
