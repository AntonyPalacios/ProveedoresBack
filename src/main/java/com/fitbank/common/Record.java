package com.fitbank.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.lang.StringUtils;

/**
 * Registros pertenecientes a una tabla.
 * 
 * @author Fitbank
 * @version 2.0
 */
public class Record extends HashMap<String, Field> implements Serializable, Cloneable {

    /**
     * Version del registro para el manejo de optimistic locking.
     */
    private Integer           version;

    private Object            parent;

    /**
     * Numero de registro
     */
    private Integer           number;

    // FIXME corregir esto en false donde se cree un record que no sea de la
    // base
    private boolean           databaseRecord   = true;

    private static final long serialVersionUID = 1L;

    // private List<Criteria> criterias = new ArrayList<Criteria>();
    private List<Field>       fields           = new ArrayList<Field>();

    /**
     * Crea una nueva instancia de Record
     */
    public Record() {
    }

    public Record(int pNumber) {
        this.setNumber(pNumber);
    }

    /**
     * Crea una nueva instancia de registro dado un Nodo de un documento XML.
     * 
     * @param pNode
     *            Nodo de referencia.
     * @throws Exception
     */
    public Record(Node pNode) throws Exception {
        NodeList nl = ((Element) pNode).getChildNodes();
        this.version = XmlHelper.getIntegerValueByAttribute(pNode, "version");
        this.number = XmlHelper.getIntegerValueByAttribute(pNode, "numero");
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Field field = new Field(n);
            this.addField(field);
        }
    }

    /**
     * Adiciona un campo al registro.
     */
    public void addField(Field pField) {
        pField.setParent(this);
        this.fields.add(pField);
        super.put(pField.getName(), pField);

    }

    /**
     * Clona una registro.
     */
    public Record cloneMe() throws CloneNotSupportedException {
        Record record = (Record) super.clone();
        record.fields = new ArrayList<Field>();
        for (Field obj : this.fields) {
            record.fields.add(obj.cloneMe());
        }
        return record;
    }

    /**
     * Entrega un campo dado un ejemplo. No compara valores nulos (o en blanco).
     * 
     * @param pTableFieldName
     *            tabla.campo a buscar
     * @return
     */
    public Field findField(Field pField) {
        for (Field field : this.fields) {
            if (StringUtils.isNotBlank(pField.getAlias()) && !pField.getAlias().equals(field.getAlias())) {
                continue;
            }
            if (StringUtils.isNotBlank(pField.getName()) && !pField.getName().equalsIgnoreCase(field.getName())) {
                continue;
            }
            if (pField.getValue() != null && !pField.getValue().equals(field.getValue())) {
                continue;
            }
            if (!pField.getDependencies().isEmpty()) {
                if (pField.getDependencies().size() != field.getDependencies()
                        .size()) {
                    continue;
                }
                int iguales = 0;
                for (Dependence dependency : pField.getDependencies()) {
                    for (Dependence dependency2 : field.getDependencies()) {
                        if (!equals(dependency, dependency2)) {
                            continue;
                        }
                        iguales++;
                    }
                }
                if (iguales != pField.getDependencies().size()) {
                    continue;
                }
            }
            return field;
        }

        this.addField(pField);

        return pField;
    }

    /**
     * Compara solo campos que no estan null o en blanco.
     * @param dependency
     * @param dependency2
     * @return
     */
    private boolean equals(Dependence dependency, Dependence dependency2) {
        if (StringUtils.isNotBlank(dependency.getFrom()) && !dependency.getFrom().equals(dependency2.getFrom())) {
            return false;
        }
        if (StringUtils.isNotBlank(dependency.getFromAlias()) && !dependency.getFromAlias().equals(dependency2.getFromAlias())) {
            return false;
        }
        if (StringUtils.isNotBlank(dependency.getTo()) && !dependency.getTo().equals(dependency2.getTo())) {
            return false;
        }
        if (StringUtils.isNotBlank(dependency.getToAlias()) && !dependency.getToAlias().equals(dependency2.getToAlias())) {
            return false;
        }
        if (dependency.getValue() != null && !dependency.getValue().equals(dependency2.getValue())) {
            return false;
        }
        return true;
    }

    /**
     * Entrega un campo dado un ejemplo.
     * 
     * @param pTableFieldName
     *            tabla.campo a buscar
     * @return
     */
    public Field findFieldByExample(Field pField) {
        Field field;
        if (StringUtils.isBlank(pField.getAlias())) {
            field = this.findFieldByName(pField.getName());
        } else {
            field = this.findFieldByAlias(pField.getAlias(), pField.getName());
        }

        if (field == null) {
            this.addField(pField);
            field = pField;
        }
        return field;
    }

    /**
     * Entrega un campo dado el nombre.
     * 
     * @param pTableFieldName
     *            tabla.campo a buscar
     * @return
     */
    public Field findFieldByName(String pTableFieldName) {
        Field field = null;
        for (Field obj : this.fields) {
            if (obj.getRealName().compareToIgnoreCase(pTableFieldName.replaceAll("\\+", ".").replaceAll(":", ".")) == 0) {
                field = obj;
                break;
            }
        }
        if (field == null) {
            for (Field obj : this.fields) {
                String name = obj.getRealName();
                if (name.indexOf('.') > 0) {
                    name = name.substring(name.indexOf('.') + 1);
                }
                if (name.compareToIgnoreCase(pTableFieldName) == 0) {
                    field = obj;
                    break;
                }
            }
        }
        if (field != null) {
            field.setParent(this);
        }
        return field;
    }

    public Field findFieldByAlias(String alias, String pTableFieldName) {
        for (Field obj : this.fields) {
            if (obj.getAlias().equals(alias) && obj.getName().equalsIgnoreCase(pTableFieldName)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Buesca un Campo dentro del registro en el caso de no encontrarlo lo crea
     * 
     * @param pName
     *            Nombre del Campo.
     * @return La referencia al Campo
     */
    public Field findFieldByNameCreate(String pName) {
        Field f = this.findFieldByName(pName);
        if (f == null) {
            f = new Field(pName);
            this.addField(f);
        }
        return f;
    }

    @Override
    public Field get(Object key) {
        Field f = this.findFieldByName((String) key);
        return (f == null) ? this.findFieldByNameCreate((String) key) : f;
    }

    public List<Field> getChangedFields() throws Exception {
        List<Field> chF = new ArrayList<Field>();
        for (Field field : this.fields) {
            if (field.isChanged()) {
                chF.add(field);
            }
        }
        return chF;
    }

    /**
     * Entrega el valor de fields.
     * 
     * @return fields.
     */
    public List<Field> getFields() {
        return this.fields;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Object getParent() {
        return this.parent;
    }

    public List<Field> getPrimaryKeysList() {
        List<Field> pk = new ArrayList<Field>();
        for (Field field : this.fields) {
            if (field.isPrimaryKey()) {
                pk.add(field);
            }
        }
        return pk;
    }

    public Object getValue(String pName) {
        return this.findFieldByNameCreate(pName).getValue();
    }

    /**
     * Entrega el valor de version.
     * 
     * @return version.
     */
    public Integer getVersion() {
        return this.version;
    }

    public boolean hasData() {
        for (Field field : this.getFields()) {
            if (field.hasData()) {
                return true;
            }
        }
        return false;
    }

    public boolean isChanged() throws Exception {
        return this.getChangedFields().size() > 0;
    }

    public boolean isDatabaseRecord() {
        return this.databaseRecord;
    }

    @Override
    public Field put(String key, Field pField) {
        this.fields.add(pField);
        return super.put(pField.getName(), pField);
    }

    public void setDatabaseRecord(boolean nuevo) {
        this.databaseRecord = nuevo;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public void setValue(String pName, Object pValue) {
        this.findFieldByNameCreate(pName).setValue(pValue);
    }

    /**
     * Fija el valor de version.
     * 
     * @param pVersion
     *            .
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     * Transforma un registro en un nodo XML.
     * 
     * @return node
     * @throws Exception
     */
    public Node toNode() throws Exception {
        Node node = XmlHelper.createNode("REG");
        if (this.version != null) {
            XmlHelper.addAttribute(node, "version", this.version);
        }
        if (this.number != null) {
            XmlHelper.addAttribute(node, "numero", this.number);
        }
        for (Field obj : this.fields) {
            XmlHelper.appendChild(node, obj.toNode());
        }
        return node;
    }

    @Override
    public String toString() {
        String data = "";
        List<Field> pk = new ArrayList<Field>();
        try {
            pk = this.getPrimaryKeysList();
            if (!pk.isEmpty()) {
                for (Field field : pk) {
                    data += field.getName() + "=" + field.getValue() + " ";
                }

            } else {
                data = "Sin clave primaria";
            }
        } catch (Exception e) {
            data = "Sin clave primaria";
        }
        String sFields = "";
        for (String key : this.keySet()) {
            sFields += " " + key;
        }
        return "Record (" + data + ")" + sFields;
    }
}
