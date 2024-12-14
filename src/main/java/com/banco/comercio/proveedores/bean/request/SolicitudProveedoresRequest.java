package com.banco.comercio.proveedores.bean.request;

import com.banco.comercio.proveedores.fitbank.beans.type.AuditType;
import lombok.Data;

@Data
public class SolicitudProveedoresRequest extends AuditType {
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
    private DirectoresRequest directoresRequest;
    private AccionistaRequest accionistaRequest;
    private ProductosRequest productosRequest;
    private GerenteRequest gerenteRequest;
}
