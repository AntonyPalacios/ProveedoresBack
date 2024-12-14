package com.banco.comercio.proveedores.loan;

import com.banco.comercio.proveedores.util.MetodosUtiles;
import com.fitbank.common.Detail;
import com.fitbank.uci.client.UCIClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * 
 * @author C&A Pro Consulting 
 *
 */

@Component
@SuppressWarnings("ALL")
@Slf4j
public class BusinessDelegate {

	@Autowired
	private UCIClient uciClient;

	private static Logger log = Logger.getLogger("BusinessDelegate-");

//	public Detail process(Detail pDetail)  {
//		return uciClient.send(pDetail);
//	}

	public Detail process(Detail detail) throws Exception {
		detail.findFieldByNameCreate("SERVICIO").setValue("proveedores");
		val xmlRequest = detail.toXml().toString();
		log.info("detail después prepareHeader: " + xmlRequest);
		val xmlResponse = uciClient.sendSerializable2(xmlRequest).toString();
		log.info("detail después process: " + xmlResponse);
		MetodosUtiles.verificarGRS(xmlResponse);
		Detail detailResponse = Detail.valueOf(xmlResponse);

		return detailResponse;
	}

}
