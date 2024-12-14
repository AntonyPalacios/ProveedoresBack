package com.fitbank.common.properties;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertiesHandler {
    // ~ Variables de Instancia
    // ***********************************************************************

    /**
     * Archivo
     */
    private File file;

    /**
     * Recurso que contiene las propiedades definidas en el Archivo
     */
    private Properties resource;

    /**
     * Path del Archivo de propiedades
     */
    private String resourceFile;

    /**
     * Fecha del archivo subido a memoria
     */
    private long date;

    private String name;

    private String basePath;

    private static final String PROPERTIES = ".properties";

    // ~ Contructores
    // *********************************************************************************

    /**
     * Crea una nueva instancia de PropertiesHandler
     * 
     * @throws Exception
     *             Error
     * @param pName
     *            Raiz del Archivo properties Manejado
     */
    public PropertiesHandler(String pName) throws Exception {
        this.name = pName;
        this.reload();

    }

    // ~ Mï¿½todos
    // **************************************************************************************

    /**
     * Verifica si el archivo fuente de las propiedades ha sido modificado
     * 
     * @throws Exception
     *             Error al verificar el Archivo
     */
    private void check() throws Exception {
        try {
            if (this.resource == null) {
                this.reload();
            }
        } catch (NullPointerException e) {
            System.out.println("No se puede cargar en caliente al Archivo " + this.name + PROPERTIES);
        }
    }

    /**
     * Encuentra el archivo
     * 
     * @throws Exception
     *             Error al encontrar el archivo
     */
    private void findFile() throws Exception {
        this.file = new File(this.resourceFile);
        this.date = this.file.lastModified();
    }

    /**
     * Entrega el valor en booleano de la variable
     * 
     * @param pKey
     *            Nombre de la variable a entregar
     * @return El valor booleano de la variable
     * @throws Exception
     *             Error al convertir el valor encontrado a booleano
     */
    public boolean getBooleanValue(String pKey) throws Exception {
        return (this.getValue(pKey).compareTo("true") == 0) ? true : false;
    }

    /**
     * Entrega el valor en entero de la variable
     * 
     * @param pKey
     *            Nombre de la variable a entregar
     * @return El valor entero de la variable
     * @throws Exception
     *             Error al convertir el valor encontrado a entero
     */
    public int getIntValue(String pKey) throws Exception {
        return Integer.parseInt(this.getValue(pKey));
    }

    public Set<Object> getKeyList() throws Exception {
        return this.resource.keySet();
    }

    /**
     * Entrega la lista de Valores asociados al Key separado por ','
     * 
     * @param pKey
     *            Key del archivo
     * @return Lista de valores
     * @throws Exception
     */
    public List<String> getList(String pKey) throws Exception {
        return this.getList(pKey, ",");
    }

    /**
     * Entrega la lista de Valores asociados al Key separado por un token
     * 
     * @param pKey
     *            Key del archivo
     * @param pToken
     *            Separador de valores
     * @return Lista de Valores
     * @throws Exception
     */
    public List<String> getList(String pKey, String pToken) throws Exception {
        String data = this.getStringValue(pKey);
        List<String> list = new ArrayList<String>();
        if (data.indexOf(pToken) < 0) {
            list.add(data);
        } else {
            String[] dataArr = data.split(pToken);
            for (String string : dataArr) {
                list.add(string);
            }
        }
        return list;
    }

    public String getStringValue(String pKey) throws Exception {
        this.check();
        try {
            return this.resource.getProperty(pKey);
        } catch (NullPointerException e) {
            throw new Exception("Cadena No Encontrada " + pKey, e);
        }
    }

    /**
     * Entrega el valor de la variable
     * 
     * @param pKey
     *            Nombre de la variable a entregar
     * @return El valor de la variable
     * @throws Exception
     *             Error al Encontrar la variable
     */
    public String getValue(String pKey) throws Exception {
        this.check();
        try {
            return this.resource.getProperty(pKey).trim();
        } catch (NullPointerException e) {
            throw new Exception("Cadena No Encontrada " + pKey, e);
        }
    }

    public boolean hasValue(String pKey) throws Exception {
        return this.resource.containsKey(pKey);
    }

    /**
     * Recarga el contenido del Archivo
     * 
     * @throws Exception
     *             Error al recargar el archivo
     */
    private void reload() throws Exception {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/" +  this.name + PROPERTIES);
        } catch (NullPointerException e) {
            in = this.getClass().getClassLoader().getResource(this.name + PROPERTIES).openStream();
        }
        this.resource = new Properties();
        this.resource.load(in);
        in.close();
    }
}
