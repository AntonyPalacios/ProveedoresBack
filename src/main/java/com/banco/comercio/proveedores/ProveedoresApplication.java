package com.banco.comercio.proveedores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { ProveedoresApplication.class }, scanBasePackages = {
"com.banco.comercio.proveedores", "com.fitbank.uci.client"})
public class ProveedoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProveedoresApplication.class, args);
	}

}
