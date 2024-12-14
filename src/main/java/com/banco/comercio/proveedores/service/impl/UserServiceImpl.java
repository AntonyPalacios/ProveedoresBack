package com.banco.comercio.proveedores.service.impl;

import com.banco.comercio.proveedores.fitbank.beans.UserFitBank;
import com.banco.comercio.proveedores.loan.BusinessDelegate;
import com.banco.comercio.proveedores.service.UserService;
import com.banco.comercio.proveedores.util.TransactionFitBank;
import com.fitbank.common.*;
import com.fitbank.common.crypto.Decrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author C&A Pro Consulting
 *
 */
@Repository
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private Decrypt decrypt;

	@Value("${app.ipAddress}")
	private String ipAddress;

	@Value("${app.channel}")
	private String channel;

	@Autowired
	private BusinessDelegate businessDelegate;

	@Bean
	public Decrypt getDecrypt() {
		decrypt = new Decrypt();
		return decrypt;
	}

	@Override
	public UserFitBank login(String id, String password){
		UserFitBank user = null;
		Detail det = new Detail();
		String message = null;
		det.setUser(id);

		try {
			det.setPassword(decrypt.encrypt(password));
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
		det.setIpaddress(ipAddress);
		det.setType(TransactionFitBank.LOGIN.getType());
		det.setSubsystem(TransactionFitBank.LOGIN.getSubsystem());
		det.setTransaction(TransactionFitBank.LOGIN.getTransacction());
		det.setVersion(TransactionFitBank.LOGIN.getVersion());
		det.setMessageid(Uid.getString());
		det.setSessionid(Uid.getString());
		det.setChannel(channel);
		det.setReverse("0");

		try {
			message = det.toXml();
			logger.info("Login RQ: {}", message);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}


		try {
			det = businessDelegate.process(det);
			message = det.toXml();
			//PERMITIR SOLO ACCESO A USUARIOS CON ROL 13
//			if(det.getRole()!= null && det.getRole()!=13){
//				throw new AuthenticationServiceException("USUARIO O PASSWORD INCORRECTO");
//			}
			logger.info("Login RS: {}", message);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		GeneralResponse resp = det.getResponse();
		if (resp.getCode().compareTo(GeneralResponse.OK) == 0) {
			user = new UserFitBank();
			user.setUsuario(det.getUser());
			user.setNameClient(det.findTableByName("TCOMPANIAUSUARIOS").findRecordByNumber(0)
					.findFieldByName("NOMBRELEGAL").getStringValue());
			user.setRol(det.findFieldByNameCreate("_RoleName").getStringValue());

		} else {
			//MENSAJE GENERAL EN CASO DE USUARIOS NULOS, BLOQUEADOS O INACTIVOS
			throw new AuthenticationServiceException("USUARIO O PASSWORD INCORRECTO");
		}

		return user;
	}

	@Override
	public User findByUserName(String id) {

		User user = null;
		Detail det = new Detail();
		String message = null;
		det.setUser(id);
		det.setIpaddress(ipAddress);
		det.setType(TransactionFitBank.GENERAL.getType());
		det.setSubsystem(TransactionFitBank.GENERAL.getSubsystem());
		det.setTransaction(TransactionFitBank.GENERAL.getTransacction());
		det.setVersion(TransactionFitBank.GENERAL.getVersion());
		det.setMessageid(Uid.getString());
		det.setSessionid(Uid.getString());
		det.setChannel(channel);
		det.setReverse("0");

		try {
			det.setAccountingdate(new Date(Calendar.getInstance().getTimeInMillis()));
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		Table tCompaniaUsuarios = new Table("TCOMPANIAUSUARIOS", "TCOMPANIAUSUARIOS");
		Criterion criterionTCompaniaUsuarios = new Criterion("CUSUARIO", "CUSUARIO", id);
		tCompaniaUsuarios.addCriterion(criterionTCompaniaUsuarios);

		// Informacion a devolver
		Record record = new Record();
		record.addField(new Field("CSUCURSAL"));
		record.addField(new Field("COFICINA"));
		record.addField(new Field("CAREA"));
		tCompaniaUsuarios.addRecord(record);

		det.addTable(tCompaniaUsuarios);

		try {
			message = det.toXml();
			logger.info("General RQ: {}", message);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}


		try {
			det = businessDelegate.process(det);
			message = det.toXml();
			logger.info("General RS: {}", message);
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		GeneralResponse resp = det.getResponse();
		if (resp.getCode().compareTo(GeneralResponse.OK) == 0) {
			User userDetail = new User(det.getUser(),det.getPassword(),new ArrayList<>());
//			user = new UserFitBank();
//			user.setUsuario(det.getUser());
//			user.setPassword(det.getPassword());
//			user.setNameClient("");
//			if (det.findTableByName("TCOMPANIAUSUARIOS") != null
//					&& det.findTableByName("TCOMPANIAUSUARIOS").findRecordByNumber(0) != null) {
//				tCompaniaUsuarios = det.findTableByName("TCOMPANIAUSUARIOS");
//				user.setBranch(tCompaniaUsuarios.findRecordByNumber(0).findFieldByName("CSUCURSAL").getIntegerValue());
//				user.setOffice(tCompaniaUsuarios.findRecordByNumber(0).findFieldByName("COFICINA").getIntegerValue());
//				user.setArea(tCompaniaUsuarios.findRecordByNumber(0).findFieldByName("CAREA").getIntegerValue());
//			}
			return userDetail;

		} else {
			throw new AuthenticationServiceException(resp.getUserMessage().replace("<*>", "").trim());
		}


	}

}
