package com.fitbank.common;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implemneta el parser de un mensaje XML.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class XMLParser {
    private static DOMParser parser = new DOMParser();

    /**
     * Documento xml resultado de parsear el string de entrada.
     */
    private Document         document;

    public XMLParser(Document pDocument) throws Exception {
        this.document = pDocument;
    }

    /**
     * Crea un nueva instancia de XMLParser, parsea el documento y encera el
     * document.
     * 
     * @param pXML
     * @throws Exception
     */
    public XMLParser(String pXML) throws Exception {
        parser.parse(new InputSource(new StringReader(pXML)));
        this.document = parser.getDocument();
    }

    /**
     * Busca un nodo relativo al nodo enviado.
     * 
     * @param pNode
     *            Nodo base.
     * @param pXpath
     *            ruta a buscar en el nodo.
     * @return
     * @throws Exception
     */
    public Node findNode(Node pNode, String pXpath) throws Exception {
        NodeIterator nit = this.findNodeIterator(pNode, pXpath);
        return nit.nextNode();
    }

    /**
     * Busca un nodo en el documento dado el xpath.
     * 
     * @param pXpath
     *            Ruta a buscar para obtener un nodo.
     * @return node
     * @throws Exception
     */
    public Node findNode(String pXpath) throws Exception {
        NodeIterator nit = this.findNodeIterator(pXpath);
        return nit.nextNode();
    }

    /**
     * Obtiene un nuevo documento desde la ruta dada.
     * 
     * @param pNode
     *            Nodo base.
     * @param pXpath
     *            Ruta a buscar y crear un nodo.
     * @return
     * @throws Exception
     */
    public NodeIterator findNodeIterator(Node pNode, String pXpath) throws Exception {
        Document d = new DocumentImpl();
        return XPathAPI.selectNodeIterator(d.importNode(pNode, true), pXpath);
    }

    /**
     * Busca un nodo dentro del documento dado una ruta.
     * 
     * @param pXpath
     *            Ruta a buscar.
     * @return
     * @throws Exception
     */
    public NodeIterator findNodeIterator(String pXpath) throws Exception {
        return XPathAPI.selectNodeIterator(this.document, pXpath);
    }

    public Node getChildByTagName(Node pNode, String pTag) throws Exception {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag).item(0);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Node getChildByTagName(String pTag) throws Exception {
        return this.getChildByTagName(this.document.getDocumentElement(), pTag);
    }

    public List<Node> getChildNodes() throws Exception {
        return this.getChildNodes(null);
    }

    public List<Node> getChildNodes(Node pNode) throws Exception {
        NodeList nl = ((pNode == null) ? this.document : pNode).getChildNodes();
        List<Node> res = new ArrayList<Node>();
        if (nl != null) {
            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    res.add(n);
                }
            }
        }
        return res;
    }

    public NodeList getChildrenByTagName(Node pNode, String pTag) throws Exception {
        try {
            Element e = (Element) pNode;
            return e.getElementsByTagName(pTag);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /*
     * public static void main(String[] args) { try { FileInputStream fin=new
     * FileInputStream("d:/work/fitbank/message.xml"); byte b[]=new byte[9999];
     * int car=0; String data=""; do { car=fin.read(b); if(car>0){ data+=new
     * String(b,0,car); } } while (car>0); fin.close(); SimpleDateFormat sdf =
     * new SimpleDateFormat("ss.SSS"); long a = System.currentTimeMillis();
     * XMLParser p=new XMLParser(data); Detail det = new Detail(p); long t =
     * System.currentTimeMillis() - a; System.out.println("tiempo de busqueda "+
     * sdf.format(new Date(t))); a = System.currentTimeMillis();
     * System.out.println(det.toXml()); t = System.currentTimeMillis() - a;
     * System.out.println("tiempo de xml to xml "+ sdf.format(new Date(t))); }
     * catch (Exception e) { e.printStackTrace(); } }
     */

    public List<Node> getComplexChildren() throws Exception {
        return this.getComplexChildren(null);
    }

    public List<Node> getComplexChildren(Node pNode) throws Exception {
        List<Node> res = new ArrayList<Node>();
        List<Node> nl = this.getChildNodes(pNode);
        for (Node node : nl) {
            List<Node> nl1 = this.getChildNodes(node);
            if (!nl1.isEmpty()) {
                res.add(node);
            }
        }
        return res;
    }

    public Document getDocument() {
        return this.document;
    }

    public List<Node> getSingleChildren() throws Exception {
        return this.getSingleChildren(null);
    }

    public List<Node> getSingleChildren(Node pNode) throws Exception {
        List<Node> res = new ArrayList<Node>();
        List<Node> nl = this.getChildNodes(pNode);
        for (Node node : nl) {
            List<Node> nl1 = this.getChildNodes(node);
            if (nl1.size() < 1) {
                res.add(node);
            }
        }
        return res;
    }

    public String getValue(Node pNode) throws Exception {
        NodeList nl = ((pNode == null) ? this.document : pNode).getChildNodes();
        if (nl != null) {
            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                if (n.getNodeType() == Node.TEXT_NODE) {
                    return n.getNodeValue();
                }
            }
        }
        return null;
    }

    /**
     * Busca y entrega el valor de una ruta xpath, dado un nodo.
     * 
     * @param pNode
     *            Nodo relativo para la busqueda.
     * @param pXpath
     *            ruta a buscar.
     * @return value.
     * @throws Exception
     */
    public String getValue(Node pNode, String pXpath) throws Exception {
        Node node = this.findNode(pNode, pXpath);
        return (node.getNodeValue() != null) ? node.getNodeValue() : node.getFirstChild().getNodeValue();
    }

    /**
     * Busca y entrega el valor de una ruta.
     * 
     * @param pXpath
     *            rura a buscar.
     * @return value
     * @throws Exception
     */
    public String getValue(String pXpath) throws Exception {
        Node node = this.findNode(pXpath);
        return (node.getNodeValue() != null) ? node.getNodeValue() : node.getFirstChild().getNodeValue();
    }
}
