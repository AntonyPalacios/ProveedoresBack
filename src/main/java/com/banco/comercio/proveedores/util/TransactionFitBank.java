package com.banco.comercio.proveedores.util;

public enum TransactionFitBank {

	QUERY("CON", "18", "7500", "01", "CONSULTA"),
	MANTENIMIENTO("MAN", "18", "7500", "01", "GRABAR"),
	LOGIN("SIG", "01", "6100", "01", "Logeo"),
	GENERAL("CON", "01", "0003", "01", "Consulta General");
	
	private String type;
	private String subsystem;
	private String transacction;
	private String version;
	private String description;

	private TransactionFitBank(String type, String subsystem, String transacction, String version,
                               String description) {
		this.type = type;
		this.subsystem = subsystem;
		this.transacction = transacction;
		this.version = version;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public String getTransacction() {
		return transacction;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

}
