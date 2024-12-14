package com.banco.comercio.proveedores.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
//@PropertySources({ @PropertySource(value = "classpath:uciclient.properties"),
//		@PropertySource(value = "file:${uciclient.properties.path}", ignoreResourceNotFound = true) })
@PropertySource(value = "classpath:uciclient.properties")
public class UCIConfig {

	@Value("${uci2.host}")
	private String uci2Host;

	@Value("${uci2.port}")
	private String uci2port;

	@Value("${uci2.timeout}")
	private String uci2Timeout;

	@Value("${uci3.host}")
	private String uci3Host;

	@Value("${uci3.port}")
	private String uci3Port;

	@Value("${uci3.timeout}")
	private String uci3Timeout;


	public String getUci2Host() {
		return uci2Host;
	}

	public void setUci2Host(String uci2Host) {
		this.uci2Host = uci2Host;
	}

	public String getUci2port() {
		return uci2port;
	}

	public void setUci2port(String uci2port) {
		this.uci2port = uci2port;
	}

	public String getUci2Timeout() {
		return uci2Timeout;
	}

	public void setUci2Timeout(String uci2Timeout) {
		this.uci2Timeout = uci2Timeout;
	}

	public String getUci3Host() {
		return uci3Host;
	}

	public void setUci3Host(String uci3Host) {
		this.uci3Host = uci3Host;
	}

	public String getUci3Port() {
		return uci3Port;
	}

	public void setUci3Port(String uci3Port) {
		this.uci3Port = uci3Port;
	}

	public String getUci3Timeout() {
		return uci3Timeout;
	}

	public void setUci3Timeout(String uci3Timeout) {
		this.uci3Timeout = uci3Timeout;
	}



}
