package com.banco.comercio.proveedores.util;


import com.banco.comercio.proveedores.config.FitbankProperties;
import com.fitbank.common.Uid;
import com.fitbank.uci.client.FitbankException;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class MetodosUtiles {

	private static Logger log = LoggerFactory.getLogger(MetodosUtiles.class);

	public static String getCharacterDataFromElement(Element e) {
		String value = "";

		try {
			if (e != null) {
				Node child = e.getFirstChild();
				if (child instanceof CharacterData) {
					CharacterData cd = (CharacterData) child;
					value = cd.getData();
				}
			} else {
				value = "";
			}

		} catch (RuntimeException ex) {
			log.error(ex.getMessage(), ex);
		}

		return value;
	}

	/* Método que corrije el tipo de moneda recibido PEN a S/. */
	public static String corregirTipoMonedaPENaSOL(String moneda) {
		return Constants.MONEDA_PEN.equals(moneda) ? Constants.MONEDA_SOL : moneda;
	}

	public static String getMessageId() {
		return Uid.getString();
	}

	public static void verificarGRS(String xmlResponse/* , String codigoGRS, String mensajeGRS */)
			throws FitbankException, ParserConfigurationException, SAXException, IOException {
		Document doc = MetodosUtiles.getXMLResponseDocument(xmlResponse);
		NodeList nodes = doc.getElementsByTagName(Constants.GRS);

		if (nodes == null || nodes.getLength() == 0) {
			throw new FitbankException(Constants.ERROR_GRS_EMPTY, Constants.ERROR_INTERNO_FITBANK_MESSAGE,
					"No existe GRS en el response");

		} else {
			Element grs = (Element) nodes.item(0);
			Element line = (Element) grs.getElementsByTagName(Constants.MSGU).item(0);

			if (grs.hasAttribute(Constants.COD)) {
				String codigoGRS = grs.getAttribute(Constants.COD);
				String mensajeGRS = MetodosUtiles.getCharacterDataFromElement(line).trim();
				log.debug("codigoGRS: {}, mensajeGRS: {}", codigoGRS, mensajeGRS);

				if (!"0".equals(codigoGRS)) {
					// Cualquier código de error diferente de 0 en GRS se reporta como error
					if ("<*>".equals(codigoGRS)) {
						throw new FitbankException(Constants.ERROR_FITBANK_INTERNO_GENERAL_CODE,
								"ERROR INTERNO GENERAL EN FITBANK", mensajeGRS);
					} else {
						throw new FitbankException(codigoGRS, Constants.ERROR_INTERNO_FITBANK_MESSAGE, mensajeGRS);
					}
				} else {
					log.debug("GRS Ok");
				}
			} else {
				throw new FitbankException(Constants.ERROR_GRS_CODE_EMPTY, Constants.ERROR_INTERNO_FITBANK_MESSAGE,
						"No existe el atributo \'cod\' en el GRS del response");
			}
		}
	}

	/* Extrae de la trama xmlResponse el objeto Document */
	public static Document getXMLResponseDocument(String xmlResponse)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		DocumentBuilder db = factory.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlResponse));
		Document doc = db.parse(is);
		return doc;
	}

	public static ParametrizedDataMessageId getXMLRequestTemplate(Object request, String templateName,
																  Configuration freemarkerConfig, FitbankProperties fitbankProperties, String messageId,
																  HashMap<String, Object> parametros) throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		String xmlRequest = "";
		messageId = (!"".equals(messageId) && messageId != null) ? messageId : getMessageId();
		log.info("Template: {}", templateName);
		log.info(Constants.SERVICIO_1_REQUEST, request);
		Template t = freemarkerConfig.getTemplate(templateName);

		HashMap<String, Object> model = new HashMap<>();
		model.put(Constants.FITBANK, fitbankProperties);
		model.put(Constants.MSG, messageId);
		model.put(Constants.REQUEST, request);

		// Se asignan los Parámetros
		parametros.keySet().stream().forEach((paramName) -> {
			model.put(paramName, parametros.get(paramName));
		});

		xmlRequest = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		return new ParametrizedDataMessageId(xmlRequest, messageId);
	}

	public static ParametrizedDataMessageId getXMLRequestTemplate(Object request, String templateName,
			Configuration freemarkerConfig, FitbankProperties fitbankProperties, String messageId)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		String xmlRequest = "";
		messageId = (!"".equals(messageId) && messageId != null) ? messageId : getMessageId();
		log.info("Template: {}", templateName);
		log.info(Constants.SERVICIO_1_REQUEST, request);
		Template t = freemarkerConfig.getTemplate(templateName);
		HashMap<String, Object> model = new HashMap<>();
		model.put(Constants.FITBANK, fitbankProperties);
		model.put(Constants.MSG, messageId);
		model.put(Constants.REQUEST, request);
		xmlRequest = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		return new ParametrizedDataMessageId(xmlRequest, messageId);
	}
}
