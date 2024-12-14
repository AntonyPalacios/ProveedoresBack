package com.fitbank.common;

import com.fitbank.uci.client.FitbankException;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;

/**
 * Clase utilitaria para conversiÃ³n de objeto y compaciÃ³n de objetos.
 * 
 * @version 1.10 31 Ago 2009
 * @author BANTEC Inc.
 */
@Slf4j
public class BeanManager {

    /**
     * UNCHECKED Constante para la anotaciÃ³n unchecked
     */
    private static final String UNCHECKED = "unchecked";

    public static boolean compareNull(Object value, Object value1) throws Exception {
        if (((value == null) && (value1 == null)) || !((value != null) && (value instanceof Comparable))
                || !((value1 != null) && (value1 instanceof Comparable))) {
            return true;
        }
        return false;
    }

    /**
     * Compara dos Objetos, si los objetos son iguales retorna true.
     * 
     * @param pObject
     *            Objeto uno
     * @param pOtherObject
     *            Objeto dos.
     * @return equal true si son iguales, false si no.
     * @throws Exception
     */

    @SuppressWarnings(BeanManager.UNCHECKED)
    public static boolean compareObject(Object pObject, Object pOtherObject) throws Exception {
        Field f[] = pObject.getClass().getDeclaredFields();
        boolean equal = true;
        for (Field obj : f) {
            if (BeanManager.filterFields(obj)) {
                continue;
            }
            Object value = BeanManager.getBeanAttributeValue(pObject, obj.getName());
            Object value1 = BeanManager.getBeanAttributeValue(pOtherObject, obj.getName());
            /*
             * if ((value == null) && (value1 == null)) { continue; }
             */
            if (BeanManager.compareNull(value, value1)) {
                continue;
            }
            /*
             * if (!((value1 != null) && (value1 instanceof Comparable))) {
             * continue; }
             */
            if ((((Comparable) value).compareTo(value1) != 0)) {
                return false;
            }
            /*
             * if ((value1 == null) && (value != null)) { return false; }
             */
            /*
             * if (((Comparable) value).compareTo(value1) != 0) { return false;
             * }
             */
        }
        return equal;
    }

    private static Date convertDate(String sValue) throws ParseException {
        String value = sValue;
        String d[] = value.split(" ");
        if (d.length == 2) {
            value = d[0];
        }
        return new Date(FormatDates.getInstance().getTransportDateFormat().parse(value.replaceAll("\\/", "-"))
                .getTime());
    }

    /**
     * Metodo Encargado de la conversiÃ³n del tipo de Dato de un Objeto
     * 
     * @param pValue
     *            Objeto que se quiere convertir.
     * @param pType
     *            Tipo de dato al que se quiere convertir.
     * @return Object Objeto ya convertido.
     * @throws Exception
     */
    public static <T> T convertObject(Object pValue, Class<T> pType) {
        try {
            return BeanManager.convertObject(pValue, pType, false);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * Metodo Encargado de la conversiÃ³n del tipo de Dato de un Objeto
     * 
     * @param pValue
     *            Objeto que se quiere convertir.
     * @param pType
     *            Tipo de dato al que se quiere convertir.
     * @param pUpper
     *            Si el tipo de dato es String si es que se devolvera en
     *            mayï¿½sculas la cadena.
     * @return Object Objeto ya convertido.
     * @throws Exception
     */
    public static <T> T convertObject(Object pValue, Class<T> pType, boolean pUpper) throws Exception {
        Object value = pValue;
        if (value == null) {
            return null;
        }
        if ((pType.getName().compareTo("java.lang.String") == 0) && pUpper) {
            value = value.toString().toUpperCase();
        }

        if ((value.getClass().getName().compareTo(pType.getName()) == 0)) {
            return (T) value;
        }
        if ((pType.getName().compareTo("java.lang.String") != 0) && (pValue.toString().compareTo("") == 0)) {
            return null;
        }

        try {
            T oVal = BeanManager.convertValueByType(value, pType);
            if (oVal != null) {
                return oVal;
            }
        } catch (ParseException ex) {
            throw new Error(ex);
        }

        return (T) value;
    }

    private static Timestamp convertTimestamp(String sValue) throws ParseException {
        String val = sValue;
        if (val.indexOf('.') < 0) {
            val += ".0";
        }
        String d[] = val.split(" ");
        if (d.length == 2) {
            val = d[0] + " " + d[1].replaceAll("-", ":");
        }
        try {
            Timestamp times = new Timestamp(FormatDates.getInstance().getTransportTimestampFormat().parse(val)
                    .getTime());
            String sTimestamp = FormatDates.getInstance().getTransportTimestampFormat().format(times);
            if (sTimestamp.compareTo(val) == 0) {
                return times;
            }
            return Timestamp.valueOf(val);
        } catch (Exception e) {
            return new Timestamp(FormatDates.getInstance().getTransportDateFormat().parse(val).getTime());
        }
    }

    private static Blob manageBlob(Object value) throws Exception {
        byte[] b = null;
        if (value instanceof String) {
            b = ((String) value).getBytes();
        } else {
            b = (byte[]) value;
        }
        return (Blob) createBLOB(b);
    }

    private static <T> T convertValueByType(Object value, Class<T> pType) throws Exception {
        try {
            return pType.cast(value);
        } catch (ClassCastException e) {
        }
        if ((pType.getName().compareTo(Blob.class.getName()) == 0) || (value instanceof byte[])) {
            return (T) BeanManager.manageBlob(value);
        }
        String sValue = value.toString();
        sValue = BeanManager.correctNumberValue(sValue, pType);
        T oVal = BeanManager.valFromConstructor(pType, sValue);
        if (oVal != null) {
            return oVal;
        }
        if (pType.getName().compareTo(Timestamp.class.getName()) == 0) {
            return (T) BeanManager.convertTimestamp(sValue);
        }
        if (pType.getName().compareTo(Date.class.getName()) == 0) {
            return (T) BeanManager.convertDate(sValue);
        }

        if (pType.getName().compareTo(Clob.class.getName()) == 0) {
            return (T) createCLOB(sValue);
        }
        if (pType.getName().compareTo(Blob.class.getName()) == 0) {
            // return BlobProxy.generateProxy(sValue.getBytes());
            return (T) createBLOB(sValue.getBytes());
        }
        return null;
    }

    private static <T> T createCLOB(String sValue) throws Exception {
        // return ClobProxy.generateProxy(sValue);
        try {
            return (T) createCLOBHB36(sValue);
            // (T) Hibernate.createClob(sValue);//

        } catch (NoClassDefFoundError e) {
            Class c = Class.forName("org.hibernate.lob.ClobImpl");
            return (T) c.getConstructor(String.class).newInstance(sValue);
            // return (T) new ClobImpl(sValue);
        } catch (Exception e) {
            Class c = Class.forName("org.hibernate.lob.ClobImpl");
            return (T) c.getConstructor(String.class).newInstance(sValue);
            // return (T) new ClobImpl(sValue);

        }
    }

    private static <T> T createBLOB(byte[] pB) throws Exception {
        try {
            return (T) createBLOBHB36(pB);
            // (T) Hibernate.createBlob(pB);

        } catch (NoClassDefFoundError e) {
            Class c = Class.forName("org.hibernate.lob.BlobImpl");
            return (T) c.getConstructor(byte[].class).newInstance(pB);
            // return (T) new BlobImpl(pB);
        } catch (Exception e) {
            if (e instanceof ClassNotFoundException) {
                Class c = Class.forName("org.hibernate.lob.BlobImpl");
                return (T) c.getConstructor(byte[].class).newInstance(pB);
            }
            throw e;
        }
    }

    private static <T> T createCLOBHB36(String sValue) throws Exception {
        Class c = Class.forName("org.hibernate.engine.jdbc.ClobProxy");
        Method m = c.getMethod("generateProxy", String.class);
        return (T) m.invoke(null, sValue);
    }

    private static <T> T createBLOBHB36(byte[] sValue) throws Exception {
        Class c = Class.forName("org.hibernate.engine.jdbc.BlobProxy");
        Method m = c.getMethod("generateProxy", byte[].class);
        return (T) m.invoke(null, sValue);
    }

    private static String correctNumberValue(String pValue, Class<?> pType) {
        String sValue = pValue;
        if ((pType.getName().compareTo("java.math.BigDecimal") == 0) && (sValue.indexOf(',') > 0)) {
            sValue = sValue.replaceAll("\\.", "");
            sValue = sValue.replace(',', '.');
        }
        if (((pType.getName().compareTo("java.lang.Integer") == 0) || (pType.getName().compareTo("java.lang.Long") == 0))
                && (sValue.indexOf('.') > 0)) {
            // if (sValue.indexOf('.') > 0) {
            sValue = sValue.substring(0, sValue.indexOf('.'));
            // }
        }
        return sValue;
    }

    private static boolean filterFields(Field obj) {
        if ((obj.getName().compareTo("pk") == 0) || (obj.getName().compareTo("hashValue") == 0)
                || (obj.getName().compareTo("class") == 0) || (obj.getName().compareTo("serialVersionUID") == 0)
                || (obj.getName().compareTo("TABLE_NAME") == 0)) {
            return true;
        }
        return false;
    }

    /**
     * Entrega el valor de un atributo para que sea comparable.
     * 
     * @param pObject
     *            Bean de referencia.
     * @param pAttribute
     *            Atributo a obtener el valor.
     * @return
     * @throws Exception
     */
    @SuppressWarnings(BeanManager.UNCHECKED)
    public static Comparable<Object> getBeanAttributeComparable(Object pObject, String pAttribute) throws Exception {
        return (Comparable<Object>) BeanManager.getBeanAttributeValue(pObject, pAttribute);
    }

    /**
     * Entrega el valor de un atributo perteneciente a un bean persistente.
     * 
     * @param pObject
     *            Bean persistente.
     * @param pName
     *            Nombre del atributo.
     * @return
     * @throws Exception
     */
    public static Object getBeanAttributeValue(Object pObject, String pAttribute) throws Exception {
        if (pAttribute.indexOf('.') > 0) {
            String name = pAttribute.substring(0, pAttribute.indexOf('.'));
            String subName = pAttribute.substring(pAttribute.indexOf('.') + 1);
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m = pObject.getClass().getMethod("get" + name, new Class[] {});
            return BeanManager.getBeanAttributeValue(m.invoke(pObject, new Object[] {}), subName);
        }
        Method m = BeanManager.getBeanGetterMethod(pObject, pAttribute);
        return m.invoke(pObject, new Object[] {});
    }

    /**
     * Entrega el valor de un atributo perteneciente a un bean persistente.
     * 
     * @param pObject
     *            Bean persistente.
     * @param pName
     *            Nombre del atributo.
     * @return
     * @throws Exception
     */
    public static Method getBeanGetterMethod(Object pObject, String pField) throws Exception {
        String field = pField.substring(0, 1).toUpperCase() + pField.substring(1);
        return pObject.getClass().getMethod("get" + field, new Class[] {});
    }

    /**
     * Entrega el nombre del metodo a invocar para fijar un valor en un Bean.
     * 
     * @param pObject
     *            Bean de referencia.
     * @param pAttribute
     *            Nombre del atributo a fijar el valor.
     * @param pValue
     *            Valor a fijar en el atributo.
     * @return method
     * @throws Exception
     */
    public static Method getBeanSetterMethod(Object pObject, String pAttribute, Object pValue) throws Exception {
        String attribute = pAttribute.substring(0, 1).toUpperCase() + pAttribute.substring(1);
        Class pClass = null;
        try {
            pClass = pObject.getClass().getDeclaredField(attribute.toLowerCase()).getType();
        } catch (NoSuchFieldException e) {
            pClass = String.class;
        }
        try {
            return pObject.getClass().getMethod("set" + attribute, new Class[] { pClass });
        } catch (NoSuchMethodException e) {
            return pObject.getClass().getMethod("set" + attribute, new Class[] { pValue.getClass() });
        }
    }

    /**
     * Une la tabla con el campo que pertenece a la tabla.
     * 
     * @param tableName
     *            Nombre de la tabla.
     * @param field
     *            Campo de la tabla.
     * @return String Cadena unida.
     * @throws Exception
     */
    public static String joinFields(String tableName, String field) throws Exception {
        return tableName + "+" + field;
    }

    public static String readClob(Clob pData) throws Exception {
        Reader read = pData.getCharacterStream();
        char buff[] = new char[9999];
        int car = 0;
        String sVal = "";
        try {
            do {
                car = read.read(buff);
                if (car > 0) {
                    sVal += new String(buff, 0, car);
                }
            } while (car > 0);
        } finally {
            read.close();
        }
        return sVal;
    }

    /**
     * Fija el valor de un atributo en un TransportBean.
     * 
     * @param pObject
     *            Referencia a TransportBean.
     * @param pAttribute
     *            Nombre del atributo a fijar el valor.
     * @param pValue
     *            Valor a fijar en el atributo del bean.
     * @return
     * @throws Exception
     */
    public static Object setBeanAttributeValue(Object pObject, String pAttribute, Object pValue) throws Exception {
        return BeanManager.setBeanAttributeValue(pObject, pAttribute, pValue, false);
    }

    /**
     * Fija el valor de un atributo en un TransportBean.
     * 
     * @param pObject
     *            Referencia a TransportBean.
     * @param pAttribute
     *            Nombre del atributo a fijar el valor.
     * @param pValue
     *            Valor a fijar en el atributo del bean.
     * @param pUpper
     *            Si la cadena estarï¿½ en mayï¿½suclas o no.
     * @return
     * @throws Exception
     */
    public static Object setBeanAttributeValue(Object pObject, String pAttribute, Object pValue, boolean pUpper)
            throws Exception {
        Class classtype = null;
        try {
            if (pAttribute.indexOf('.') > 0) {
                String name = pAttribute.substring(0, pAttribute.indexOf('.'));
                String subName = pAttribute.substring(pAttribute.indexOf('.') + 1);
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method m = pObject.getClass().getMethod("get" + name, new Class[] {});
                Object subobj = m.invoke(pObject, new Object[] {});
                if (subobj == null) {
                    subobj = m.getReturnType().newInstance();
                    Method m1 = pObject.getClass().getMethod("set" + name, new Class[] { m.getReturnType() });
                    m1.invoke(pObject, subobj);
                }
                return BeanManager.setBeanAttributeValue(subobj, subName, pValue, pUpper);
            }
            Method m = BeanManager.getBeanSetterMethod(pObject, pAttribute, pValue);
            classtype = m.getParameterTypes()[0];
            Object value = BeanManager.convertObject(pValue, classtype, pUpper);
            // pValue = convertObject(pValue,
            // m.getParameterTypes()[0],pUpper);
            return m.invoke(pObject, new Object[] { value });
        } catch (Exception e) {
            throw new FitbankException("GEN021", "CAMPO {0} NO ES DEL TIPO {1} ", e.getStackTrace().toString());
        }

    }

    private static <T> T valFromConstructor(Class<T> pType, String sValue) {
        Class<?>[] param = { String.class };
        try {
            Constructor<?> c = pType.getConstructor(param);
            Object[] data = { sValue };
            return (T) c.newInstance(data);
        } catch (Exception e) {
            if (pType.getName().compareTo("java.math.BigDecimal") == 0) {
                throw new Error(e);
            }
            log.info(e.getMessage());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BeanManager";
    }

}
