package com.banco.comercio.proveedores.provider;

import com.banco.comercio.proveedores.fitbank.beans.UserFitBank;
import com.banco.comercio.proveedores.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author C&A Pro Consulting
 *
 */
@Component
public class FitbankAuthenticationProvider implements AuthenticationProvider {

	private static Logger logger = LoggerFactory.getLogger(FitbankAuthenticationProvider.class);

	@Autowired
	UserService loginRepository;


	@Override
	public Authentication authenticate(Authentication authentication) {
		//
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		//

		UserFitBank user = loginRepository.login(name, password);

		if (user != null) {
			List<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRol()));

			return new UsernamePasswordAuthenticationToken(user.getUsuario(), null, authorities);
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
