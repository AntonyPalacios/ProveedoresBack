package com.fitbank.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Join implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object parent;

    /**
     * Nombre de la tabla.
     */
    private String name;

    /**
     * Alias de la tabla.
     */
    private String alias;

    /**
     * Lista de dependencias de otras tablas.
     */
    private List<Dependence> dependencies = new ArrayList<Dependence>();

    /**
     * Crea una nueva instancia de Table dado un nodo de un Documento XML.
     * @param pNode Nodo del XML
     * @throws Exception
     */
    public Join(Node pNode) throws Exception {
        this.name = XmlHelper.getStringValueByAttribute(pNode, "name").toUpperCase();
        this.alias = XmlHelper.getStringValueByAttribute(pNode, "alias");
        this.completeDependencies(pNode);
    }

    /**
     * Construye una tabla a partir de su definicion en XML
     * @param pXMLData XML que contiene la definicion de la Tabla
     * @throws Exception
     */
    public Join(String pXMLData) throws Exception {
        this(new XMLParser(pXMLData).findNode("/JOIN"));
    }

    /**
     * Crea una instancia de Table.
     */
    public Join(String pName, String pAlias) {
        this.name = pName.toUpperCase();
        this.alias = pAlias;
    }

    /**
     * Fija el valor de dependences.
     * @param pDependence
     */
    public void addDependence(Dependence pDependence) {
        this.dependencies.add(pDependence);
    }

    /**
     * Completa dependencias de una tabla.
     * @param pNode Nodo de referencia.
     * @throws Exception
     */
    private void completeDependencies(Node pNode) throws Exception {
        NodeList nl = ((Element)pNode).getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().compareTo("DEP") != 0) {
                continue;
            }
            Dependence dep = new Dependence(nl.item(i));
            this.addDependence(dep);
        }
    }

    /**
     * Entrega el valor de alias.
     * @return alias.
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Entrega el valor de dependences.
     * @return dependences.
     */
    public List<Dependence> getDependencies() {
        return this.dependencies;
    }

    /**
     * Entrega el valor de name.
     * @return name.
     */
    public String getName() {
        return this.name.toUpperCase();
    }

    public Object getParent() {
        return this.parent;
    }

    /**
     * Fija el valor de alias.
     * @param alias.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Fija el valor de name.
     * @param name.
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    /**
     * Transforma un campo en un nodo XML.
     * @return Node
     * @throws Exception
     */
    public Node toNode() throws Exception {
        Node node = XmlHelper.createNode("JOIN");
        XmlHelper.addAttribute(node, "name", this.name);
        XmlHelper.addAttribute(node, "alias", this.alias);
        for (Dependence obj : this.dependencies) {
            XmlHelper.appendChild(node, obj.toNode());
        }
        return node;
    }

    @Override
    public String toString() {
        return "Join(" + this.name + "[" + this.alias + "])";
    }
}
