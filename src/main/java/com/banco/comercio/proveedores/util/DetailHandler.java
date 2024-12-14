package com.banco.comercio.proveedores.util;

import com.fitbank.common.Detail;
import com.fitbank.common.Uid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Logger;

@Slf4j
@Component
public class DetailHandler {
	//private static Logger logger = Logger.getLogger(DetailHandler.class);
	
	/*
			fit.ipaddress=2.2.2.2
			fit.channel=WEB
			fit.sesionlogin=LOGINHB
			fit.opensession=LOGINBM
			fit.terminaexclude=BM2202
	*/
	
	
	@Value("${app.ipAddress}")
//	@Value("${fitbank.IPA}")
	private String ipAddress;

	@Value("${app.channel}")
//	@Value("${fitbank.CAN}")
	private String channel;
	
	@Value("${fitbank.USR}")
	private String userFitbank;
	
	@Value("${fitbank.IDM}")
	private String language;
	
	@Value("${fitbank.TER}")
	private String terminal;
	
	@Value("${fitbank.ROL}")
	private int role;
	
	@Value("${fitbank.CIO}")
	private int company=2;
	
	@Value("${fitbank.SUC}")
	private int branch;
	
	@Value("${fitbank.OFC}")
	private int office;
	
	@Value("${fitbank.ARE}")
	private String area;
	
	private static Logger log = Logger.getLogger("Proveedores-");
	
	public void prepareHeaderDetail(Detail pDetail/* ,BaseBM pBase */   ) {
		log.info("DetailHandler.prepareHeaderDetail - inicio");
		
        pDetail.setUser(userFitbank);
		pDetail.setIpaddress(ipAddress);
		pDetail.setSessionid(Uid.getString());
		pDetail.setLanguage(language);
		pDetail.setTerminal(terminal);
		pDetail.setChannel(channel);
		pDetail.setRole(role);
		pDetail.setCompany(company);
		pDetail.setOriginbranch(branch);
		pDetail.setOriginoffice(office);
		pDetail.setSecuritylevel(10);
		pDetail.setAccountingdate(new Date(Calendar.getInstance().getTimeInMillis()));
		pDetail.setArea(area);
		pDetail.setMessageid(Uid.getString());	
	}
	
}
