package com.fitbank.uci.client;

import com.banco.comercio.proveedores.config.UCIConfig;
import com.fitbank.common.properties.PropertiesHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;


/**
 * Clase que implementa
 * 
 * @author Fit-Bank
 */
@Component
public class UCIClient {

	@Autowired
	UCIConfig uciConfig;

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Campo utilizado para
	 * 
	 * @since empty
	 */
	private static PropertiesHandler param = null;

	/**
	 * Campo utilizado para
	 * 
	 * @since empty
	 */

	private static void prepareSingletons() throws Exception {
		synchronized (UCIClient.class) {
			if (param == null) {
				param = new PropertiesHandler("uciclient");
			}
		}
	}

	public Object sendSerializable3(Object pDetail) throws Exception {
		prepareSingletons();
		Object response = pDetail;

		try {
			String uci3Host = uciConfig.getUci3Host().trim();
			String uci3Port = uciConfig.getUci3Port().trim();
			String uci3Timeout = uciConfig.getUci3Timeout().trim();

			logger.info("uci3.host: " + uci3Host);
			logger.info("uci3.port: " + uci3Port);
			logger.info("uci3.timeout: " + uci3Timeout);

			response = sendRequestFitbank(pDetail,uci3Host,uci3Port,uci3Timeout);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Exception e1 = new Exception("ERROR En la conexion del UCI Client 3: ", e);
			throw e1;

		}

		return response;
	}

	public Object sendSerializable2(Object pDetail) throws Exception {
		prepareSingletons();
		Object response = pDetail;

		try {
			String uci2Host = uciConfig.getUci2Host().trim();
			String uci2Port = uciConfig.getUci2port().trim();
			String uci2Timeout = uciConfig.getUci2Timeout().trim();

			logger.info("uci2.host: " + uci2Host);
			logger.info("uci2.port: " + uci2Port);
			logger.info("uci2.timeout: " + uci2Timeout);

			response = sendRequestFitbank(pDetail,uci2Host,uci2Port,uci2Timeout);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Exception e1 = new Exception("ERROR En la conexion del UCI Client 2: ", e);
			throw e1;
		} 

		return response;
	}

	
	public Object sendRequestFitbank(Object pDetail,String uciHost, String port, String timeOut ) 
			throws NumberFormatException , IOException , ClassNotFoundException {
		
		Object response = pDetail;
		Socket client = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			client = new Socket(uciHost, Integer.parseInt(port));
			client.setSoLinger(true, 0);
			client.setSoTimeout(Integer.parseInt(timeOut) * 1000);
			in = client.getInputStream();
			out = client.getOutputStream();

			ObjectOutputStream oout = new ObjectOutputStream(out);
			long time = System.currentTimeMillis();
			oout.writeObject(pDetail);
			oout.flush();

			ObjectInputStream oin = new ObjectInputStream(in);

			response = oin.readObject();
			time = System.currentTimeMillis() - time;
		} finally {
			if(client!=null) {
				client.close();
			}
		}
		
		return response;
	}
}
