package com.banco.comercio.proveedores.bean.response;

public class SolicitudProveedoresResponse {
    private String solicitudProveedoresId;
    private String razonSocial;
    private String ruc;
    private String actividadEconomicaPrinc;
    private String actividadEconomicaSec;
    private String tipoDocumento;
    private String numeroDocumento;
    private String programaAnticorrupcion;
    private String nombreEncargadoPreven;
    private String licitaEstado;
    private String direccion;
    private String ubigeo;
    private String correo;
    private String telefono;
    private String anexo;
    private String aniosMercado;
    private DirectoresResponse directoresResponse;
    private AccionistaResponse accionistaRequest;
    private ProductosResponse productosResponse;

    public String getSolicitudProveedoresId() {
        return solicitudProveedoresId;
    }

    public void setSolicitudProveedoresId(String solicitudProveedoresId) {
        this.solicitudProveedoresId = solicitudProveedoresId;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getActividadEconomicaPrinc() {
        return actividadEconomicaPrinc;
    }

    public void setActividadEconomicaPrinc(String actividadEconomicaPrinc) {
        this.actividadEconomicaPrinc = actividadEconomicaPrinc;
    }

    public String getActividadEconomicaSec() {
        return actividadEconomicaSec;
    }

    public void setActividadEconomicaSec(String actividadEconomicaSec) {
        this.actividadEconomicaSec = actividadEconomicaSec;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getProgramaAnticorrupcion() {
        return programaAnticorrupcion;
    }

    public void setProgramaAnticorrupcion(String programaAnticorrupcion) {
        this.programaAnticorrupcion = programaAnticorrupcion;
    }

    public String getNombreEncargadoPreven() {
        return nombreEncargadoPreven;
    }

    public void setNombreEncargadoPreven(String nombreEncargadoPreven) {
        this.nombreEncargadoPreven = nombreEncargadoPreven;
    }

    public String getLicitaEstado() {
        return licitaEstado;
    }

    public void setLicitaEstado(String licitaEstado) {
        this.licitaEstado = licitaEstado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getAniosMercado() {
        return aniosMercado;
    }

    public void setAniosMercado(String aniosMercado) {
        this.aniosMercado = aniosMercado;
    }

    public DirectoresResponse getDirectoresRequest() {
        return directoresResponse;
    }

    public void setDirectoresRequest(DirectoresResponse directoresResponse) {
        this.directoresResponse = directoresResponse;
    }

    public AccionistaResponse getAccionistaRequest() {
        return accionistaRequest;
    }

    public void setAccionistaRequest(AccionistaResponse accionistaRequest) {
        this.accionistaRequest = accionistaRequest;
    }

    public ProductosResponse getProductosRequest() {
        return productosResponse;
    }

    public void setProductosRequest(ProductosResponse productosResponse) {
        this.productosResponse = productosResponse;
    }
}
