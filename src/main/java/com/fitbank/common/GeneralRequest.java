package com.fitbank.common;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;

/**
 * Clase para Peticiones
 * 
 */
@Slf4j
public abstract class GeneralRequest extends HashMap<String, Object> implements Serializable, Cloneable {

    private static final long serialVersionUID    = 3L;

    /**
     * Tipo de mensaje
     */
    protected String          processType;

    /**
     * Tipo de mensaje
     */
    protected String          type;

    /**
     * Codigo de Usuario
     */
    protected String          user;

    /**
     * Codigo de Usuario interno.
     */
    protected Integer         innerusercode;

    /**
     * Password del usuario.
     */
    protected String          password;

    /**
     * Codigo de Terminal.
     */
    protected String          terminal;

    /**
     * Idioma preferido del usuario.
     */
    protected String          language;

    /**
     * Rol del Usuario
     */
    protected Integer         role;

    /**
     * Identificador de la Sesiï¿½n o token.
     */
    protected String          sessionid;

    /**
     * Nivel de Seguridad del Usuario
     */
    protected Integer         securitylevel;

    /**
     * IP del terminal
     */
    protected String          ipaddress;

    /**
     * MAC address del terminal
     */
    protected String          macaddress;

    /**
     * Subsistema seleccionado por el usuario
     */
    protected String          subsystem;

    /**
     * Transacciï¿½n seleccionado por el Usuario
     */
    protected String          transaction;

    /**
     * Versiï¿½n de la transacciï¿½n selecionada
     */
    protected String          version;

    /**
     * Numero de mensaje con el cual se procesa una transaccion. user +
     * threadNumber + timestamp de la base de datos.
     */
    protected String          messageId;

    /**
     * Numero de hilo, utilizado en procesos batch procesamiento multihilo, este
     * formara parte del numero de mensaje de la transacciï¿½n.
     */
    private String            threadNumber        = "1";

    /**
     * Numero de comprobante contable.
     */
    private String            accountinNumber;

    /** Canal */
    protected String          channel;

    /**
     * Companï¿½a origen.
     */
    protected Integer         company;

    /**
     * Sucursal origen.
     */
    protected Integer         originBranch;

    /**
     * Oficina origen.
     */
    protected Integer         originOffice;

    /**
     * Fecha conatable de la transaccion.
     */
    protected Date            accountingDate;

    /**
     * Codigo de usuario que autoriza la transaccion.
     */
    protected String          authorizeruser;

    /**
     * Password del usuario que autoriza la transaccion.
     */
    protected String          authorizerpassword;

    /**
     * Area
     */
    protected String          area;

    /**
     * Indica si la transaccion se ejecuta en modo reverso.
     */
    protected String          reverse             = "0";

    /**
     * Numero de mensaje a reversar.
     */
    protected String          messageidreverse;

    /**
     * Password del usuario.
     */
    protected String          newpassword;

    /**
     * Password del usuario.
     */
    protected String          realtransaction;

    /**
     * Indica si la transacciï¿½n se ejecuta en modo batch.
     */
    protected boolean         batch               = false;

    /**
     * Indica que la transacciï¿½n es generada de manera Externa
     */
    protected boolean         externalTransaction = false;

    /**
     * Nombre del archivo que origina la Transaccion
     */
    protected String          file;

    /**
     * General response.
     */
    protected GeneralResponse response            = null;

    private void appendValidate(Node pNode, String pTag, Object pValue) throws Exception {
        if (pValue != null) {
            XmlHelper.appendChild(pNode, pTag, pValue);
        }
    }

    public void copy(GeneralRequest pParam) throws Exception {
        this.copyFields(GeneralRequest.class.getDeclaredFields(), pParam);
    }

    private void copyFields(Field[] fs, GeneralRequest pParam) throws Exception {
        for (Field f : fs) {
            try {
                f.set(pParam, f.get(this));
            } catch (Exception e) {
                continue;
            }
        }
    }

    private void copyFieldsToDetail(Field[] fs, GeneralRequest pParam) throws Exception {
        for (Field f : fs) {
            try {
                f.set(pParam, f.get(this));
            } catch (Exception e) {
                continue;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void copySuperClasses(Object o) {

        try {
            Class subclass = o.getClass();
            Class superclass = subclass.getSuperclass();
            while (superclass != null) {
                String className = superclass.getName();
                subclass = superclass;
                if (className.compareTo("com.fitbank.dto.GeneralRequest") == 0) {
                    Field[] campos = subclass.getDeclaredFields();
                    this.copy((GeneralRequest) o);
                    for (Field campo : campos) {
                        try {
                            campo.set(this, campo.get(o));
                        } catch (IllegalAccessException e) {
                            continue;
                        }
                    }
                }
                superclass = subclass.getSuperclass();
            }

        } catch (Exception ex) {
            log.info("ex");
        }

    }

    public void copyToDetail(GeneralRequest pParam) {
        try {
            this.copyFieldsToDetail(pParam.getClass().getDeclaredFields(), pParam);
            this.copySuperClasses(pParam);
            this.copyFieldsToDetail(pParam.getClass().getFields(), pParam);
        } catch (Exception ex) {
        	log.info("ex");
        }

    }

    /**
     * Llena un general request dado un nodo de un Documento XML.
     * 
     * @param pNode
     * @throws Exception
     */
    protected void fillRequest(Node pNode) throws Exception {
        this.user = XmlHelper.getStringValueByTag(pNode, "USR");
        this.password = XmlHelper.getStringValueByTag(pNode, "PWD");
        this.newpassword = XmlHelper.getStringValueByTag(pNode, "NPW");
        this.language = XmlHelper.getStringValueByTag(pNode, "IDM");
        this.terminal = XmlHelper.getStringValueByTag(pNode, "TER");
        this.processType = XmlHelper.getStringValueByTag(pNode, "TPP");
        this.type = XmlHelper.getStringValueByTag(pNode, "TIP");
        this.sessionid = XmlHelper.getStringValueByTag(pNode, "SID");
        this.role = XmlHelper.getIntegerValueByTag(pNode, "ROL");
        this.securitylevel = XmlHelper.getIntegerValueByTag(pNode, "NVS");
        this.ipaddress = XmlHelper.getStringValueByTag(pNode, "IPA");
        this.macaddress = XmlHelper.getStringValueByTag(pNode, "MAC");
        this.subsystem = XmlHelper.getStringValueByTag(pNode, "SUB");
        this.transaction = XmlHelper.getStringValueByTag(pNode, "TRN");
        this.version = XmlHelper.getStringValueByTag(pNode, "VER");
        this.company = XmlHelper.getIntegerValueByTag(pNode, "CIO");
        this.originBranch = XmlHelper.getIntegerValueByTag(pNode, "SUC");
        this.originOffice = XmlHelper.getIntegerValueByTag(pNode, "OFC");
        this.messageId = XmlHelper.getStringValueByTag(pNode, "MSG");
        this.messageidreverse = XmlHelper.getStringValueByTag(pNode, "NMR");
        this.reverse = XmlHelper.getStringValueByTag(pNode, "REV");
        this.channel = XmlHelper.getStringValueByTag(pNode, "CAN");
        this.accountingDate = XmlHelper.getDateValueByTag(pNode, "FCN");
        this.realtransaction = XmlHelper.getStringValueByTag(pNode, "RTN");
        this.area = XmlHelper.getStringValueByTag(pNode, "ARE");
        // Authorizer
        Element e = (Element) pNode;
        if (e != null) {
            NodeList nl = e.getElementsByTagName("AUT");
            if (nl.getLength() > 0) {
                Node n = nl.item(0);
                this.authorizeruser = XmlHelper.getStringValueByTag(n, "USR");
                this.authorizerpassword = XmlHelper.getStringValueByTag(n, "PWD");
            }
        }
    }

    /**
     * Entrega el Valor de accountingdate
     *
     * @return accountingdate
     */
    public Date getAccountingDate() {
        return this.accountingDate;
    }

    //@Deprecated
    public Date getAccountingdate() {
        return getAccountingDate();
    }

    /**
     * Entrega el valor de accountinNumber
     * 
     * @return accountinNumber
     */
    public String getAccountinNumber() {
        return this.accountinNumber;
    }

    public String getArea() {
        return this.area;
    }

    /**
     * Entrega el valor de authorizerpassword.
     * 
     * @return authorizerpassword.
     */
    public String getAuthorizerpassword() {
        return this.authorizerpassword;
    }

    /**
     * Entrega el valor de authorizeruser.
     * 
     * @return authorizeruser.
     */
    public String getAuthorizeruser() {
        return this.authorizeruser;
    }

    /**
     * Entrega el Valor de channel.
     * 
     * @return channel.
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * Entrega el Valor de company.
     * 
     * @return company.
     */
    public Integer getCompany() {
        return this.company;
    }

    public String getFile() {
        return this.file;
    }

    /**
     * Transforma el request General a un nodo XML.
     * 
     * @return nodo.
     * @throws Exception
     */
    public Node getHeaderNode() throws Exception {
        Node node = XmlHelper.createNode("GRQ");
        XmlHelper.appendChild(node, "USR", this.user);
        XmlHelper.appendChild(node, "IDM", this.language);
        XmlHelper.appendChild(node, "TER", this.terminal);
        XmlHelper.appendChild(node, "SID", this.sessionid);
        XmlHelper.appendChild(node, "ROL", this.role);
        XmlHelper.appendChild(node, "NVS", this.securitylevel);
        if ((this.type != null) && (this.type.compareTo("SIG") == 0)) {
            XmlHelper.appendChild(node, "PWD", this.password);
            XmlHelper.appendChild(node, "NPW", this.newpassword);
        }
        if (this.ipaddress != null) {
            XmlHelper.appendChild(node, "IPA", this.ipaddress);
        } else {
            XmlHelper.appendChild(node, "MAC", this.macaddress);
        }
        XmlHelper.appendChild(node, "TIP", this.type);
        XmlHelper.appendChild(node, "SUB", this.subsystem);
        XmlHelper.appendChild(node, "TRN", this.transaction);
        XmlHelper.appendChild(node, "VER", this.version);
        XmlHelper.appendChild(node, "ARE", this.area);
        this.appendValidate(node, "TPP", this.processType);
        this.appendValidate(node, "CIO", this.company);
        this.appendValidate(node, "SUC", this.originBranch);
        this.appendValidate(node, "OFC", this.originOffice);
        this.appendValidate(node, "MSG", this.messageId);
        this.appendValidate(node, "NMR", this.messageidreverse);
        this.appendValidate(node, "REV", this.reverse);
        this.appendValidate(node, "CAN", this.channel);
        this.appendValidate(node, "FCN", this.accountingDate);
        this.appendValidate(node, "RTN", this.realtransaction);
        return node;
    }

    /**
     * Entrega el valor de innerusercode
     * 
     * @return String
     */
    public Integer getInnerusercode() {
        return this.innerusercode;
    }

    /**
     * Entrega el Valor de ipaddress.
     * 
     * @return ipaddress.
     */
    public String getIpaddress() {
        return this.ipaddress;
    }

    /**
     * Entrega el Valor de language.
     * 
     * @return language.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Entrega el valor de macaddress.
     * 
     * @return macaddress.
     */
    public String getMacaddress() {
        return this.macaddress;
    }

    /**
     * Entrega el valor de messageid.
     * 
     * @return messageid.
     */
    public String getMessageId() {
        return this.messageId;
    }

    //@Deprecated
    public String getMessageid() {
        return getMessageId();
    }

    /**
     * Entrega el numeor de mensaje a reversar.
     * 
     * @return messageidreverse
     */
    public String getMessageidreverse() {
        return this.messageidreverse;
    }

    public String getNewpassword() {
        return this.newpassword;
    }

    /**
     * Entrega el valor de originbranch.
     * 
     * @return originbranch.
     */
    public Integer getOriginBranch() {
        return this.originBranch;
    }

   // @Deprecated
    public Integer getOriginbranch() {
        return getOriginBranch();
    }

    /**
     * Entrega el valor de originoffice.
     * 
     * @return originoffice.
     */
    public Integer getOriginOffice() {
        return this.originOffice;
    }

    //@Deprecated
    public Integer getOriginoffice() {
        return getOriginOffice();
    }

    /**
     * Entrega el valor de password.
     * 
     * @return password.
     */
    public String getPassword() {
        return this.password;
    }

    public String getRealtransaction() {
        return this.realtransaction;
    }

    /**
     * Entrega el valor de response.
     * 
     * @return response.
     */
    public GeneralResponse getResponse() {
        return this.response;
    }

    /**
     * Indica si la transaccion se ejecuta en modo normal o reverso.
     * 
     * @return
     */
    public String getReverse() {
        return ((this.reverse == null) || (this.reverse.compareTo("") == 0)) ? "0" : this.reverse;
    }

    /**
     * Entrega el Valor de role.
     * 
     * @return role.
     */
    public Integer getRole() {
        return this.role;
    }

    /**
     * Entrega el Valor de securitylevel.
     * 
     * @return securitylevel.
     */
    public Integer getSecuritylevel() {
        return this.securitylevel;
    }

    /**
     * Entrega el Valor de sessionid.
     * 
     * @return sessionid.
     */
    public String getSessionid() {
        return this.sessionid;
    }

    /**
     * Entrega el Valor de subsystem.
     * 
     * @return subsystem.
     */
    public String getSubsystem() {
        return this.subsystem;
    }

    /**
     * Entrega el Valor de terminal.
     * 
     * @return terminal.
     */
    public String getTerminal() {
        return this.terminal;
    }

    /**
     * Entrega el valor de threadNumber
     * 
     * @return threadNumber
     */
    public String getThreadNumber() {
        return this.threadNumber;
    }

    /**
     * Entrega el Valor de transaction.
     * 
     * @return transaction.
     */
    public String getTransaction() {
        return this.transaction;
    }

    public String getProcessType() {
        return this.processType;
    }

    /**
     * Entrega el valor de type.
     * 
     * @return type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Entrega el Valor de user.
     * 
     * @return user.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Entrega el Valor de version.
     * 
     * @return version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Entrega el valor de isBatch
     * 
     * @return boolean
     */
    public boolean isBatch() {
        return this.batch;
    }

    public boolean isExternalTransaction() {
        return this.externalTransaction;
    }

    /**
     * Indica si la transaccion necesita iniciar una transaccion de Hibernate.
     * 
     * @return
     */
    public abstract boolean isTransactionRequired();

    /**
     * Fija el valor de accountingdate
     * 
     * @param accountingdate
     */
    public void setAccountingDate(Date accountingDate) {
        this.accountingDate = accountingDate;
    }

    //@Deprecated
    public void setAccountingdate(Date accountingDate) {
        setAccountingDate(accountingDate);
    }

    /**
     * Fija el valor de accountinNumber
     * 
     * @param accountinNumber
     */
    public void setAccountinNumber(String accountinNumber) {
        this.accountinNumber = accountinNumber;
    }

    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Fija el valor de authorizerpassword.
     * 
     * @param authorizerpassword
     *            .
     */
    public void setAuthorizerpassword(String authorizerpassword) {
        this.authorizerpassword = authorizerpassword;
    }

    /**
     * Fija el valor de authorizeruser.
     * 
     * @param authorizeruser
     *            .
     */
    public void setAuthorizeruser(String authorizeruser) {
        this.authorizeruser = authorizeruser;
    }

    /**
     * Fija el valor de isBatch
     * 
     * @param isBatch
     */
    public void setBatch(boolean isBatch) {
        this.batch = isBatch;
    }

    /**
     * Fija el valor de channel
     * 
     * @param channel
     *            Nuevo valor de channel.
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Fija el valor de company
     * 
     * @param company
     *            Nuevo valor de company.
     */
    public void setCompany(Integer company) {
        this.company = company;
    }

    public void setExternalTransaction(boolean externalTransaction) {
        this.externalTransaction = externalTransaction;
    }

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Fija el valor de innerusercode
     * 
     * @param innerusercode
     */
    public void setInnerusercode(Integer innerusercode) {
        this.innerusercode = innerusercode;
    }

    /**
     * Fija el valor de ipaddress
     * 
     * @param ipaddress
     *            Nuevo valor de ipaddress.
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    /**
     * Fija el valor de language
     * 
     * @param language
     *            Nuevo valor de language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Fija el valor de macaddress.
     * 
     * @param macaddress
     *            .
     */
    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    /**
     * Fija el valor de messageid.
     * 
     * @param messageid
     *            .
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    //@Deprecated
    public void setMessageid(String messageId) {
        setMessageId(messageId);
    }

    /**
     * Fija el numeor de mensaje a reversar.
     * 
     * @param pMessageidreverse
     */
    public void setMessageidreverse(String pMessageidreverse) {
        this.messageidreverse = pMessageidreverse;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    /**
     * Fija el valor de originbranch.
     * 
     * @param originbranch
     *            .
     */
    public void setOriginBranch(Integer originBranch) {
        this.originBranch = originBranch;
    }

    @Deprecated
    public void setOriginbranch(Integer originBranch) {
        setOriginBranch(originBranch);
    }

    /**
     * Fija el valor de originoffice.
     * 
     * @param originoffice
     *            .
     */
    public void setOriginOffice(Integer originOffice) {
        this.originOffice = originOffice;
    }

    //@Deprecated
    public void setOriginoffice(Integer originOffice) {
        setOriginOffice(originOffice);
    }

    /**
     * Fija el valor de password.
     * 
     * @param password
     *            .
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealtransaction(String newrealtransaction) {
        this.realtransaction = newrealtransaction;
    }

    /**
     * Fija el valor de response.
     * 
     * @param response
     *            .
     */
    public void setResponse(GeneralResponse response) {
        this.response = response;
    }

    /**
     * Fija la transaccion en modo normal (0), reverso(1).
     * 
     * @param reverse
     */
    public void setReverse(String pReverse) {
        if ((pReverse == null) || (pReverse.compareTo("") == 0)) {
            this.reverse = "0";
        } else {
            this.reverse = pReverse;
        }
    }

    /**
     * Fija el valor de role
     * 
     * @param role
     *            Nuevo valor de role.
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * Fija el valor de securitylevel
     * 
     * @param securitylevel
     *            Nuevo valor de securitylevel.
     */
    public void setSecuritylevel(Integer securitylevel) {
        this.securitylevel = securitylevel;
    }

    /**
     * Fija el valor de sessionid
     * 
     * @param sessionid
     *            Nuevo valor de sessionid.
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * Fija el valor de subsystem
     * 
     * @param subsystem
     *            Nuevo valor de subsystem.
     */
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * Fija el valor de terminal
     * 
     * @param terminal
     *            Nuevo valor de terminal.
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * Fija el valor de threadNumber
     * 
     * @param threadNumber
     */
    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }

    /**
     * Fija el valor de transaction
     * 
     * @param transaction
     *            Nuevo valor de transaction.
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setProcessType(String pProcessType) {
        this.processType = pProcessType;
    }

    /**
     * Fija el valor de type.
     * 
     * @param pType
     *            .
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     * Fija el valor de user
     * 
     * @param user
     *            Nuevo valor de user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Fija el valor de version
     * 
     * @param version
     *            Nuevo valor de version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Implementaciï¿½n toString
     */
    @Override
    public String toString() {
        Field[] fs = this.getClass().getDeclaredFields();
        String data = "";
        for (Field f : fs) {
            try {
                String name = f.getName();
                if ((name.compareTo("hashValue") == 0) || (name.compareTo("serialVersionUID") == 0)) {
                    continue;
                }
                data += name + "=" + f.get(this) + ";";
            } catch (Exception e) {
                continue;
            }
        }
        if (data.compareTo("") == 0) {
            data = super.toString();
        }
        return data;
    }
}
