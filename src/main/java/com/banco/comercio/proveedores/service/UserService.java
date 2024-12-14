package com.banco.comercio.proveedores.service;


import com.banco.comercio.proveedores.fitbank.beans.UserFitBank;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author C&A Pro Consulting
 *
 */
public interface UserService {

	public UserFitBank login(String id, String password) ;
	
	public User findByUserName(String id);
}
