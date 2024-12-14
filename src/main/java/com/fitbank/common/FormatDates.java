package com.fitbank.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que entrega los distintos formatos de fechas.
 * 
 * @author Fitbank
 * @version 2.0
 */
public final class FormatDates {
    /**
     * Intancia Singleton
     */
    private static FormatDates instance = null;

    /**
     * Formatea una fecha para ser empleada como campo de particionamiento en
     * una Tabla
     * 
     * @param pDate
     *            Fecha de Referencia.
     * @return Fecha con el formato requerido
     * @throws Exception
     */
    public static String formatFPartition(Date pDate) throws Exception {
        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");
        return s.format(pDate);
    }

    /**
     * Obtiene el AÃ±o de una fecha
     * 
     * @param pDate
     *            Fecha de Referencia.
     * @return Fecha con el formato requerido
     * @throws Exception
     */
    public static String formatYear(Date pDate) throws Exception {
        SimpleDateFormat s = new SimpleDateFormat("yyyy");
        return s.format(pDate);
    }
    
    public static String formatDateToyyyyMMdd() throws Exception {
    	return FormatDates.getInstance().getFormatyyyyMMdd().format(new Date());
    }
    
    public static String formatDateToHHmmss() throws Exception {
    	return FormatDates.getInstance().getFormatHHmmss().format(new Date());
    }
    
    public static String formatDateToHHsmmsssa() throws Exception {
    	return FormatDates.getInstance().getFormatHHsmmsssa().format(new Date());
    }

    /**
     * Devuelve la fecha hasta default de 2999-12-31
     * 
     * @return Fecha hasta 2999-12-31
     * @throws Exception
     */
    public static java.sql.Date getDefaultExpiryDate() throws Exception {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date a = s.parse("2999-12-31");
        return new java.sql.Date(a.getTime());
    }

    /**
     * Devuelve la fecha hasta default de 2999-12-31
     * 
     * @return Fecha hasta 2999-12-31
     * @throws Exception
     */
    public static Timestamp getDefaultExpiryTimestamp() throws Exception {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date a = s.parse("2999-12-31");
        return new Timestamp(a.getTime());
    }

    /**
     * Obtiene el singleton
     * 
     * @return Referencia al singleton
     */
    public static FormatDates getInstance() {
        synchronized (FormatDates.class) {

            if (instance == null) {
                instance = new FormatDates();
            }
        }
        return instance;
    }

    /**
     * Crea una Instancia de FormatDates
     */
    private FormatDates() {
    }

    /**
     * Obtiene el formateador de un conteo de tiempo en String
     * 
     * @return Formateador.
     * @throws Exception
     */
    public SimpleDateFormat getTimeCountFormat() {
        return new SimpleDateFormat("mm:ss.SSS");
    }

    /**
     * Obtiene el formateador de Horas para su transaporte en String
     * 
     * @return Formateador
     * @throws Exception
     */
    public SimpleDateFormat getTimeFormat() throws Exception {
        return new SimpleDateFormat("HH:mm:ss");
    }

    /**
     * Obtiene el Formateador para el Transporte de Fechas
     * 
     * @return Formateador para el transporte de Fechas
     * @throws Exception
     *             Error en la Creaciï¿½n del Formateador
     */
    public SimpleDateFormat getTransportDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * Obtiene el Formateador para el Transporte de Timestamp
     * 
     * @throws Exception
     *             Error en la creaciï¿½n del Formateador
     * @return Formateador para el Transporte de Timestamp
     */
    public SimpleDateFormat getTransportDatetimeFormat() throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    /**
     * Obtiene el formateador de Timestamps para su transaporte en String
     * 
     * @return Formtateador
     * @throws Exception
     */
    public SimpleDateFormat getTransportTimestampFormat() throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Obtiene el formateador de un aï¿½o.
     * 
     * @return Formateador.
     * @throws Exception
     */
    public SimpleDateFormat getYearFormat() throws Exception {
        return new SimpleDateFormat("yyyy");
    }
    
    public SimpleDateFormat getFormatyyyyMMdd() throws Exception {
        return new SimpleDateFormat("yyyyMMdd");
    }
    
    public SimpleDateFormat getFormatHHmmss() throws Exception {
        return new SimpleDateFormat("HHmmss");
    }
    
    public SimpleDateFormat getFormatHHsmmsssa() throws Exception {
        return new SimpleDateFormat("HH:mm:ss a");
    }
    
    public static void main(String args[]) {
    	try {
			String format = FormatDates.formatDateToHHsmmsssa();
			System.out.println(format);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}