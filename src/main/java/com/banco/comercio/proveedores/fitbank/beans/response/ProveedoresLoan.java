package com.banco.comercio.proveedores.fitbank.beans.response;

import com.banco.comercio.proveedores.fitbank.beans.type.AuditType;
import lombok.Data;

import java.util.Date;


@Data
public class ProveedoresLoan extends AuditType {
    private String proveedoresId;
    private String razonSocial;
    private String tipoPersona;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String correo;
    private Date fechaCaducidad;
    private String proyectoId;


}
