package com.fitbank.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serializer.ToXMLStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Clase utilitaria para manejo de xml.
 * 
 */

@Slf4j
public class XmlHelper {

    private static SimpleDateFormat sdfdate      = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat sdftimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Adiciona un atributo a un nodo.
     * 
     * @param pNode
     *            Nombre del nodo.
     * @param pName
     *            Nombre del atributo.
     * @param pValue
     *            Valor del atributo.
     */
    public static void addAttribute(Node pNode, String pName, Object pValue) throws Exception {
        ((Element) pNode).setAttribute(pName, pValue.toString());
    }

    /**
     * Adiciona un hijo al nodo.
     * 
     * @param pParent
     *            Nodo padre.
     * @param pChild
     *            Nodo hijo.
     * @throws Exception
     */
    public static void appendChild(Node pParent, Node pChild) throws Exception {
        pParent.appendChild(pParent.getOwnerDocument().importNode(pChild, true));
    }

    /**
     * Adiciona un tag a un nodo.
     * 
     * @param pParent
     *            Nodo padre.
     * @param pChildName
     *            Nombre del nodo a crear.
     * @param pValue
     *            Valor del nodo a crear.
     * @throws Exception
     */
    public static void appendChild(Node pParent, String pChildName, Object pValue) throws Exception {
        Node child = XmlHelper.createNode(pChildName, pValue);
        pParent.appendChild(pParent.getOwnerDocument().importNode(child, true));
    }

    /**
     * Crea un nodo vacio.
     * 
     * @param pName
     *            Nombre del nodo.
     * @return node
     * @throws Exception
     */
    public static Node createNode(Document pDocument, String pName) throws Exception {

        Node node = pDocument.createElement(pName);
        return node;
    }

    /**
     * Crea un nodo con valor.
     * 
     * @param pName
     *            Nombre del nodo
     * @param pValue
     *            Valor del nodo.
     * @return node
     * @throws Exception
     */
    public static Node createNode(Document pDocument, String pName, Object pValue) throws Exception {
        Document d = pDocument;
        // Crea un nuevo nodo.
        Node node = XmlHelper.createNode(pName);
        // Crea un documento xml

        // Crea un nodo con valor sin nombre
        Node value;

        if (pValue == null) {
            value = null;
            log.info("El tag " + pName + " se esta poniendo valor nulo.");
        } else if (pValue instanceof char[]) {
            value = d.createCDATASection(new String((char[]) pValue));
        } else {
            value = d.createTextNode(pValue.toString());
        }
        // Imposta el nodo al nuevo documento.

        // Fija un valor al nodo.
        if (value != null) {
            XmlHelper.appendChild(node, value);
        }
        return node;
    }

    public static Node createNode(String pName) throws Exception {
        Document d = new DocumentImpl();
        return createNode(d, pName);
    }

    public static Node createNode(String pName, Object pValue) throws Exception {
        Document d = new DocumentImpl();
        return createNode(d, pName, pValue);
    }

    public static BigDecimal getBigDecimalValueByTag(Node pNode, String pTag) throws Exception {
        try {
            return new BigDecimal(getStringValueByTag(pNode, pTag));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static boolean getBooleanValueByAttribute(Node pNode, String pAttribute) throws Exception {
        boolean b = false;
        try {
            Element e = (Element) pNode;
            String attribute = e.getAttribute(pAttribute);
            if (attribute != null) {
                if ((attribute.compareTo("true") == 0) || (attribute.compareTo("1") == 0)) {
                    b = true;
                }
            }
            return b;
        } catch (NullPointerException e) {
            return b;
        }
    }

    public static Date getDateValueByTag(Node pNode, String pTag) throws Exception {
        try {
            return new Date(sdfdate.parse(getStringValueByTag(pNode, pTag)).getTime());
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Entrega el valor de un Atributo.
     * 
     * @param pNode
     * @param pAttribute
     * @return
     * @throws Exception
     */
    public static Integer getIntegerValueByAttribute(Node pNode, String pAttribute) throws Exception {
        try {
            return new Integer(getStringValueByAttribute(pNode, pAttribute));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer getIntegerValueByTag(Node pNode, String pTag) throws Exception {
        try {
            return new Integer(getStringValueByTag(pNode, pTag));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Entrega el valor de un Atributo.
     * 
     * @param pNode
     * @param pAttribute
     * @return
     * @throws Exception
     */
    public static String getStringValueByAttribute(Node pNode, String pAttribute) throws Exception {
        try {
            Element e = (Element) pNode;
            return e.getAttribute(pAttribute);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Entrega el valor de un Atributo como Enum.
     * 
     * @param pNode
     * @param pAttribute
     * @param pEnumClass
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEnumValueByAttribute(Node pNode, String pAttribute, Class<T> pEnumClass) throws Exception {
        try {
            return (T) Enum.valueOf((Class<? extends Enum>)pEnumClass, getStringValueByAttribute(pNode, pAttribute));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Entrega el valor de un tag.
     * 
     * @param pNode
     * @param pTag
     * @return
     * @throws Exception
     */
    public static String getStringValueByTag(Node pNode, String pTag) throws Exception {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0).getFirstChild().getNodeValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static Timestamp getTimestampValueByTag(Node pNode, String pTag) throws Exception {
        try {
            return new Timestamp(sdftimestamp.parse(getStringValueByTag(pNode, pTag)).getTime());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            Node node = XmlHelper.createNode("DEP", "hola mundo ï¿½&ï¿½ <&> ï¿½&ï¿½");
            XmlHelper.addAttribute(node, "name", "valor de atributo ï¿½&");
            String data = XmlHelper.nodeToString(node);
            log.info(node + "  data " + data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Entega un string dado un Nodo.
     * 
     * @param pNode
     *            Nodo a serializar.
     * @return
     * @throws Exception
     */
    public static String nodeToString(Node pNode) throws Exception {
        String data = "";
        ToXMLStream dser = (ToXMLStream) (new ToXMLStream()).asDOMSerializer();

        StringWriter sw = new StringWriter();
        dser.setWriter(sw);
        dser.serialize(pNode);
        String element = sw.toString();
        data = element.substring(element.indexOf(">") + 1);
        return data;
    }

    public XmlHelper() {
    }

}
