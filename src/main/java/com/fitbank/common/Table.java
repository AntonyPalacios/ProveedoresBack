package com.fitbank.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.*;

public class Table extends HashMap<String, Object> implements Serializable {

    /**
	 * 
	 */
    private static final long   serialVersionUID  = 2L;

    /**
     * Indicador de si la tabla tiene más páginas
     */
    private String              hasMorePages      = "0";

    private Object              parent;

    /**
     * Nombre de la tabla.
     */
    private String              name;

    /**
     * Alias de la tabla.
     */
    private String              alias;

    /**
     * Numero de paga utilizada en paginamiento.
     */
    private Integer             pageNumber;

    /**
     * Numero de registros por pagina.
     */
    private Integer             requestedRecords;

    /**
     * Registro seleccionado en la tabla, util en consultas maestro detalle.
     */
    private Integer             currentRecord     = 0;

    /**
     * Indica si la Tabla genera una transacciï¿½n financiera.
     */
    private boolean             financial         = false;

    /**
     * Indica si la Tabla debe ser procesada.
     */
    private boolean             readonly          = false;

    /**
     * Indica si la consulta de la tabla es especial.
     */
    private boolean             special           = false;

    private boolean             mutirecord        = true;

    private int                 iBloque           = 0;

    /**
     * Lista de criterios asociados a la tabla.
     */
    private List<Join>          joins             = new ArrayList<Join>();

    /**
     * Lista de criterios asociados a la tabla.
     */
    private List<Criterion>     criteria          = new ArrayList<Criterion>();

    /**
     * Lista de registros pertenecientes a la tabla.
     */
    // private Map<Integer, Record> records = new LinkedHashMap<Integer,
    // Record>();
    private List<Record>        records           = new ArrayList<Record>();

    private Record              structure;

    /**
     * Lista de dependencias de otras tablas.
     */
    private List<Dependence>    dependencies      = new ArrayList<Dependence>();

    private final static String DATA_KEY          = "_data";

    private final static String RECORD_PREFIX_KEY = "_record";

    /**
     * Crea una nueva instancia de Table dado un nodo de un Documento XML.
     * 
     * @param pNode
     *            Nodo de
     * @throws Exception
     */
    public Table(Node pNode) throws Exception {
        this.name = XmlHelper.getStringValueByAttribute(pNode, "name").toUpperCase();
        this.alias = XmlHelper.getStringValueByAttribute(pNode, "alias");
        this.hasMorePages = XmlHelper.getStringValueByAttribute(pNode, "mpg");
        if (this.hasMorePages == null) {
            this.hasMorePages = "0";
        }
        try {
            this.iBloque = XmlHelper.getIntegerValueByAttribute(pNode, "blq");
        } catch (Exception e) {
        }
        this.pageNumber = XmlHelper.getIntegerValueByAttribute(pNode, "npg");
        this.requestedRecords = XmlHelper.getIntegerValueByAttribute(pNode, "nrg");
        this.currentRecord = XmlHelper.getIntegerValueByAttribute(pNode, "ract");
        this.financial = XmlHelper.getBooleanValueByAttribute(pNode, "financial");
        this.readonly = XmlHelper.getBooleanValueByAttribute(pNode, "readonly");
        this.special = XmlHelper.getBooleanValueByAttribute(pNode, "special");
        this.completeJoins(pNode);
        this.completeCriteria(pNode);
        this.completeRecords(pNode);
        this.completeDependencies(pNode);
        List<Record> data = new ArrayList<Record>();
        data.addAll(this.records);
        this.put(DATA_KEY, data);
    }

    /**
     * Construye una tabla a partir de su definiciï¿½n en XML
     * 
     * @param pXMLData
     *            XML que contiene la definiciï¿½n de la Tabla
     * @throws Exception
     */
    public Table(String pXMLData) throws Exception {
        this(new XMLParser(pXMLData).findNode("/TBL"));
    }

    /**
     * Crea una instancia de Table.
     */
    public Table(String pName, String pAlias) {
        this.name = pName.toUpperCase();
        this.alias = pAlias;
        List<Record> data = new ArrayList<Record>();
        data.addAll(this.records);
        this.put(DATA_KEY, data);
    }

    public void addJoin(Join pJoin) {
        this.joins.add(pJoin);
    }

    /**
     * Adiciona un criterio a la tabla.
     * 
     * @param pCriterion
     *            .
     */
    public void addCriterion(Criterion pCriterion) {
        this.criteria.add(pCriterion);
        this.put(pCriterion.getName(), pCriterion);
    }

    /**
     * Fija el valor de dependences.
     * 
     * @param pDependence
     *            .
     */
    public void addDependence(Dependence pDependence) {
        this.dependencies.add(pDependence);
    }

    /**
     * Fija el valor de records.
     * 
     * @param pRecord
     *            .
     */
    public void addRecord(Record pRecord) {
        if (pRecord.getNumber() == null) {
            int max = 0;
            for (Record record : this.getRecords()) {
                max = Math.max(record.getNumber() + 1, max);
            }
            pRecord.setNumber(max);
        }
        pRecord.setParent(this);
        // records.add(pRecord.getNumber(), pRecord);
        this.records.add(pRecord);
        List<Record> data = new ArrayList<Record>();
        data.addAll(this.records);
        this.put(DATA_KEY, data);
    }

    public void cleanCriteria() {
        List<Criterion> vol = new ArrayList<Criterion>();
        for (Criterion cri : this.criteria) {
            if (cri.isInternal()) {
                vol.add(cri);
            }
        }
        for (Criterion criterion : vol) {
            this.criteria.remove(criterion);
        }
    }

    public void clearEmptyRecords() {
        List<Record> aux = new ArrayList<Record>();
        for (Record record : this.records) {
            if (record.hasData()) {
                aux.add(record);
            }
        }
        this.clearRecords();
        for (Record record : aux) {
            this.addRecord(record);
        }
    }

    public void clearRecords() {
        this.records.clear();
        List<Record> data = new ArrayList<Record>();
        data.addAll(this.records);
        this.put(DATA_KEY, data);
    }

    public void clearUnchangedRecords() {
        List<Record> aux = new ArrayList<Record>();
        for (Record record : this.records) {
            try {
                if (record.isChanged()) {
                    aux.add(record);
                }
            } catch (Exception e) {
                aux.add(record);
            }
        }
        this.clearRecords();
        for (Record record : aux) {
            this.addRecord(record);
        }
    }

    private void completeJoins(Node pNode) throws Exception {
        NodeList nl = ((Element) pNode).getElementsByTagName("JOIN");
        for (int i = 0; i < nl.getLength(); i++) {
            Join join = new Join(nl.item(i));
            this.addJoin(join);
        }
    }

    /**
     * Completa criterios de busqueda de una tabla.
     * 
     * @param pNode
     *            Nodo de referencia.
     * @throws Exception
     */
    private void completeCriteria(Node pNode) throws Exception {
        NodeList nl = ((Element) pNode).getElementsByTagName("CRI");
        for (int i = 0; i < nl.getLength(); i++) {
            Criterion cri = new Criterion(nl.item(i));
            this.addCriterion(cri);
        }
    }

    /**
     * Completa dependencias de una tabla.
     * 
     * @param pNode
     *            Nodo de referencia.
     * @throws Exception
     */
    private void completeDependencies(Node pNode) throws Exception {
        NodeList nl = ((Element) pNode).getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().compareTo("DEP") != 0) {
                continue;
            }
            Dependence dep = new Dependence(nl.item(i));
            this.addDependence(dep);
        }
    }

    /**
     * Complete registros de una tabla.
     * 
     * @param pNode
     *            Nodo de referencia.
     * @throws Exception
     */
    private void completeRecords(Node pNode) throws Exception {
        NodeList nl = ((Element) pNode).getElementsByTagName("REG");
        for (int i = 0; i < nl.getLength(); i++) {
            Record record = new Record(nl.item(i));
            this.addRecord(record);
        }
    }

    /**
     * Entrega un criterio dado un ejemplo. No compara valores nulos (o en blanco).
     *
     * @param pTableFieldName
     *            tabla.campo a buscar
     * @return
     */
    public Criterion findCriterion(Criterion pCriterion) {
        for (Criterion criterion : this.criteria) {
            //order,condition
            if (StringUtils.isNotBlank(pCriterion.getAlias()) && !pCriterion.getAlias().equals(criterion.getAlias())) {
                continue;
            }
            if (StringUtils.isNotBlank(pCriterion.getName()) && !pCriterion.getName().equalsIgnoreCase(criterion.getName())) {
                continue;
            }
            if (pCriterion.getValue() != null && !pCriterion.getValue().equals(criterion.getValue())) {
                continue;
            }
            if (pCriterion.getOrder() != null && !pCriterion.getOrder().equals(criterion.getOrder())) {
                continue;
            }
            if (StringUtils.isNotBlank(pCriterion.getCondition()) && !pCriterion.getCondition().equalsIgnoreCase(criterion.getCondition())) {
                continue;
            }
            return criterion;
        }

        this.addCriterion(pCriterion);

        return pCriterion;
    }

    /**
     * Busca un criterio dado un ejemplo.
     * 
     * @param pCriteriaName
     * @return criteria.
     */
    public Criterion findCriterionByExample(Criterion pCriterium) {
        Criterion criterion;
        if (StringUtils.isBlank(pCriterium.getAlias())) {
            criterion = this.findCriterionByName(pCriterium.getName());
        } else {
            criterion = this.findCriterionByAlias(pCriterium.getAlias(), pCriterium.getName(), pCriterium.getOrder());
        }

        if (criterion == null) {
            this.addCriterion(pCriterium);
            criterion = pCriterium;
        }

        return criterion;
    }

    public Criterion findCriterionByAlias(String alias, String pCriteriaName) {
        for (Criterion criterium : this.criteria) {
            if (criterium.getAlias().equals(alias) && criterium.getName().equalsIgnoreCase(pCriteriaName)) {
                return criterium;
            }
        }
        return null;
    }

    public Criterion findCriterionByAlias(String alias, String pCriteriaName, Integer order) {
        for (Criterion criterium : this.criteria) {
            if (criterium.getAlias().equals(alias) && criterium.getName().equalsIgnoreCase(pCriteriaName)
                    && (criterium.getOrder() == null && order == null || criterium.getOrder() == order)) {
                return criterium;
            }
        }
        return null;
    }

    /**
     * Busca un criterio por nombre.
     * 
     * @param pCriteriaName
     * @return criteria.
     */
    public Criterion findCriterionByName(String pCriteriaName) {
        for (Criterion criteria : this.criteria) {
            if (criteria.getName().compareTo(pCriteriaName) == 0) {
                return criteria;
            }
        }
        return null;
    }

    /**
     * Entrega el valor de records.
     * 
     * @return records.
     */
    public Record findRecordByExample(final Record pRecord) {
        Record record = (Record) CollectionUtils.find(this.records, new Predicate() {
            public boolean evaluate(Object object) {
                return ((Record) object).getNumber().equals(pRecord.getNumber());
            }
        });

        if (record == null) {
            record = pRecord;
            this.addRecord(pRecord);
        }

        return record;
    }

    /**
     * Entrega el valor de records.
     * 
     * @return records.
     */
    public Record findRecordByNumber(int pRecordNumber) {
        return findRecordByExample(new Record(pRecordNumber));
    }

    /**
     * Entrega List de Record por nombre del campo y el valor.
     * 
     * @param pnameField
     *            .
     * @param pvalue
     *            .
     * @return the List Record
     */

    public List<Record> findRecordsbyValue(String pnameField, Object pvalue) {
        Field field = null;
        Object value = null;
        List<Record> listrecord = new ArrayList<Record>();
        for (Record record : this.records) {
            field = record.findFieldByName(pnameField);
            value = field != null ? field.getValue() : null;
            if ((value != null) && (value.toString().compareTo(pvalue.toString()) == 0)) {
                listrecord.add(record);
            }
        }

        return listrecord;
    }

    public String getTableNameByAlias(String pAlias) {
        if (getAlias().equals(pAlias)) {
            return getName();
        }
        
        for (Join join : getJoins()) {
            if (join.getAlias().equals(pAlias)) {
                return join.getName();
            }
        }
        
        return null;
    }
    
    @Override
    public Object get(Object key) {
        String sKey = (String) key;
        if (sKey.compareTo("_alias") == 0) {
            return this.alias;
        }
        if (sKey.compareTo("_name") == 0) {
            return this.name;
        }
        if (sKey.compareTo("_structure") == 0) {
            return this.getLabeledFields();
        }
        if (sKey.compareTo("_realData") == 0) {
            return this.getRealData();
        }
        if (sKey.compareTo("_realSize") == 0) {
            return this.getRealData().size();
        }
        if (sKey.compareTo("_structureSize") == 0) {
            try {
                return this.structure.getFields().size();
            } catch (Exception e) {
                return 0;
            }
        }
        if (sKey.compareTo("_structureWidth") == 0) {
            return this.getStructureWidth();
        }
        if (sKey.compareTo("_size") == 0) {
            return this.getRecordCount();
        }
        if (sKey.compareTo("hasMorePages") == 0) {
            return this.getHasMorePages();
        }
        if (sKey.compareTo("pageNumber") == 0) {
            return this.getPageNumber();
        }
        if (sKey.compareTo("recordCount") == 0) {
            return this.getRecordCount();
        }
        return super.get(key);
    }

    /**
     * Entrega el valor de alias.
     * 
     * @return alias.
     */
    public String getAlias() {
        return this.alias;
    }

    public List<Join> getJoins() {
        return this.joins;
    }

    /**
     * Entrega el valor de criterias.
     * 
     * @return criterias.
     */
    public List<Criterion> getCriteria() {
        return this.criteria;
    }

    /**
     * Entrega el valor de currentRecord.
     * 
     * @return currentRecord.
     */
    public Integer getCurrentRecord() {
        return this.currentRecord;
    }

    public List<String> getDataTables() throws Exception {
        List<String> l = new ArrayList<String>();
        if (this.getRecordCount() > 0) {
            Record rec = this.getRecords().iterator().next();
            List<Field> fields = rec.getFields();
            Map<String, String> aux = new HashMap<String, String>();
            for (Field field : fields) {
                if ((field.getDependencies() != null) && (field.getDependencies().size() > 0)) {
                    continue;
                }
                String name = field.getRealName();
                if (name.indexOf('.') > 0) {
                    StringTokenizer st = new StringTokenizer(name, ".");
                    name = (String) st.nextElement();
                    String sField = (String) st.nextElement();
                    if ((sField.indexOf('_') > 0) && (sField.compareTo("CPERSONA_COMPANIA") != 0)) {
                        name += sField.substring(sField.indexOf('_'));
                    }
                } else {
                    name = this.getName();
                }
                aux.put(name.toUpperCase(), name);
            }
            Iterator<String> it = aux.keySet().iterator();
            l.add(this.name);
            while (it.hasNext()) {
                String tname = it.next();
                if (tname.compareTo(this.name) == 0) {
                    continue;
                }
                l.add(tname);
            }
        }
        return l;
    }

    /**
     * Entrega el valor de dependences.
     * 
     * @return dependences.
     */
    public List<Dependence> getDependencies() {
        return this.dependencies;
    }

    /**
     * Entrega el valor de hasMorePages.
     * 
     * @return hasMorePages.
     */
    public String getHasMorePages() {
        return this.hasMorePages;
    }

    public int getIBloque() {
        return this.iBloque;
    }

    public Iterable<Field> getLabeledFields() {
        List<Field> list = new ArrayList<Field>();
        if (this.structure != null) {
            Iterable<Field> f = this.structure.getFields();
            Map<Integer, Field> m = new HashMap<Integer, Field>();
            for (Field field : f) {
                field.setParent(this);
                if ((field.getLabel() != null) && (field.getOrder() > -1)) {
                    m.put(field.getOrder(), field);
                }
            }
            List<Integer> key = new ArrayList<Integer>();
            for (Integer k : m.keySet()) {
                key.add(k);
            }
            Collections.sort(key);
            for (Integer k : key) {
                list.add(m.get(k));
            }
        }
        return list;
    }

    /**
     * Entrega el valor de name.
     * 
     * @return name.
     */
    public String getName() {
        return this.name.toUpperCase();
    }

    /**
     * Entrega el valor de pageNumber.
     * 
     * @return pageNumber.
     */
    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Object getParent() {
        return this.parent;
    }

    @SuppressWarnings("unchecked")
    public List<Record> getRealData() {
        List<Record> data = (List<Record>) this.get(DATA_KEY);
        List<Record> real = new ArrayList<Record>();
        real.addAll(data);
        if ((this.pageNumber != null) && (this.requestedRecords != null)) {
            if ((this.pageNumber > 0) && (this.requestedRecords > real.size()) && (this.structure != null)) {
                for (int i = real.size(); i < this.requestedRecords; i++) {
                    Record r = new Record();
                    List<Field> f = this.structure.getFields();
                    for (Field field : f) {
                        r.findFieldByNameCreate(field.getName());
                    }
                    this.addRecord(r);
                }
            }
        }

        return (List<Record>) this.get(DATA_KEY);
    }

    /**
     * Entrega el numero de records.
     * 
     * @return numero de records.
     */
    public int getRecordCount() {
        return this.records.size();
    }

    /**
     * Entrega el valor de records.
     * 
     * @return records.
     */
    public Iterable<Record> getRecords() {
        return this.records;
    }

    /**
     * Entrega el valor de recordCount.
     * 
     * @return recordCount.
     */
    public Integer getRequestedRecords() {
        return this.requestedRecords;
    }

    public Record getStructure() {
        if (this.structure != null) {
            this.structure.setParent(this);
        }
        return this.structure;
    }

    public Integer getStructureWidth() {
        Integer w = 0;
        try {
            List<Field> fs = this.structure.getFields();
            for (Field field : fs) {
                if (!field.isHidden()) {
                    w += field.getSize();
                }
            }
        } catch (Exception e) {
        }
        return w;
    }

    public boolean isChanged() throws Exception {
        boolean change = false;
        if (this.readonly || this.financial) {
            return false;
        }
        for (Record rcd : this.records) {
            change = rcd.isChanged();
            if (change) {
                break;
            }
        }
        return change;
    }

    /**
     * Entrega el valor de financial
     * 
     * @return financial
     */
    public boolean isFinancial() {
        return this.financial;
    }

    public boolean isMutirecord() {
        return this.mutirecord;
    }

    /**
     * Entrega el valor de readonly
     * 
     * @return the readonly
     */
    public boolean isReadonly() {
        return this.readonly;
    }

    public boolean isSpecial() {
        return this.special;
    }

    /**
     * Indicador de si la tabla debe actualizar.
     * 
     * @return Bandera
     */
    public boolean isUpdatable() {
        return this.alias.toLowerCase().indexOf("z__") != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(String key, Object value) {
        if (key.compareTo(DATA_KEY) == 0) {
            Collection<Object> col = (Collection<Object>) value;
            Object[] arr = col.toArray();
            for (int i = 0; i < arr.length; i++) {
                super.put(RECORD_PREFIX_KEY + i, arr[i]);
            }
        }
        return super.put(key, value);
    }

    /**
     * Elimina un registro de la Tabla
     * 
     * @param pIndex
     *            indice del Registro
     */
    public void removeRecord(int pIndex) {
        this.records.remove(pIndex);
        List<Record> data = new ArrayList<Record>();
        data.addAll(this.records);
        this.put(DATA_KEY, data);
    }

    /**
     * Fija el valor de alias.
     * 
     * @param alias
     *            .
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Fija el valor de currentRecord.
     * 
     * @param currentRecord
     *            .
     */
    public void setCurrentRecord(Integer currentRecord) {
        this.currentRecord = currentRecord;
    }

    /**
     * Fija el valor de financial
     * 
     * @param financial
     */
    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

    /**
     * Fija el valor de hasMorePages.
     * 
     * @param pHasMorePages
     *            .
     */
    public void setHasMorePages(String pHasMorePages) {
        this.hasMorePages = pHasMorePages;
    }

    public void setIBloque(int bloque) {
        this.iBloque = bloque;
    }

    public void setMutirecord(boolean mutirecord) {
        this.mutirecord = mutirecord;
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
     * Fija el valor de pageNumber.
     * 
     * @param pageNumber
     *            .
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    /**
     * Fija el valor de readonly
     * 
     * @param readonly
     *            the readonly to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * Fija el valor de recordCount.
     * 
     * @param recordCount
     *            .
     */
    public void setRequestedRecords(Integer recordCount) {
        this.requestedRecords = recordCount;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public void setStructure(Record structure) {
        this.structure = structure;
    }

    /**
     * Transforma un campo en un nodo XML.
     * 
     * @return
     * @throws Exception
     */
    public Node toNode() throws Exception {
        Node node = XmlHelper.createNode("TBL");
        XmlHelper.addAttribute(node, "name", this.name);
        XmlHelper.addAttribute(node, "alias", this.alias);
        if (this.pageNumber != null) {
            XmlHelper.addAttribute(node, "npg", this.pageNumber);
        }
        if (this.requestedRecords != null) {
            XmlHelper.addAttribute(node, "nrg", this.requestedRecords);
        }
        if (this.currentRecord != null) {
            XmlHelper.addAttribute(node, "ract", this.currentRecord);
        }
        if (this.hasMorePages != null) {
            XmlHelper.addAttribute(node, "mpg", this.hasMorePages);
        }
        XmlHelper.addAttribute(node, "blq", this.iBloque);
        if (this.isFinancial()) {
            XmlHelper.addAttribute(node, "financial", "true");
        } else {
            XmlHelper.addAttribute(node, "financial", "false");
        }
        if (this.isReadonly()) {
            XmlHelper.addAttribute(node, "readonly", "true");
        } else {
            XmlHelper.addAttribute(node, "readonly", "false");
        }
        if (this.isSpecial()) {
            XmlHelper.addAttribute(node, "special", "true");
        } else {
            XmlHelper.addAttribute(node, "special", "false");
        }
        for (Join obj : this.joins) {
            XmlHelper.appendChild(node, obj.toNode());
        }
        for (Criterion obj : this.criteria) {
            XmlHelper.appendChild(node, obj.toNode());
        }
        for (Record obj : this.getRecords()) {
            XmlHelper.appendChild(node, obj.toNode());
        }
        for (Dependence obj : this.dependencies) {
            XmlHelper.appendChild(node, obj.toNode());
        }

        return node;
    }

    @Override
    public String toString() {
        return "Table(" + this.name + "[" + this.alias + "])";
    }
}
