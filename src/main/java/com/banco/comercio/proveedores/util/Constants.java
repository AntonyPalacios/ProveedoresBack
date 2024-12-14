package com.banco.comercio.proveedores.util;

public class Constants {

	public static final String CAM = "CAM";
	public static final String CUI = "CUI";
	public static final String VAL = "VAL";
	public static final String REG = "REG";
	public static final String GRS = "GRS";
	public static final String TBL = "TBL";
	public static final String MSG = "MSG";
	public static final String ALIAS = "alias";
	public static final String NOMBRES = "NOMBRES";
	public static final String DIGITOVERIFICADOR = "DIGITOVERIFICADOR";
	public static final String APELLIDOPATERNO = "APELLIDOPATERNO";
	public static final String APELLIDOMATERNO = "APELLIDOMATERNO";
	public static final String DIRECCIONDOM = "DIRECCIONDOM";
	public static final String TIPODOCUMENTO = "TIPODOCUMENTO";
	public static final String NUMERODOCUMENTO = "NUMERODOCUMENTO";
	public static final String SEXO = "SEXO";
	public static final String FECHANAC = "FECHANAC";
	public static final String DEPARTAMENTO_DOM = "DEPARTAMENTODOM";
	public static final String PROVINCIA_DOM = "PROVINCIADOM";
	public static final String DISTRITO_DOM = "DISTRITODOM";
	public static final String DEPARTAMENTO_NAC = "DEPARTAMENTONAC";
	public static final String FECHAEMISION = "FECHAEMISION";
	public static final String PROVINCIA_NAC = "PROVINCIANAC";
	public static final String DISTRITO_NAC = "DISTRITONAC";
	public static final String GRADOINSTRUCCION = "GRADOINSTRUCCION";
	public static final String NOMBRE_PADRE = "NOMBREPADRE";
	public static final String NOMBRE_MADRE = "NOMBREMADRE";
	public static final String ESTADO_CIVIL = "ESTADOCIVIL";
	public static final String MSGU = "MSGU";
	public static final String COD = "cod";
	public static final String NAME = "name";
	public static final String COD_ = "COD_";
	public static final String MESSAGE_SENT = "Message sent";
	public static final String INVALID_MOBILE_NUMBER = "Invalid mobile number";
	public static final String INVALID_MOBILE_NUMBER_CODE = "2015";
	public static final String GUION = "-";
	
	public static final String CONSTANCIA_VOTACION = "CONSTANCIA_VOTACION";
	/*public static final String SMS_MESSAGE = "<#> Tu código de Creditos Digitales es {0}."
			+ " No lo compartas - Cpc2dK20823"*/;
	
	public static final String SMS_MESSAGE = "Banco de Comercio\nCuenta Digital\n" + "Clave Dinámica {0}.";
	
	public static final String SMS_NOTIFICATION_MESSAGE = "Cuenta Digital\n" + "{0}.\n{1}\n";
	public static final String MESSAGE = "message";

	public static final String COTIZACION_COMPRA = "cotizacionCompra";
	public static final String COTIZACION_VENTA = "cotizacionVenta";
	public static final String CODIGO_ERROR = "codigoError";
	public static final String NUMERO_OPERACION = "numeroOperacion";
	public static final String MONTO_CONVERTIDO = "montoConvertido";
	public static final String NUMERO_SOLICITUD = "numeroSolicitud";
	public static final String TOTALCOMISION = "TOTALCOMISION";
	public static final String MONTO_TOTAL = "MONTO_TOTAL";
	public static final String COMISION = "COMISION";

	public static final String EN_RETIRO = "R";
	public static final String EN_ACTIVIDAD = "A";
	public static final String PNP = "PNP";
	public static final String COD_RETIRO = "4";
	public static final String COD_ACTIVIDAD = "3";
	public static final String COD_PNP = "142";
	public static final String COD_RESTO_INSTI = "68";

	public static final String FITBANK = "fitbank";
	public static final String XML_REQUEST = "xmlRequest: {}";
	public static final String XML_RESPONSE = "xmlResponse: {}";
	public static final String REQUEST = "request";

	public static final String RESPUESTA_INFOBIP_SMS = "Respuesta InfoBIP SMS:";
	public static final String RESPUESTA_BACCAMBIO_TIPOCAMBIO = "Respuesta Baccambio TC:";
	public static final String MONEDA_SOL = "S/.";
	public static final String MONEDA_PEN = "PEN";
	public static final String MONEDA_USD = "USD";
	public static final String SERVICIO_1_REQUEST = "Servicio1Request: {}";
	public static final String CPERSONA = "CPERSONA";
	
	public static final String OK_ENUM_CODE = "COD_0";
	
	public static final String SERVICE_ERROR_CODE = "2022";
	public static final String OK_CODE = "2000";
	public static final String ERROR_AUTH_USER_DISABLED = "2001";
	public static final String ERROR_AUTH_BAD_CREDENTIALS = "2002";
	
	public static final String OK = "OK";
	public static final String NO_OK = "NoOK";
	public static final String ERROR_INTERNO_FITBANK_MESSAGE = "Error interno de Fitbank";
	
	/************ Código de Errores originados en Fitbank (0 - 8999) **************/
	public static final String ERROR_GENERAL_DISPOSITIVOUCI_CODE = "7000";
	public static final String ERROR_FITBANK_INTERNO_GENERAL_CODE = "7001";
	
	public static final String ERROR_TIMEOUT_SMS_GENERAL = "8000";
	public static final String ERROR_SMS_FITBANK = "8001";
	public static final String ERROR_RESPUESTA_INFOBIP = "8002";
	public static final String ERROR_SMS_NO_ENVIADO_INFOBIP = "8003";
	
	public static final String ERROR_TIMEOUT_EXCHANGE_RATE = "8004"; // Cuando se ejecuta el fallback de exchangeRate
	public static final String ERROR_TIMEOUT_PERSONAL_INFORMATION = "8005"; // Cuando se ejecuta el fallback de getPersonalInformation
	public static final String ERROR_TIMEOUT_REGISTER_WALLEX_SOLICITUDE = "8006"; // Cuando se ejecuta el fallback de getPersonalInformation
	public static final String ERROR_TIMEOUT_TRANSFER_CURRENCY = "8007"; // Cuando se ejecuta el fallback de transferCurrencyExchange
	public static final String ERROR_TIMEOUT_VALIDATE_TRANSFER = "8008"; // Cuando se ejecuta el fallback de validateTransfer

	public static final String ERROR_EMPTY_EXCHANGE_RATE = "8009"; // No tiene el tipo de cambio en la respuesta
	public static final String ERROR_GRS_EMPTY = "8010"; // No existe el GRS en la trama de respuesta
	public static final String ERROR_GRS_CODE_EMPTY = "8011"; // No existe el código de respuesta en el GRS
	public static final String ERROR_OBTENCION_COMISION = "8012";
	
	public static final String ERROR_WALLEX_SOLICITUDE_LOG_EMPTY = "8013";
	public static final String ERROR_WALLEX_SOLICITUDE_LOG_NO_VALIDATED = "8014";
	public static final String ERROR_WALLEX_SOLICITUDE_LOG_VALIDATED = "8015";
        public static final String ERROR_WALLEX_SOLICITUDE_LOG_TRANSFERED = "8016";
        public static final String ERROR_WALLEX_SOLICITUDE_LOG_CADUCADA = "8017";
	
	public static final String ERROR_SMS_NEW_TOKEN = "8020";
	public static final String ERROR_SMS_UPDATE_TOKEN = "8021";
	public static final String ERROR_SMS_VALIDATE_TOKEN = "8022";
	public static final String ERROR_SESION_INEXISTENTE_VALIDATE_TOKEN = "8023";
	public static final String ERROR_TOKEN_CADUCADO_VALIDATE_TOKEN = "8024";
	public static final String ERROR_INTENTOS_MAXIMOS_VALIDATE_TOKEN = "8025";
	/******************************************************************************/
	
	// Código de Errores originados en REST API (9000 - 9999)
	public static final String NULL_POINTER_ERROR_CODE = "9001";
	public static final String USER_ARGUMENTS_INVALID_ERROR_CODE = "9002";
	
	// Estatus de un log Solicitud Wallex
	public static final String REGISTRADO = "REGISTRADO";
	public static final String VALIDADO = "VALIDADO";
	public static final String TRANSFERIDO = "TRANSFERIDO";
        public static final String CADUCADA = "CADUCADA";
        
        
	public static final String APP_NAME = "Creditos Digitales";

	// Usadas para Cancelar Cuenta:
	public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
	public static final String COMPANIA = "COMPANIA";
	public static final String MONEDACTA = "MONEDACTA";
	public static final String PARAMETRO17 = "PARAMETRO17";
	public static final String NOMBREPERSONA = "NOMBREPERSONA";
	public static final String CGRUPOPROD = "CGRUPOPROD";
	public static final String IDENTIFICACION = "IDENTIFICACION";
	public static final String CPRODUCTO = "CPRODUCTO";
	public static final String SDISPONIBLE = "SDISPONIBLE";
	public static final String CTASCOBRAR = "CTASCOBRAR";
	public static final String CPRODUCTO_EWALLET = "93";
	public static final String ERROR_PRODUCTO_EWALLET = "8050";
	public static final String ERROR_CUENTA_EWALLET = "8051";
	public static final String ERROR_SALDOS_PENDIENTES_EWALLET = "8052";

}