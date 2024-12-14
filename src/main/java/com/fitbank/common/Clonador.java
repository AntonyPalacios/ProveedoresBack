package com.fitbank.common;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Clase que clona objetos a profundidad. Clona objetos, no sus referencias.
 * 
 * @author FitBank 2.0
 * 
 * @param <T>
 *            Tipo de objeto a clonar
 *
 * @deprecated Usar la clase en com.fitbank.util en el proyecto Util
 */

@Slf4j
@Deprecated
public class Clonador<T> {

	/**
	 * Metodo para clonar objetos de cualquier Tipo.
	 * 
	 * @param t
	 *            Objeto a ser clonado
	 * 
	 * @return Objeto clonado
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clonar(T t) {
		T obj = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(t);
			out.flush();
			out.close();

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			obj = (T) in.readObject();
		} catch (IOException e) {
			log.info("as", e.getStackTrace().toString());
		} catch (ClassNotFoundException e) {
			log.info("as", e.getStackTrace().toString());
		}

		return obj;
	}
}