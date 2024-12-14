package com.fitbank.common;

import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * Dependencias utilizadas para obtener descripciones y/o valores resultantes de
 * un query previo.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class Dependence implements Serializable, Cloneable {

	/**
		 *  
		 */
	private static final long serialVersionUID = 2L;

	/**
	 * Alias campo padre.
	 */
	private String fromAlias;

	/**
	 * Tabla campo padre.
	 */
	private String from;

	/**
	 * Alias campo dependiente.
	 */
	private String toAlias;

	/**
	 * Tabla campo dependiente.
	 */
	private String to;

	/**
	 * Valor inmediato
	 */
	private Object value = null;

	/**
	 * Crea una nueva instancia de Dependence dado un Nodo de un documento XML.
	 * 
	 * @param pNode
	 */
	public Dependence(Node pNode) throws Exception {
		this.fromAlias = XmlHelper.getStringValueByAttribute(pNode, "aliasDesde");
		this.from = XmlHelper.getStringValueByAttribute(pNode, "desde");
		this.toAlias = XmlHelper.getStringValueByAttribute(pNode, "aliasHacia");
		this.to = XmlHelper.getStringValueByAttribute(pNode, "hacia");
		this.value = XmlHelper.getStringValueByAttribute(pNode, "val");
	}

	/**
	 * @param to
	 */
	public Dependence(String pTo) {
		this.to = pTo;
	}

	/**
	 * @param from
	 * @param to
	 */
	public Dependence(String pFrom, String pTo) {
		this.from = pFrom;
		this.to = pTo;
	}

	public Dependence(String fromAlias, String pFrom, String pTo) {
		this.fromAlias = fromAlias;
		this.from = pFrom;
		this.to = pTo;
	}

	public Dependence(String fromAlias, String pFrom, String toAlias, String pTo) {
		this.fromAlias = fromAlias;
		this.from = pFrom;
		this.toAlias = toAlias;
		this.to = pTo;
	}

	/**
	 * Clona una dependencia.
	 */
	public Dependence cloneMe() throws CloneNotSupportedException {
		return (Dependence) this.clone();
	}

	public String getFromAlias() {
		return this.fromAlias;
	}

	/**
	 * Entrega el valor de from.
	 * 
	 * @return from.
	 */
	public String getFrom() {
		return this.from;
	}

	public String getToAlias() {
		return this.toAlias;
	}

	/**
	 * Entrega el valor de to.
	 * 
	 * @return to.
	 */
	public String getTo() {
		return this.to;
	}

	/**
	 * Entrega el valor de to.
	 * 
	 * @return to.
	 */
	public Object getValue() {
		return this.value;
	}

	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}

	/**
	 * Fija el valor de from.
	 * 
	 * @param from .
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	public void setToAlias(String toAlias) {
		this.toAlias = toAlias;
	}

	/**
	 * Fija el valor de to.
	 * 
	 * @param to .
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Fija el valor de to.
	 * 
	 * @param to .
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Transforma una dependencia en un nodo XML.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Node toNode() throws Exception {
		Node node = XmlHelper.createNode("DEP");
		if (this.fromAlias != null) {
			XmlHelper.addAttribute(node, "aliasDesde", this.fromAlias);
		}
		XmlHelper.addAttribute(node, "desde", this.from);
		if (this.toAlias != null) {
			XmlHelper.addAttribute(node, "aliasHacia", this.toAlias);
		}
		XmlHelper.addAttribute(node, "hacia", this.to);
		if (this.value != null) {
			XmlHelper.addAttribute(node, "val", this.value);
		}
		return node;
	}

	@Override
	public String toString() {
		if (this.fromAlias != null || toAlias != null) {
			return "Dependence(" + this.fromAlias + "." + this.from + "="
					+ (this.value != null ? "'" + this.value + "'" : this.toAlias + "." + this.to) + ")";
		} else {
			return "Dependence(" + this.from + "=" + (this.value != null ? "'" + this.value + "'" : this.to) + ")";
		}
	}

}
