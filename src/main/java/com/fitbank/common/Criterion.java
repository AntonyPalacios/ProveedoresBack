package com.fitbank.common;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Clase que se encarga de manejo de criterios de consulta.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class Criterion implements DetailField, Serializable {
    // TODO: Definir si se usa LIKE
    /**
	 *
	 */
    private static final long serialVersionUID = 2L;

    /**
     * Alias del criterio.
     */
    private String            alias;

    /**
     * Nombre del criterio.
     */
    private String            name;

    /**
     * Valro del criterio.
     */
    private Object            value;

    /**
     * Orden a utiliar en el order by de la sentencia HQL.
     */
    private Integer           order;

    private String            origenDB;

    /**
     * Condicion a utilizar en las restricciones.
     */
    private String            condition        = "LIKE";

    private boolean           internal         = false;

    /**
     * Crea una nueva instancia de criterio dado un nodo de un documento XML.
     * 
     * @param pNode
     *            Nodo de referencia.
     */
    public Criterion(Node pNode) throws Exception {
        this.alias = XmlHelper.getStringValueByAttribute(pNode, "alias");
        this.name = XmlHelper.getStringValueByAttribute(pNode, "name");
        this.value = XmlHelper.getStringValueByAttribute(pNode, "val");
        this.condition = XmlHelper.getStringValueByAttribute(pNode, "cond");
        this.order = XmlHelper.getIntegerValueByAttribute(pNode, "ord");
    }

    /**
     * Crea una nueva instancia de criterio.
     * 
     * @param pName
     *            Nombre del citerio.
     */
    public Criterion(String pName) {
        this.name = pName;
    }

    /**
     * Crea una nueva instancia de criterio.
     * 
     * @param pName
     *            Nombre del citerio.
     * @param pValue
     *            Valor del criterio.
     */
    public Criterion(String pName, Object pValue) {
        this.name = pName;
        this.setValue(pValue);
    }

    public Criterion(String alias, String pName, Object pValue) {
        this.alias = alias;
        this.name = pName;
        this.setValue(pValue);
    }

    /**
     * Entrega el valor de condition.
     * 
     * @return condition.
     */
    public String getCondition() {
        return this.condition;
    }

    public String getAlias() {
        return this.alias;
    }

    /**
     * Entrega el valor de name.
     * 
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Entrega el valor de order.
     * 
     * @return order.
     */
    public Integer getOrder() {
        return this.order;
    }

    public String getOrigenDB() {
        return this.origenDB;
    }

    public String getRealName() {
        String aux = this.name;
        int index = aux.indexOf('+');
        if (index > 0) {
            aux = aux.substring(0, index) + "." + aux.substring(index + 1);
        }
        index = aux.indexOf(':');
        if (index > 0) {
            aux = aux.substring(0, index) + "." + aux.substring(index + 1);
        }
        return aux.toUpperCase();
    }

    /**
     * Entrega el valor de value.
     * 
     * @return value.
     */
    public Object getValue() {
        if ((this.value instanceof String) && (this.value != null) && (((String) this.value).trim().compareTo("") == 0)) {
            return null;
        }
        return this.value;
    }

    public boolean isInternal() {
        return this.internal;
    }

    /**
     * Fija el valor de condition.
     * 
     * @param condition
     *            .
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Fija el valor de name.
     * 
     * @param name
     *            .
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Fija el valor de order.
     * 
     * @param order
     *            .
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setOrigenDB(String origenDB) {
        this.origenDB = origenDB;
    }

    /**
     * Fija el valor de value.
     * 
     * @param value
     *            .
     */
    public void setValue(Object value) {
        /*
         * if(value instanceof String){ value=((String)value).toUpperCase(); }
         */
        this.value = value;
    }

	public List<Dependence> getDependencies() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Transforma una criterio en un nodo XML.
     * 
     * @return node
     * @throws Exception
     */
    public Node toNode() throws Exception {
        Node node = XmlHelper.createNode("CRI");
        if (this.alias != null) {
            XmlHelper.addAttribute(node, "alias", this.alias);
        }
        XmlHelper.addAttribute(node, "name", this.name);
        try {
            XmlHelper.addAttribute(node, "val", this.value);
        } catch (Exception ex) {

        }
        if (this.order != null) {
            XmlHelper.addAttribute(node, "ord", this.order);
        }
        if (this.condition != null) {
            XmlHelper.addAttribute(node, "cond", this.condition);
        }
        return node;
    }

    @Override
    public String toString() {
        if (this.alias != null) {
            return "Criterion(" + this.alias + ", " + this.name + ")";
        } else {
            return "Criterion(" + this.name + ")";
        }
    }
}
