package com.banco.comercio.proveedores.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "fitbank")
public class FitbankProperties {

  private String USR;
  private String IDM;
  private String TER;
  private String SID;
  private String ROL;
  private String NVS;
  private String IPA;
  private String TIP;
  private String VER;
  private String ARE;
  private String TPP;
  private String CIO;
  private String SUC;
  private String OFC;
  private String MSG;
  private String REV;
  private String CAN;
  private String FCN;
  private String CPERSONA;

}
