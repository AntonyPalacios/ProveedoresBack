package com.fitbank.common;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * CLase encargada de la respuesta
 */
public class GeneralResponse implements Serializable {
    /**
     * Cï¿½digo de respuesta para la Salida Satisfactoria
     */
    public final static String OK               = "0";

    /**
     * Cï¿½digo de la salida
     */
    private String             code             = "";

    /**
     * Mensaje de usuario
     */
    private String             userMessage      = "";

    /**
     * Mensaje tï¿½cnico de error.
     */
    private String             technicalMessage = "";

    /**
     * Stack trace del proceso de una mensaje si sale por error.
     */
    private String             stackTrace;

    /**
     * Indica si la excepciï¿½n se origina en UCI, cuando se cae la comunicaciï¿½n
     * con Fitbank.
     */
    private boolean            uciException     = false;

    /**
     * Indicador de si el resutado termina con commit;
     */
    private boolean            commitable       = false;

    /**
     * Versiï¿½n de Serializaciï¿½n
     */
    private static final long  serialVersionUID = 1L;

    private BigDecimal         balance;

    private Date               accountingDate;

    /**
     * Crea una nueva instancia de GeneralResponse
     * 
     * @param pCode
     *            Cï¿½digo
     */
    public GeneralResponse(String pCode) {
        this.code = pCode;
    }

    /**
     * Crea una nueva instancia de GeneralResponse
     * 
     * @param pCode
     *            Cï¿½digo
     * @param pUserMessage
     *            Mensaje de usuario
     */
    public GeneralResponse(String pCode, String pUserMessage) {
        this.code = pCode;
        this.userMessage = pUserMessage;
    }

    /**
     * Crea una nueva instancia de GeneralResponse
     * 
     * @param pCode
     *            Cï¿½digo
     * @param pUserMessage
     *            Mensaje de Usuario
     * @param pTechnicalMessage
     *            Mensaje Tï¿½cnico.
     */
    public GeneralResponse(String pCode, String pUserMessage, String pTechnicalMessage) {
        this.code = pCode;
        this.userMessage = pUserMessage;
        this.technicalMessage = pTechnicalMessage;
    }

    public Date getAccountingDate() {
        return this.accountingDate;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * @return Entrega el valor de code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Transforma un response a un nodo XML.
     * 
     * @return
     * @throws Exception
     */
    public Node getResponseNode() throws Exception {
        Node node = XmlHelper.createNode("GRS");
        XmlHelper.addAttribute(node, "cod", this.code);
        XmlHelper.appendChild(node, "MSGU", this.userMessage);
        if (this.technicalMessage != null) {
            XmlHelper.appendChild(node, "MSGP", this.technicalMessage);
        }
        if (this.stackTrace != null) {
            XmlHelper.appendChild(node, "STKT", this.stackTrace);
        }
        return node;
    }

    /**
     * Entrega el valor de stackTrace.
     * 
     * @return stackTrace.
     */
    public String getStackTrace() {
        return this.stackTrace;
    }

    /**
     * @return Entrega el valor de technicalMessage.
     */
    public String getTechnicalMessage() {
        return this.technicalMessage;
    }

    /**
     * @return Entrega el valor de userMessage.
     */
    public String getUserMessage() {
        return this.userMessage;
    }

    /**
     * Entrega el valor de isUciException
     * 
     * @return isFitbankException
     */
    public boolean isUciException() {
        return this.uciException;
    }

    public void setAccountingDate(Date accountingDate) {
        this.accountingDate = accountingDate;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Fija el valor de code
     * 
     * @param pCode
     *            Valor de code a fijar.
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     * Fija el valor de stackTrace.
     * 
     * @param stackTrace
     *            .
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    /**
     * Fija el valor de technicalMessage
     * 
     * @param pTechnicalMessage
     *            Valor de technicalMessage a fijar.
     */
    public void setTechnicalMessage(String pTechnicalMessage) {
        this.technicalMessage = pTechnicalMessage;
    }

    /**
     * Fija el valor de isFitbankException
     * 
     * @param isFitbankException
     */
    public void setUciException(boolean isUciException) {
        this.uciException = isUciException;
    }

    /**
     * Fija el valor de userMessage
     * 
     * @param pUserMessage
     *            Valor de userMessage a fijar.
     */
    public void setUserMessage(String pUserMessage) {
        this.userMessage = pUserMessage;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[codigo]" + this.code + "[mensaje]" + this.userMessage;
    }

    /**
     * Fija el valor de la propiedad commitable
     * @param commitable El valor para commitable
     */
    public void setCommitable(boolean commitable) {
        this.commitable = commitable;
    }

    /**
     * Entrega el Valor de commitable
     * @return Valor de commitable
     */
    public boolean isCommitable() {
        return commitable;
    }
}
