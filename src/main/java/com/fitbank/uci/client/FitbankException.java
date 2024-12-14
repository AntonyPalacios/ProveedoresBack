package com.fitbank.uci.client;

/**
 *
 * @author CROSSNET SAC
 */
public class FitbankException extends Exception {
    private String code;
    private String details;
    private int httpErrorCode = 0;

    public FitbankException(String code) {
        this.code = code;
    }

    public FitbankException(String code, String message) {
        super(message);
        this.details = message;
        this.code = code;
    }

    public FitbankException(String code, String message, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    
}
