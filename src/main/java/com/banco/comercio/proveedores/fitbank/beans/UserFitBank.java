package com.banco.comercio.proveedores.fitbank.beans;

import lombok.Data;

@Data
public class UserFitBank {

	private String usuario;
	private String password;
	private String nameClient;
	private String rol;
	private Integer branch;
	private Integer office;
	private Integer area;
	private Long lifeTimeSession;

}
